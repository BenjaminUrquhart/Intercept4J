package net.benjaminurquhart.jntercept.events;

import org.json.JSONObject;

import net.benjaminurquhart.jntercept.Jntercept;
import net.benjaminurquhart.jntercept.entities.Chatroom;

public class ChatMessageReceivedEvent extends Event {

	private Chatroom chatroom;
	
	public ChatMessageReceivedEvent(JSONObject data, Jntercept client) {
		super(data, client);
		String chatName = super.getJSON().getString("msg").split(" ", 2)[0];
		chatName = chatName.split(" ")[0].substring(1, chatName.length() - 1);
		this.chatroom = new Chatroom(chatName, client);
	}
	public Chatroom getChatroom() {
		return this.chatroom;
	}
	public String getUser() {
		String user = super.getMessage().split(" ")[1];
		user = user.substring(0, user.length() - 1);
		return user;
	}
	@Override
	public String getMessage() {
		return super.getMessage().split(" ", 3)[2];
	}
}
