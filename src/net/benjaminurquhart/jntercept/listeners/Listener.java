package net.benjaminurquhart.jntercept.listeners;

import net.benjaminurquhart.jntercept.events.*;

public abstract class Listener {

	public void onEvent(Event event) {
		if(event instanceof ReadyEvent) {
			this.onReady((ReadyEvent)event);
		}
		if(event instanceof CommandEvent) {
			this.onCommand((CommandEvent)event);
		}
		if(event instanceof BroadcastEvent) {
			this.onBroadcast((BroadcastEvent)event);
		}
		if(event instanceof RemoteConnectionEvent) {
			this.onRemoteConnection((RemoteConnectionEvent)event);
		}
		if(event instanceof ChatMessageReceivedEvent) {
			this.onChatMessageReceived((ChatMessageReceivedEvent)event);
		}
	}
	public void onReady(ReadyEvent event) {}
	public void onCommand(CommandEvent event) {}
	public void onBroadcast(BroadcastEvent event) {}
	public void onRemoteConnection(RemoteConnectionEvent event) {}
	public void onChatMessageReceived(ChatMessageReceivedEvent event) {}
}
