package net.benjaminurquhart.intercept4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.benjaminurquhart.intercept4j.internal.CommandHandler;
import net.benjaminurquhart.intercept4j.internal.Requester;
import net.benjaminurquhart.intercept4j.listeners.Listener;
import net.benjaminurquhart.intercept4j.utils.Logger;

public class Intercept {

	private String IP = "209.97.136.54";
	private int port = 13373;
	
	private String username;
	private String password;
	
	private ArrayList<Listener> listeners;
	
	private Requester requester;
	
	private boolean built;
	private boolean stopped;
	
	public Intercept(String username, String password) {
		this.username = username;
		this.password = password;
		this.listeners = new ArrayList<>();
		this.built = false;
		this.stopped = false;
	}
	public Logger getLogger() {
		return new Logger();
	}
	public CommandHandler getCommandHandler() {
		return new CommandHandler();
	}
	public Intercept addEventListeners(List<? extends Listener> listeners) {
		this.listeners.addAll(listeners);
		return this;
	}
	public Intercept addEventListeners(Listener... listeners) {
		return this.addEventListeners(Arrays.asList(listeners));
	}
	public Intercept setIP(String ip) {
		if(built){
			throw new IllegalStateException("Cannot set IP after the client is built!");
		}
		this.IP = ip;
		return this;
	}
	public Intercept setPort(int port) {
		if(built){
			throw new IllegalStateException("Cannot set port after the client is built!");
		}
		this.port = port;
		return this;
	}
	public String getIP() {
		return this.IP;
	}
	public int getPort() {
		return this.port;
	}
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	public List<Listener> getEventListeners(){
		return Collections.unmodifiableList(listeners);
	}
	public Intercept build() throws Exception{
		if(built && !stopped){
			return this;
		}
		Logger.info("Building...");
		this.requester = new Requester(this);
		this.built = true;
		this.stopped = false;
		Logger.info("Ready.");
		CommandHandler.setClient(this);
		return this;
	}
	public Requester getRequester() {
		return this.requester;
	}
	public boolean isHalted() {
		return this.stopped;
	}
	public void shutdown() {
		if(stopped) {
			return;
		}
		this.requester.stop();
		this.stopped = true;
	}
}
