package jp.gr.java_conf.onkohdondo.kareoke.run;

import java.rmi.UnexpectedException;

import jp.gr.java_conf.onkohdondo.kareoke.util.Line;
import jp.gr.java_conf.onkohdondo.kareoke.util.Music;
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
				if(displayedLine[i].isDisplayed()){
					double x=0;
					p.textAlign(PConstants.LEFT);
					p.textFont(music.kareokeFontB);
					if(displayedLine[i].getLine().getLocation()==
							Line.LEFT){
//						PApplet.println(displayedLine[i].
//								getLine().getString()+"\t"+Line.LEFT);
						x=0;
					}if(displayedLine[i].getLine().getLocation()==
							Line.CENTER){
						x=p.width/2-p.textWidth(
								displayedLine[i].getLine().getString())/2;
					}if(displayedLine[i].getLine().getLocation()==
							Line.RIGHT){
						x=p.width-p.textWidth(
								displayedLine[i].getLine().getString());
					}
					p.fill(255);
					p.text(displayedLine[i].getLine().getString(),
							(float) x,(i+1)*displayedLine[i].
							getGraphics(music,p.millis()).height-40);
					p.textFont(music.kareokeFontT);
					p.fill(0);
					p.text(displayedLine[i].getLine().getString(),
							(float) x,(i+1)*displayedLine[i].
							getGraphics(music, p.millis()).height-40);
					p.textAlign(PConstants.CENTER);
					for(int l=0,j=0,k=0;
							l<displayedLine[i].getLine().node.size();l++){
						j=(int) p.textWidth(displayedLine[i].getLine().
								node.get(l).getStr());
						p.text(displayedLine[i].getLine().node.get(i).
								getRuby(),k+j/2,(i+1)*displayedLine[i].
								getGraphics(music, p.millis()).height-90);
						k+=j;
					}
					p.image(displayedLine[i].getGraphics(music, p.millis()),
							(float) x,i*displayedLine[i].
							getGraphics(music, p.millis()).height);
					if(displayedLine[i].getEnd()+1000<p.millis()){
						displayedLine[i].setDisplayed(false);
						displayedLine[i].setLine(null);
						displayedLine[i].setLastDeleted(p.millis());
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
