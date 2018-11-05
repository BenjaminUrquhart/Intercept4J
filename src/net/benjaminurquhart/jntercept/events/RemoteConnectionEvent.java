package net.benjaminurquhart.jntercept.events;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;

public class RemoteConnectionEvent extends Event {

	private JSONObject player;
	
	public RemoteConnectionEvent(JSONObject data, Jntercept client) {
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
