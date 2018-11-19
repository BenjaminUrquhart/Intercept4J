package net.benjaminurquhart.jntercept.internal;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.events.Event;

public class Queue {

	private ArrayList<QueuedAction> queue;
	
	protected Queue() {
		this.queue = new ArrayList<>();
	}
	public synchronized void queue(JSONObject json) {
		this.queue(json, c -> {});
	}
	public synchronized void queue(JSONObject json, Consumer<Event> callback) {
		queue.add(QueuedAction.build(json, callback));
	}
	protected synchronized void dequeue(QueuedAction action) {
		queue.remove(action);
	}
	protected boolean hasNext() {
		return this.queue.size() > 0;
	}
	protected synchronized QueuedAction getLatest() {
		if(!this.hasNext()) {
			return null;
		}
		return this.queue.remove(0);
	}
}
