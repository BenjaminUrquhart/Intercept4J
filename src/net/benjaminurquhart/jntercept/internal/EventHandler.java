package net.benjaminurquhart.jntercept.internal;

import java.io.BufferedReader;

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
		while(true) {
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
					handleEvent(new JSONObject(line));
				}
			}
			catch(Exception e) {
				System.err.println("An exception occured!");
				e.printStackTrace();
			}
		}
	}
	public static void handleEvent(JSONObject json) {
		Logger.debug(json.toString());
		Event event = new Event(json, client); 
		switch(json.getString("event")) {
		case "chat": event = new ChatMessageReceivedEvent(json, client); break;
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
