package net.benjaminurquhart.jntercept.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.utils.Checks;
import net.benjaminurquhart.jntercept.utils.Logger;

import javax.security.auth.login.*;

public class Requester {

	private Socket conn;
	private BufferedReader input;
	private PrintWriter output;
	
	private JSONObject clientInfo;
	
	private EventHandler handler;
	
	public Requester(Jntercept client) throws Exception{
		conn = new Socket(client.getIP(), client.getPort());
		input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		output = new PrintWriter(conn.getOutputStream());
		clientInfo = new JSONObject(input.readLine());
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
	}
	public void send(JSONObject json){
		try {
			Logger.debug(json.toString());
			output.println(json);
			output.flush();
		}
		catch(Exception e) {}
	}
	public JSONObject getConnectionInfo() {
		return this.clientInfo;
	}
	public void stop() {
		try {
			Logger.info("Stopping...");
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
