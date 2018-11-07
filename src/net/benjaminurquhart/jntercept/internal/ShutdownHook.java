package net.benjaminurquhart.jntercept.internal;

import net.benjaminurquhart.jntercept.Jntercept;

public class ShutdownHook extends Thread{

	private Jntercept client;
	
	public ShutdownHook(Jntercept client){
		this.client = client;
	}
	
	@Override
	public void run(){
		client.shutdown();
	}
}
