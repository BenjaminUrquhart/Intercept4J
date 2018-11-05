package net.benjaminurquhart.intercept4j.events;

import org.json.JSONObject;

import net.benjaminurquhart.intercept4j.Intercept;

public class BroadcastEvent extends Event {

	public BroadcastEvent(JSONObject data, Intercept client) {
		super(data, client);
	}

}
