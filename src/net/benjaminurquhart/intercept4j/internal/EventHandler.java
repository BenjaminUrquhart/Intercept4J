package net.benjaminurquhart.intercept4j.internal;

import java.io.BufferedReader;

import org.json.JSONObject;

import net.benjaminurquhart.intercept4j.Intercept;
import net.benjaminurquhart.intercept4j.events.*;
import net.benjaminurquhart.intercept4j.events.Event;
import net.benjaminurquhart.intercept4j.listeners.Listener;
import net.benjaminurquhart.intercept4j.utils.Logger;

public class EventHandler extends Thread{

	private static BufferedReader input;
	private static Intercept client;
	
	@SuppressWarnings("static-access")
	protected EventHandler(BufferedReader input, Intercept client) {
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
		case "chat": event = new ChatMessageReceivedEvent(json, client);
		case "connect": event = new ReadyEvent(json, client); 
		case "command": event = new CommandEvent(json, client);
		case "connected": event = new RemoteConnectionEvent(json, client);
		case "broadcast": event = new BroadcastEvent(json,  client);
		default: Logger.warn("Unknown event: " + json);
		}
		for(Listener listener : client.getEventListeners()) {
			listener.onEvent(event);
		}
	}
}
