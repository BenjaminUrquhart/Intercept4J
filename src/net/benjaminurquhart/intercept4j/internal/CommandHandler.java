package net.benjaminurquhart.intercept4j.internal;

import org.json.JSONObject;

import net.benjaminurquhart.intercept4j.Intercept;
import net.benjaminurquhart.intercept4j.enums.Command;

public class CommandHandler {

	private static Intercept client = null;
	
	public static void setClient(Intercept client) {
		CommandHandler.client = client;
	}
	public void runCommand(Command cmd, String args) {
		runCommand(cmd + args);
	}
	public void runCommand(String cmd) {
		JSONObject request = new JSONObject();
		request.put("request", "command");
		request.put("cmd", cmd);
		client.getRequester().send(request);
	}
}
