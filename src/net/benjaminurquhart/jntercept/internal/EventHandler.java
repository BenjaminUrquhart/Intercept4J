package net.benjaminurquhart.jntercept.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.events.*;
import net.benjaminurquhart.jntercept.listeners.Listener;
import net.benjaminurquhart.jntercept.utils.Logger;

public class EventHandler extends Thread{

	private static BufferedReader input;
	private static Jntercept client;
	private static UncaughtExceptionHandler exceptionHandler;
	private static List<Consumer<Event>> callbacks;
	
	@SuppressWarnings("static-access")
	protected EventHandler(BufferedReader input, Jntercept client) {
		this.input = input;
		this.client = client;
		this.callbacks = new ArrayList<>();
		this.exceptionHandler = 
		(thread, e) -> {
			Logger.error("An uncaught exception was thrown in an event listener");
			e.printStackTrace();
		};
	}
	
	@Override
	public void run() {
		while(!interrupted()) {
			try {
				String line = input.readLine();
				Logger.debug(line);
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
	protected synchronized void addCallback(Consumer<Event> callback) {
		callbacks.add(callback);
	}
	public static void handleEvent(JSONObject json) {
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
		final Event forListener = event;
		Thread thread = new Thread() {
			@Override
			public void run() {
				if(callbacks.isEmpty()) {
					return;
				}
				synchronized(EventListener.class) {
					callbacks.remove(0).accept(forListener);
				}
			}
		};
		thread.setName("CallbackExecuter-" + thread.hashCode());
		thread.start();
		for(Listener listener : client.getEventListeners()) {
			thread = new Thread() {
				@Override
				public void run() {
					listener.onEvent(forListener);
				}
			};
			thread.setUncaughtExceptionHandler(exceptionHandler);
			thread.start();
		}
	}
}
