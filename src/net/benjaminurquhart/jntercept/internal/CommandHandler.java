package net.benjaminurquhart.jntercept.internal;

import java.util.function.Consumer;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.enums.Command;
import net.benjaminurquhart.jntercept.events.Event;

public class CommandHandler {

	private Requester requester = null;
	
	public CommandHandler(Requester requester) {
		this.requester = requester;
	}
	public void runCommand(Command cmd, String... args) {
		runCommand(o -> {}, cmd, args);
	}
	public void runCommand(Command cmd, String args) {
		runCommand(o -> {}, cmd, args);
	}
	public void runCommand(String cmd) {
		runCommand(o -> {}, cmd);
	}
	public void runCommand(Consumer<Event> callback, Command cmd, String... args){
		String arg = "";
		for(String s : args){
			arg += s + " ";
		}
		runCommand(callback, cmd, arg);
	}
	public void runCommand(Consumer<Event> callback, Command cmd, String args) {
		runCommand(callback, cmd + args);
	}
	public void runCommand(Consumer<Event> callback, String cmd) {
		JSONObject request = new JSONObject();
		request.put("request", "command");
		request.put("cmd", cmd.trim());
		requester.getQueue().queue(request, callback);
	}
}
