package net.benjaminurquhart.jntercept.internal;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.events.*;
import net.benjaminurquhart.jntercept.listeners.Listener;
import net.benjaminurquhart.jntercept.utils.Logger;

public class EventHandler extends Thread{

	private static BufferedReader input;
	private static Jntercept client;
	
	@SuppressWarnings("static-access")
	protected EventHandler(BufferedReader input, Jntercept client) {
		this.input = input;
		this.client = client;
	}
	
	@Override
	public void run() {
		while(!interrupted()) {
			try {
				String line = input.readLine();
				if(line == null) {
					if(client.isHalted()) {
						System.exit(0);
					}
					else {
						System.err.println("Connection to server closed.");
						System.exit(1);
					}
				}
				else {
					try{
						handleEvent(new JSONObject(line));
					}
					catch(Exception e){
						System.err.println("An exception occured!");
						e.printStackTrace();
					}
				}
			}
			catch(IOException e){
				this.interrupt();
			}
		}
	}
	public static void handleEvent(JSONObject json) {
		Logger.debug(json.toString());
		if(!json.has("event")){
			Logger.warn("Received event with no event field! Assuming we hit a rate-limit.");
			json.put("event", "error");
		}
		Event event = new Event(json, client); 
		switch(json.getString("event")) {
		case "chat": event = new ChatMessageReceivedEvent(json, client); break;
		case "error" : event = new ErrorEvent(json, client);  break;
		case "connect": event = new ReadyEvent(json, client);  break;
		case "command": event = new CommandEvent(json, client);  break;
		case "connected": event = new RemoteConnectionEvent(json, client);  break;
		case "broadcast": event = new BroadcastEvent(json,  client);  break;
		default: Logger.warn("Unknown event: " + json);  break;
		}
		for(Listener listener : client.getEventListeners()) {
			listener.onEvent(event);
		}
	}
}
