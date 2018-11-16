package net.benjaminurquhart.jntercept.utils;

public class ColorUtil extends ANSI{

	public static String stripBubColor(String text){
		return text.replaceAll(SPLIT + ".", "");
	}
	public static String toANSI(String text){
		for(BubColor color : BubColor.values()){
			text = text.replace(color.toString(), color.toANSI());
		}
		return text;
	}
}
