package net.benjaminurquhart.jntercept.internal;

public class QueueThread extends Thread{
	
	private Requester requester;
	
	protected QueueThread(Requester requester) {
		this.requester = requester;
	}
	
	@Override
	public void run() {
		while(!interrupted()) {
			if(requester.getQueue().hasNext()) {
				requester.send(requester.getQueue().getLatest());
			}
		}
	}
}
