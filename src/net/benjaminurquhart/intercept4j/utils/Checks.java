package net.benjaminurquhart.intercept4j.utils;

import org.json.JSONObject;

public final class Checks {

	public static boolean success(JSONObject json) {
		return (json.has("success") && json.getBoolean("success")) || (json.has("sucess") && json.getBoolean("sucess"));
	}
}
