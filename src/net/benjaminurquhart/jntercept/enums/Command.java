package net.benjaminurquhart.jntercept.enums;

public enum Command {

	//Filesystem
	LS("ls "),
	CAT("cat"),
	RM("rm "),
	MKDIR("mkdir "),
	RMDIR("rmdir "),
	
	//Information
	CMDS("cmds "),
	COLOURS("colours "),
	HELP("help "),
	MAN("man "),
	
	//Misc
	SL("sl "),
	
	//Remote
	CONNECT("connect "),
	SLAVES("slaves "),
	EXIT("exit "),
	
	//Software
	ANTIVIRUS("antivirus "),
	SOFTWARE("software "),
	MALWARE("malware "),
	PROBE("probe "),
	GETPW("getpw "),
	TRACE("trace "),
	BREACH("breach "),
	
	//System
	BROADCAST("broadcast "),
	HARDWARE("hardware "),
	ABANDON("abandon "),
	PORTS("ports "),
	SPECS("specs "),
	JOBS("jobs "),
	PASS("pass "),
	BITS("bits "),
	SCAN("scan "),
	
	//Web
	WEB("web "),
	
	//Other
	CHATS("chats "),
	UNKNOWN("");
	
	private final String command;
	
	private Command(String command) {
		this.command = command;
	}
	
	@Override
	public String toString() {
		return command;
	}
}
