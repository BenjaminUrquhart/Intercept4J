package net.benjaminurquhart.jntercept.internal;

import java.util.function.Consumer;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.events.Event;

public class QueuedAction {

	private Consumer<Event> consumer;
	private JSONObject json;
	
	protected QueuedAction(JSONObject json, Consumer<Event> consumer) {
		this.consumer = consumer;
		this.json = json;
	}
	protected static QueuedAction build(JSONObject json, Consumer<Event> consumer) {
		return new QueuedAction(json, consumer);
	}
	public JSONObject getJSON() {
		return this.json;
	}
	public Consumer<Event> getCallback(){
		return this.consumer;
	}
}
