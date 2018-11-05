package net.benjaminurquhart.intercept4j.events;

import org.json.JSONObject;

import net.benjaminurquhart.intercept4j.Intercept;

public class RemoteConnectionEvent extends Event {

	private JSONObject player;
	
	public RemoteConnectionEvent(JSONObject data, Intercept client) {
		super(data, client);
		this.player = data.getJSONObject("player");
	}
	public String getRemoteIP() {
		return player.getString("conn");
	}
	public String getLocalIP() {
		return player.getString("ip");
	}

}
