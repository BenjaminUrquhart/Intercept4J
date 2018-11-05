package net.benjaminurquhart.intercept4j.events;

import org.json.JSONObject;

import net.benjaminurquhart.intercept4j.Intercept;

public class CommandEvent extends Event{

	private String cmd;
	
	public CommandEvent(JSONObject data, Intercept client) {
		super(data, client);
		this.cmd = data.getString("cmd");
	}
	public String getCommand() {
		return cmd;
	}
}
