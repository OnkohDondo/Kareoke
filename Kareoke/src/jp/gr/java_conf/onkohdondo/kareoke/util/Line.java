package jp.gr.java_conf.onkohdondo.kareoke.util;

import java.util.ArrayList;

import processing.core.PApplet;

public class Line {
	public ArrayList<Node> node;
	
	public static final int LEFT   =-1;
	public static final int CENTER = 0;
	public static final int RIGHT  = 1;
	
	private int line;
	private int location;
	
	
	public Line(int arg0,int arg1){
		line=arg0;
		location=arg1;
		node=new ArrayList<Node>();
	}
	
	public int getLine(){
		return line;
	}
	
	public int getLocation(){
		return location;
	}
	
	public String toString(){
		return node.toString();
	}
	
	@Deprecated
	public String getString(){
		String ret="";
		for(Node l : node) ret+=l.getMain();
		return ret;
	}
	
	public double getWidth(PApplet p2, double time){
		double ret=0;
		for(Node node:this.node){
			if(node.getEnd()<=time){
				ret+=node.getWidth(p2);
			}else{
				double plus=node.getWidth(p2)*
						(time-node.getStart())/node.getLength();
				if(plus>0) ret+=plus;
			}
		}
		return ret;
	}

	public double getWidth(PApplet p2) {
		double ret=0;
		for(Node node:this.node)
			ret+=node.getWidth(p2);
		return ret;
	}
}
