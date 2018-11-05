package net.benjaminurquhart.intercept4j.entities;

import net.benjaminurquhart.intercept4j.Intercept;
import net.benjaminurquhart.intercept4j.enums.Command;

public class Chatroom extends Entity{

	private String name;
	
	public Chatroom(String name, Intercept client) {
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
