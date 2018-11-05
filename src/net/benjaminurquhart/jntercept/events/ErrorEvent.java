package net.benjaminurquhart.jntercept.events;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;

public class ErrorEvent extends Event {

	public ErrorEvent(JSONObject data, Jntercept client) {
		super(data, client);
	}
	@Override
	public String getMessage() {
		return super.getJSON().getString("error");
	}
}
