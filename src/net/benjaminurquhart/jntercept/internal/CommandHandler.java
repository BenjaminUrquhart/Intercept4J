package net.benjaminurquhart.jntercept.internal;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.enums.Command;

public class CommandHandler {

	private static Jntercept client = null;
	
	public static void setClient(Jntercept client) {
		CommandHandler.client = client;
	}
	public void runCommand(Command cmd, String... args){
		String arg = "";
		for(String s : args){
			arg += s + " ";
		}
		runCommand(cmd, arg);
	}
	public void runCommand(Command cmd, String args) {
		runCommand(cmd + args);
	}
	public void runCommand(String cmd) {
		JSONObject request = new JSONObject();
		request.put("request", "command");
		request.put("cmd", cmd.trim());
		client.getRequester().getQueue().queue(request);
	}
}
