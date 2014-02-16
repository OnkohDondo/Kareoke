package jp.gr.java_conf.onkohdondo.kareoke.util;

import java.util.ArrayList;

import processing.core.PApplet;

public class Node {
	private double start;
	private double length;
	private String str;
	private String ruby;
	private Music parent;
	private ArrayList<Double> timing;
	
	public Node(double arg0, double arg1, String arg2,Music arg3){
		start=arg0;
		length=arg1;
		str=arg2;
		parent=arg3;
		ruby="";
		timing=new ArrayList<Double>();
		timing.add(start);
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
	
	public String getMain(){
		return str;
	}
	
	public void setLength(double arg0){
		length=arg0;
	}
	
	public void setStr(String arg0){
		str=arg0;
	}
	
	public String toString(){
//		return (int)(start/(60000.0/186*4)*16)/16.0+"->"+
//				(int)(length/(60000.0/186*4)*16)/16.0+" "+str+
//				(isRuby()?"<"+ruby+">":"")+timing;
		return start+"->"+
			length+" "+str+
			(isRuby()?"<"+ruby+">":"")+timing;
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
	
	public double getWidth(PApplet p2){
		double ret0=getMainWidth(p2),ret1=getRubyWidth(p2);
		return ret0>ret1?ret0:ret1;
	}

	public double getMainInset(PApplet p2) {
		double ret0=getMainWidth(p2),ret1=getRubyWidth(p2);
		if(ret0<ret1) return (ret1-ret0)/2;
		return 0;
	}
	
	public double getRubyInset(PApplet p2){
		double ret0=getMainWidth(p2),ret1=getRubyWidth(p2);
		if(ret0>ret1)
			return (ret0-ret1)/2;
		return 0;
	}
	
	public double getMainWidth(PApplet p2){
		p2.textFont(parent.kareokeFontB,parent.kareokeSizeB);
		return p2.textWidth(str);
	}
	
	public double getRubyWidth(PApplet p2){
		p2.textFont(parent.kareokeFontB,parent.kareokeSizeS);
		return p2.textWidth(ruby);
	}
	
	public ArrayList<Double> getTiming(){
		return timing;
	}
	
	public double getTiming(int arg0){
		if(arg0==timing.size()){
			return getEnd();
		}
		return timing.get(arg0);
	}
	
	public void addTiming(double arg0){
		timing.add(arg0);
	}
	
}
