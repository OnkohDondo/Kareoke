package jp.gr.java_conf.onkohdondo.kareoke.util;

public class Node {
	private double start;
	private double length;
	private String str;
	private String ruby;
	
	public Node(double arg0, double arg1, String arg2){
		start=arg0;
		length=arg1;
		str=arg2;
		ruby="";
	}
	
	public double getStart(){
		return start;
	}
	
	public double getLength(){
		return length;
	}
	
	public double getEnd(){
		return start+length;
	}
	
	public String getStr(){
		return str;
	}
	
	public void setLength(double arg0){
		length=arg0;
	}
	
	public void setStr(String arg0){
		str=arg0;
	}
	
	public String toString(){
		return (int)(start/(60000.0/186*4)*16)/16.0+"->"+
				(int)(length/(60000.0/186*4)*16)/16.0+" "+str+
				(isRuby()?"<"+ruby+">":"");
	}

	public String getRuby() {
		return ruby;
	}

	public void setRuby(String ruby) {
		this.ruby = ruby;
	}
	
	public boolean isRuby(){
		return !ruby.equals("");
	}
}
