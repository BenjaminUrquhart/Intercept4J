package net.benjaminurquhart.jntercept.entities;

import net.benjaminurquhart.jntercept.Jntercept;

public abstract class Entity {

	private Jntercept client;
	
	public Entity(Jntercept client) {
		this.client = client;
	}
	public Jntercept getClient() {
		return client;
	}
}
