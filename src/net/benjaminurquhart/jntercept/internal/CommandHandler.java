package net.benjaminurquhart.jntercept.internal;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.enums.Command;

public class CommandHandler {

	private Requester requester = null;
	
	public CommandHandler(Requester requester) {
		this.requester = requester;
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
		requester.getQueue().queue(request);
	}
}
