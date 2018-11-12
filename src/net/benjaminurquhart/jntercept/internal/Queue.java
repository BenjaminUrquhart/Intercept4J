package net.benjaminurquhart.jntercept.internal;

import java.util.ArrayList;

import org.json.JSONObject;

public class Queue {

	private ArrayList<JSONObject> queue;
	
	protected Queue() {
		this.queue = new ArrayList<>();
	}
	public synchronized void queue(JSONObject json) {
		queue.add(json);
	}
	protected synchronized void dequeue(JSONObject json) {
		queue.remove(json);
	}
	protected boolean hasNext() {
		return this.queue.size() > 0;
	}
	protected synchronized JSONObject getLatest() {
		if(!this.hasNext()) {
			return null;
		}
		return this.queue.remove(0);
	}
}
