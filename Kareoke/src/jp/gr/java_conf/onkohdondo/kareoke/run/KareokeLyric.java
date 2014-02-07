package jp.gr.java_conf.onkohdondo.kareoke.run;

import java.rmi.UnexpectedException;

import jp.gr.java_conf.onkohdondo.kareoke.util.Line;
import jp.gr.java_conf.onkohdondo.kareoke.util.Music;
import jp.gr.java_conf.onkohdondo.kareoke.util.Node;
import processing.core.PApplet;
import processing.core.PConstants;

public class KareokeLyric implements Mode{
	private Music music;
	private PApplet p;
	
	private final DisplayedLine[] displayedLine;
	
	public KareokeLyric(PApplet p){
		try {
			music=new Music("600.txt");
		} catch (UnexpectedException e) {
			e.printStackTrace();
		}
		displayedLine=new DisplayedLine[4];
		for(int i=0;i<displayedLine.length;i++)
			displayedLine[i]=new DisplayedLine(p,p.width, p.height/4);
		this.p=p;
	}
	
	public void draw() {
		PApplet.println((int)(p.millis()/(60000.0/186*4)));
		p.background(128);
		boolean somethingDisplayed=false;
		for(DisplayedLine b:displayedLine)
			if(b.isDisplayed()) somethingDisplayed=true;
		if(!somethingDisplayed){
			if(music.isSomethingThatMustDisplay(p.millis(), 3000)){
				for(int i=0;i<displayedLine.length;i++){
					displayedLine[i].setLine(music.getLineThatMustDisplay
							(i, p.millis(), 12000));
					if(displayedLine[i].getLine()!=null)
						displayedLine[i].setDisplayed(true);
				}
			}
		}else{
			for(int i=0;i<displayedLine.length;i++){
				DisplayedLine thisDLine=displayedLine[i];
				Line thisLine=thisDLine.getLine();
				if(displayedLine[i].isDisplayed()){
					double x=0;
					p.textAlign(PConstants.LEFT);
					p.textFont(music.kareokeFontB);
					if(thisLine.getLocation()==Line.LEFT){
//						PApplet.println(displayedLine[i].
//								getLine().getString()+"\t"+Line.LEFT);
						x=0;
					}if(thisLine.getLocation()==Line.CENTER){
						x=p.width/2-thisLine.getWidth(p)/2;
//						x=p.width/2-p.textWidth(
//								displayedLine[i].getLine().getString())/2;
					}if(thisLine.getLocation()==Line.RIGHT){
						x=p.width-thisLine.getWidth(p);
//						x=p.width-p.textWidth(
//								displayedLine[i].getLine().getString());
					}
					//TODO p.heightは,各行に即したy座標。
					double nowX=x;
					for(int j=0,thisNodeWidth=0;j<thisLine.node.size();j++){
						Node thisNode=thisLine.node.get(j);
						thisNodeWidth=(int) thisNode.getWidth(p);
						double inset,offset,base;
						inset=thisNode.getMainInset(p);
						offset=Kareoke.MAINOFFSET;
						base=(i+1)*displayedLine[i].
								getGraphics(music,p.millis()).height;
						p.fill(255);
						p.textFont(music.kareokeFontT,music.kareokeSizeB);
						p.text(thisNode.getMain(),(float) (nowX+inset),
								(float) (base-offset));
						p.fill(0);
						p.textFont(music.kareokeFontB,music.kareokeSizeB);
						p.text(thisNode.getMain(),(float) (nowX+inset),
								(float) (base-offset));
						inset=thisNode.getRubyInset(p);
						offset+=music.kareokeSizeB;
						p.fill(255);
						p.textFont(music.kareokeFontT,music.kareokeSizeS);
						p.text(thisNode.getRuby(),(float) (nowX+inset),
								(float) (base-offset));
						p.fill(0);
						p.textFont(music.kareokeFontB,music.kareokeSizeS);
						p.text(thisNode.getRuby(),(float) (nowX+inset),
								(float) (base-offset));
						nowX+=thisNodeWidth;
					}
					p.image(thisDLine.getGraphics(music, p.millis()),
							(float) x,i*thisDLine.
							getGraphics(music, p.millis()).height);
					if(thisDLine.getEnd()+1000<p.millis()){
						thisDLine.setDisplayed(false);
						thisDLine.setLine(null);
						thisDLine.setLastDeleted(p.millis());
					}
				}
			}
			for(int i=0;i<displayedLine.length;i++){
				if(!displayedLine[i].isDisplayed()){
					if(music.isSomethingThatMustDisplay(
							p.millis(), 3000 , i) && 
							displayedLine[i].getLastDeleted()+500 <=
							p.millis()){
						displayedLine[i].setLine(music.
								getLineThatMustDisplay
								(i, p.millis(), 3000));
						if(displayedLine[i].getLine()!=null)
							displayedLine[i].setDisplayed(true);
					}else{
						displayedLine[i].setLine(music.
								getLineThatMustDisplay
								(i, p.millis(), 1000));
						if(displayedLine[i].getLine()!=null)
							displayedLine[i].setDisplayed(true);
					}
				}
			}
		}
	}

	public void keyTyped() {
		
	}
	
}
