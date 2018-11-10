package net.benjaminurquhart.jntercept.enums;

public enum AccountType {

	CLIENT("client"),
	BOT("bot");
	
	private final String type;
	
	private AccountType(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type;
	}
}
