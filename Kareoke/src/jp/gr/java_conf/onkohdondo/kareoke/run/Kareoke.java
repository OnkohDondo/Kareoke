package jp.gr.java_conf.onkohdondo.kareoke.run;

import processing.core.PApplet;
import ddf.minim.Minim;

public class Kareoke extends PApplet {
	private static final long serialVersionUID = 6047809242311459717L;
	
	private Mode mode;
	
	public static int MAINOFFSET=40;
	
	public static void main(String[] args) {
		PApplet.main(Kareoke.class.getName());
	}
	
	public void setup(){
		size(640,480);
		mode=new KareokeLyricMode(this);
		
//		Minim minim=new Minim(this);
//		minim.loadFile("六兆年と一夜物語.mp3",2048);
	}
	
	public void draw(){
		mode.draw();
	}
}
