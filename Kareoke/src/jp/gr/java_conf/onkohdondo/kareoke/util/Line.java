package jp.gr.java_conf.onkohdondo.kareoke.util;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

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
	
	public String getString(){
		String ret="";
		for(Node l : node) ret+=l.getStr();
		return ret;
	}
	
	public double getWidth(PApplet p2, PFont font,double time){
		double ret=0;
		p2.textFont(font);
		for(Node node:this.node){
			if(node.getEnd()<=time){
				ret+=p2.textWidth(node.getStr());
			}else{
				double plus=p2.textWidth(node.getStr())*
						(time-node.getStart())/node.getLength();
				if(plus>0) ret+=plus;
			}
		}
		return ret;
	}
}
