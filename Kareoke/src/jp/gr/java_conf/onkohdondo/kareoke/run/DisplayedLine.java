package jp.gr.java_conf.onkohdondo.kareoke.run;

import jp.gr.java_conf.onkohdondo.kareoke.util.Line;
import jp.gr.java_conf.onkohdondo.kareoke.util.Music;
import jp.gr.java_conf.onkohdondo.kareoke.util.Node;
import processing.core.PApplet;
import processing.core.PGraphics;

public class DisplayedLine {
	
//	@SuppressWarnings("unused")
	private double lastDeleted;
	private PGraphics lyric;
	@SuppressWarnings("unused")
	@Deprecated
	private PGraphics mask;
	
	private boolean displayed;
	
	private Line line;
	
	private PApplet p;
	
	public DisplayedLine(PApplet p,int arg0,int arg1){
		setLastDeleted(0);
		this.p=p;
	}
	
	public Line getLine(){
		return line;
	}
	
	public void setLine(Line arg0){
		line=arg0;
	}
	
	public PGraphics getGraphics(Music music,double time){
		lyric=p.createGraphics(
				(int)line.getWidth(p,time)+1,120);
		lyric.beginDraw();
		lyric.background(128);
		for(int i=0,thisNodeWidth=0,nowX=0;i<line.node.size();i++){
			Node thisNode=line.node.get(i);
			thisNodeWidth=(int) thisNode.getWidth(p);
			double inset,offset;
			inset=thisNode.getMainInset(p);
			offset=Kareoke.MAINOFFSET;
			lyric.fill(255);
			lyric.textFont(music.kareokeFontT,music.kareokeSizeB);
			lyric.text(thisNode.getMain(),(float) (nowX+inset),
					(float) (lyric.height-offset));
			lyric.fill(255,0,0);
			lyric.textFont(music.kareokeFontB,music.kareokeSizeB);
			lyric.text(thisNode.getMain(),(float) (nowX+inset),
					(float) (lyric.height-offset));
			inset=thisNode.getRubyInset(p);
			offset+=music.kareokeSizeB;
			lyric.fill(255);
			lyric.textFont(music.kareokeFontT,music.kareokeSizeS);
			lyric.text(thisNode.getRuby(),(float) (nowX+inset),
					(float) (lyric.height-offset));
			lyric.fill(255,0,0);
			lyric.textFont(music.kareokeFontB,music.kareokeSizeS);
			lyric.text(thisNode.getRuby(),(float) (nowX+inset),
					(float) (lyric.height-offset));
			nowX+=thisNodeWidth;
		}
		lyric.endDraw();
//		lyric.beginDraw();
//		lyric.background(128);
//		lyric.textSize(music.kareokeSizeB);
//		lyric.textFont(music.kareokeFontT);
//		lyric.fill(255);
//		lyric.text(line.getString(),0,lyric.height-40);
//		lyric.textFont(music.kareokeFontB);
//		lyric.fill(255,0,0);
//		lyric.text(line.getString()
//				,0,lyric.height-40);
//		lyric.textSize(music.kareokeSizeS);
//		lyric.textAlign(PConstants.CENTER);
//		p.textFont(music.kareokeFontT);
//		for(int i=0,j=0,k=0;i<line.node.size();i++){
//			j=(int) p.textWidth(line.node.get(i).getStr());
//			lyric.text(line.node.get(i).getRuby(),k+j/2,lyric.height-90);
//			k+=j;
//		}
//		lyric.endDraw();
		return lyric;
	}
	
	public boolean isDisplayed(){
		return displayed;
	}
	
	public void setDisplayed(boolean arg0){
		displayed=arg0;
	}
	
	public PGraphics getGraphics(){
		return lyric;
	}

	public double getEnd() {
		return line.node.get(line.node.size()-1).getEnd();
	}

	public double getLastDeleted() {
		return lastDeleted;
	}

	public void setLastDeleted(double lastDeleted) {
		this.lastDeleted = lastDeleted;
	}
	
}
