package net.benjaminurquhart.jntercept.events;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.internal.CommandHandler;
import net.benjaminurquhart.jntercept.utils.Checks;

public class Event {

	private JSONObject json;
	private Jntercept client;
	
	public Event(JSONObject data, Jntercept client) {
		this.json = data;
		this.client = client;
	}
	public JSONObject getJSON() {
		return this.json;
	}
	public boolean wasSuccessful() {
		return Checks.success(json);
	}
	public String getEventType() {
		return json.getString("event");
	}
	public String getMessage() {
		String msg = null;
		if(json.has("msg")) {
			msg = json.getString("msg").replace("\u200b", " ").replace("\t", " ");
			if(System.getProperty("os.name").startsWith("Windows")) {
				msg = msg.replace("\u00C2", "");
			}
		}
		return msg;
	}
	public Jntercept getClient() {
		return this.client;
	}
	public CommandHandler getCommandHandler() {
		return this.client.getCommandHandler();
	}
}
