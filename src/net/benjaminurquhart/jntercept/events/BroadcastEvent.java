package net.benjaminurquhart.jntercept.events;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;

public class BroadcastEvent extends Event {

	public BroadcastEvent(JSONObject data, Jntercept client) {
		super(data, client);
	}

}
