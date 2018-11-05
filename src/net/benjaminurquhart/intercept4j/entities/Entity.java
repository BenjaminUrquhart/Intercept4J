package net.benjaminurquhart.intercept4j.entities;

import net.benjaminurquhart.intercept4j.Intercept;

public abstract class Entity {

	private Intercept client;
	
	public Entity(Intercept client) {
		this.client = client;
	}
	public Intercept getClient() {
		return client;
	}
}
