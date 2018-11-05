package net.benjaminurquhart.jntercept.events;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;

public class ReadyEvent extends Event {

	public ReadyEvent(JSONObject data, Jntercept client) {
		super(data, client);
	}

}
