package net.benjaminurquhart.jntercept.events;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;

public class CommandEvent extends Event{

	private String cmd;
	
	public CommandEvent(JSONObject data, Jntercept client) {
		super(data, client);
		this.cmd = data.getString("cmd");
	}
	public String getCommand() {
		return cmd;
	}
}
