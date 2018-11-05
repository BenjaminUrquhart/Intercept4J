package net.benjaminurquhart.intercept4j.events;

import org.json.JSONObject;

import net.benjaminurquhart.intercept4j.Intercept;
import net.benjaminurquhart.intercept4j.internal.CommandHandler;
import net.benjaminurquhart.intercept4j.utils.Checks;

public class Event {

	private JSONObject json;
	private Intercept client;
	
	public Event(JSONObject data, Intercept client) {
		this.json = data;
		this.client = client;
	}
	public JSONObject getJSON() {
		return this.json;
	}
	public boolean isRateLimitEvent() {
		return json.has("error") && !json.has("event");
	}
	public boolean wasSuccessful() {
		return Checks.success(json);
	}
	public String getEventType() {
		if(this.isRateLimitEvent()) {
			return "ratelimit";
		}
		return json.getString("event");
	}
	public String getMessage() {
		String msg = null;
		if(json.has("msg")) {
			msg = json.getString("msg").replace("\u200b", " ");
			if(System.getProperty("os.name").startsWith("Windows")) {
				msg = msg.replace("\u00C2", "");
			}
		}
		return msg;
	}
	public Intercept getClient() {
		return this.client;
	}
	public CommandHandler getCommandHandler() {
		return new CommandHandler();
	}
}
