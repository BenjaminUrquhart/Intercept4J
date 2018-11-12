package net.benjaminurquhart.jntercept.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.enums.AccountType;
import net.benjaminurquhart.jntercept.utils.Checks;
import net.benjaminurquhart.jntercept.utils.Logger;

import javax.security.auth.login.*;

public class Requester {
	
	private Socket conn;
	private BufferedReader input;
	private PrintWriter output;
	
	private JSONObject clientInfo;
	
	private EventHandler handler;
	
	private AccountType type;
	
	private int requestsLeft = 5;
	private long canSendAfter = System.currentTimeMillis();
	private RateLimitThread rateLimitThread;
	private QueueThread queueThread;
	private Queue queueHandler;
	
	public Requester(Jntercept client) throws LoginException, UnknownHostException, IOException{
		type = client.getAccountType();
		conn = new Socket(client.getIP(), client.getPort());
		input = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		output = new PrintWriter(conn.getOutputStream());
		clientInfo = new JSONObject(input.readLine());
		rateLimitThread = new RateLimitThread(this);
		queueThread = new QueueThread(this);
		queueHandler = new Queue();
		Logger.info("Connected to websocket.");
		Logger.debug(clientInfo.toString());
		JSONObject json = new JSONObject();
		JSONObject login = new JSONObject();
		login.put("username", client.getUsername());
		login.put("password", client.getPassword());
		json.put("request", "auth");
		json.put("login", login);
		Logger.info("Logging in...");
		send(json);
		json = new JSONObject(input.readLine());
		if(!Checks.success(json)) {
			if(!json.has("error")) {
				throw new LoginException("Failed to authenticate for an unknown reason!\nRaw data: " + json);
			}
			String error = json.getString("error").trim();
			if(error.equalsIgnoreCase("User doesn't exist.")) {
				throw new AccountNotFoundException(error);
			}
			if(error.equalsIgnoreCase("Invalid password.")) {
				throw new FailedLoginException(error);
			}
		}
		json.put("request", "connect");
		json.remove("cfg");
		json.remove("event");
		json.remove("success");
		send(json);
		json = new JSONObject(input.readLine());
		if(!Checks.success(json)) {
			throw new LoginException();
		}
		Logger.info("Logged in.");
		this.handler = new EventHandler(input, client);
		EventHandler.handleEvent(json); //This runs all the listeners that override onReady();
		this.handler.start();
		this.queueThread.start();
	}
	protected Queue getQueue() {
		return this.queueHandler;
	}
	protected void resetRequests(){
		requestsLeft = 5;
	}
	protected synchronized void send(JSONObject json){
		if(json == null) {
			Logger.warn("A null JSONObject was passed to the send method!");
			return;
		}
		try {
			if(type.equals(AccountType.CLIENT)){
				if(!rateLimitThread.didStart()){
					rateLimitThread.start();
				}
				while(requestsLeft <= 0){}
				requestsLeft--;
			}
			else{
				while(canSendAfter - System.currentTimeMillis() > 0){}
				canSendAfter = System.currentTimeMillis() + (50L * (json.has("cmd") ? json.getString("cmd").length() : 0));
			}
			if(json.has("login")) {
				JSONObject censored = new JSONObject(json.toString());
				JSONObject tmp = censored.getJSONObject("login");
				censored.put("login", tmp.put("password", "[CENSORED]"));
				Logger.debug(censored.toString());
			}
			else {
				Logger.debug(json.toString());
			}
			output.println(json);
			output.flush();
		}
		catch(Exception e) {
			Logger.error("An exception occured while sending data to the server!");
			e.printStackTrace();
		}
	}
	public JSONObject getConnectionInfo() {
		return this.clientInfo;
	}
	public void stop() {
		try {
			Logger.info("Stopping...");
			this.rateLimitThread.interrupt();
			this.queueThread.interrupt();
			this.handler.interrupt();
			this.output.close();
			this.input.close();
			this.conn.close();
			Logger.info("Stopped!");
		}
		catch(IOException e) {
			Logger.error(e.toString());
		}
	}
}
