package net.benjaminurquhart.jntercept.utils;

public final class Logger {

	private static boolean enabled = true;
	private static boolean debug = false;
	
	public static void setLoggingEnabled(boolean log) {
		enabled = log;
	}
	public static void setDebugging(boolean debug) {
		Logger.debug = debug;
	}
	private static void print(String s) {
		if(!enabled) {
			return;
		}
		System.err.println(s);
	}
	public static void info(String info) {
		print("[INFO] " + info);
	}
	public static void warn(String warning) {
		print("[WARN] " + warning);
	}
	public static void error(String error) {
		print("[ERROR] " + error);
	}
	public static void debug(String debug) {
		if(!Logger.debug) {
			return;
		}
		print("[DEBUG] "  + debug);
	}
}
