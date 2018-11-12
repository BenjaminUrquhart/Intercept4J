package net.benjaminurquhart.jntercept;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.benjaminurquhart.jntercept.enums.AccountType;
import net.benjaminurquhart.jntercept.internal.CommandHandler;
import net.benjaminurquhart.jntercept.internal.Requester;
import net.benjaminurquhart.jntercept.internal.ShutdownHook;
import net.benjaminurquhart.jntercept.listeners.Listener;
import net.benjaminurquhart.jntercept.utils.Logger;

public class Jntercept {

	private String IP = "209.97.136.54";
	private int port = 13373;
	
	private String username;
	private String password;
	
	private ArrayList<Listener> listeners;
	
	private Requester requester;
	private ShutdownHook shutdownHook;
	private CommandHandler cmdHandler;
	
	private AccountType type;
	
	private boolean built;
	private boolean stopped;
	
	public Jntercept(String username, String password) {
		this(username, password, AccountType.BOT);
	}
	public Jntercept(String username, String password, AccountType type){
		this.username = username;
		this.password = password;
		this.listeners = new ArrayList<>();
		this.type = type;
		this.built = false;
		this.stopped = false;
		this.shutdownHook = new ShutdownHook(this);
	}
	public Logger getLogger() {
		return new Logger();
	}
	public CommandHandler getCommandHandler() {
		return cmdHandler;
	}
	public Jntercept addEventListeners(List<? extends Listener> listeners) {
		this.listeners.addAll(listeners);
		return this;
	}
	public Jntercept addEventListeners(Listener... listeners) {
		return this.addEventListeners(Arrays.asList(listeners));
	}
	public void removeEventListeners(Listener... listeners) {
		this.removeEventListeners(Arrays.asList(listeners));
	}
	public void removeEventListeners(List<Listener> listeners) {
		this.listeners.removeAll(listeners);
	}
	public Jntercept setIP(String ip) {
		if(built){
			throw new IllegalStateException("Cannot set IP after the client is built!");
		}
		this.IP = ip;
		return this;
	}
	public Jntercept setPort(int port) {
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
	public AccountType getAccountType(){
		return this.type;
	}
	public List<Listener> getEventListeners(){
		return Collections.unmodifiableList(listeners);
	}
	public Jntercept build() throws LoginException, UnknownHostException, IOException{
		if(built && !stopped){
			return this;
		}
		Logger.info("Building...");
		Runtime.getRuntime().addShutdownHook(shutdownHook);
		this.requester = new Requester(this);
		this.cmdHandler = new CommandHandler(this.requester);
		this.built = true;
		this.stopped = false;
		Logger.info("Ready.");
		return this;
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
