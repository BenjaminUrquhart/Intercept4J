package net.benjaminurquhart.intercept4j.events;

import org.json.JSONObject;

import net.benjaminurquhart.intercept4j.Intercept;

public class ReadyEvent extends Event {

	public ReadyEvent(JSONObject data, Intercept client) {
		super(data, client);
	}

}
