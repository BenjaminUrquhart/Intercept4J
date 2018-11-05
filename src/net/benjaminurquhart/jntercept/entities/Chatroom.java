package net.benjaminurquhart.jntercept.entities;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.enums.Command;

public class Chatroom extends Entity{

	private String name;
	
	public Chatroom(String name, Jntercept client) {
		super(client);
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void sendMessage(String msg) {
		String args = "send " + this.name + " " + msg;
		super.getClient().getCommandHandler().runCommand(Command.CHATS, args);
	}
	public void leave() {
		super.getClient().getCommandHandler().runCommand(Command.CHATS, "leave " + this.name);
	}
}
