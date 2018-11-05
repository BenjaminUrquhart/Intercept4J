package net.benjaminurquhart.jntercept.internal;

public class RateLimitThread extends Thread {

	private Requester requester;
	private boolean started = false;
	
	protected RateLimitThread(Requester requester){
		this.requester = requester;
	}
	
	public boolean didStart(){
		return this.started;
	}
	@Override
	public void start(){
		this.started = true;
		super.start();
	}
	@Override
	public void run(){
		while(true){
			try{
				Thread.sleep(5000);
				requester.resetRequests();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
