package jp.gr.java_conf.onkohdondo.kareoke.util;

import java.rmi.UnexpectedException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

public class Music {
	private static final int DEFINE=0;
	private static final int MUSIC =1;
	
	private String name;
	
	private ArrayList<Line> music;
	
	public PFont kareokeFontB,kareokeFontT;
	public int kareokeSizeB,kareokeSizeS;
	public PFont rubyB,rubyT;
	
	public Music(String path) throws UnexpectedException{
		kareokeFontT=new PApplet().createFont("あおぞら明朝 Black", 48);
		kareokeFontB=new PApplet().createFont("あおぞら明朝 Heavy", 48);
		kareokeSizeB=48;
		kareokeSizeS=18;
		
		String[] data=new PApplet().loadStrings(path);
		music=new ArrayList<Line>();
		//一行づつ読み込む
		int nowMode=DEFINE;
		double now=0;
		double bpm=0;
		int beat=4;
//		int kakko=0;
		ArrayList<String> buf=new ArrayList<String>();
		for(String thisLine : data){
			//制御メッセージだった場合
			if(thisLine.indexOf("#")!=-1){
				String[] args=thisLine.split("#")[1].split(" ");
				switch(args[0]){
				case "DEFINE":
					nowMode=DEFINE;
					break;
				case "START":
					nowMode=MUSIC;
					break;
				}
			//モードで処理がわかれる
			//定義中の場合
			}else if(nowMode==DEFINE){
				String[] args=thisLine.split("\t");
				switch(args[0]){
				case "NAME":
					name=args[1];
				}
			//音楽の場合
			}else if(nowMode==MUSIC){
				String[] args=thisLine.split("\t");
				if(args.length<2) continue;
				switch(args[0]){
				case "BPM":
					bpm=Integer.parseInt(args[1]);
					break;
				case "DELAY":
					now+=Float.parseFloat(args[1])*1000;
					break;
				default:
//					PApplet.println(args[1]);
					
					String[] defData=args[0].split(" ");
					int linePlace=Integer.parseInt(defData[0])-1;
					int loc=0;
					switch(defData[1]){
					case "R":
						loc=Line.RIGHT;
						break;
					case "C":
						loc=Line.CENTER;
						break;
					case "L":
						loc=Line.LEFT;
						break;
					}
					Line line=new Line(linePlace, loc);
					
					String[] mesureData=args[1].split("/");
					buf.add(thisLine);
					boolean comp=args[1].charAt(
							args[1].length()-1)=='/';
					
					//バッファに１つ（つまり前がなかった）
					if(buf.size()==1){
						for(int i=0;i<mesureData.length-
								(comp?0:1);i++){
							now=loadLine(line,mesureData[i]
									,now,60000.0/bpm*beat);
						}
						music.add(line);
//						if(comp) PApplet.println(line.node);
						if(comp) buf.clear();
					//バッファに2つ以上、ここでバッファが終わらない
					}else if(mesureData.length==1){
						music.add(line);
						buf.add(thisLine);
					//バッファに２つ以上、ここで一旦バッファが終わる
					}else{
						music.add(line);
//						PApplet.println(buf);
						int crossedNodes=countLastMesureNode(
								buf.get(0));
						for(int i=1;i<buf.size();i++){
							crossedNodes+=countNode(
								buf.get(i).split("\t")[1].
								split("/")[0]);
						}
						for(int i=0,j=buf.size();i<buf.size();
								i++,j--){
							String str="";
							if(i==0){
								String[] args_=buf.get(i).split("\t");
								String[] mesures=args_[1].split("/");
								str=mesures[mesures.length-1];
							}else{
								str=buf.get(i).split("\t")[1].
										split("/")[0];
							}
							now=loadLine(music.get(music.size()-j),
									str, now,
									60000.0/bpm*beat,
									crossedNodes);
						}
						for(int i=1;i<mesureData.length-
								(comp?0:1);i++){
							now=loadLine(line,mesureData[i]
									,now,60000.0/bpm*beat);
//							now+=60000.0/bpm*beat;
						}
						buf.clear();
						if(!comp)
							buf.add(thisLine);
					}
				}
			}
		}
		
		for(Line l:music) PApplet.println(l.node);
		
	}
	
	private int countLastMesureNode(String str) 
			throws UnexpectedException{
		String[] args=str.split("\t");
		String[] mesures=args[1].split("/");
		return countNode(mesures[mesures.length-1]);
	}
	
	private int countNode(String arg0) throws UnexpectedException{
		int ret=0;
		boolean counted=false;
		for(int i=0;i<arg0.length();i++){
			switch(arg0.charAt(i)){
			case '_':
			case '~':
				ret++;
			case '-':
				counted=false;
				break;
			default:
				if(!counted){
					counted=true;
					ret++;
				}
			}
		}
//		System.out.println("\tcountNode("+arg0+")="+ret);
		if(ret==0) throw new UnexpectedException(
				"空白小節は認められません。");
		return ret;
	}
	
	private double loadLine(Line target,String data,double start,
			double mesure)throws UnexpectedException{
		return loadLine(target, data, start, mesure, countNode(data));
	}
	
	private double loadLine(Line target,String data,
			double start,double min, int all) throws UnexpectedException{
		boolean counted=false;
		int kakko=0;
		for(int i=0;i<data.length();i++){
			switch(data.charAt(i)){
			case '_':
				start+=min/all;
				counted=false;
				break;
			case '-':
				counted=false;
				break;
			case '~':
				target.node.get(target.node.size()-1).setLength(
						target.node.get(target.node.size()-1)
						.getLength()+min/all);
				start+=min/all;
				counted=false;
				break;
			case '{':
				kakko=1;
				break;
			case '}':
				if(kakko==1) kakko=0;
				else throw new UnexpectedException("うわああああああ");
				break;
			case '<':
				if(!counted) throw new UnexpectedException("ぎゃああああああ");
				kakko=2;
				break;
			case '>':
				if(kakko==2) kakko=0;
				else throw new UnexpectedException("うぎゃあああああああ");
				break;
			default:
				if(counted){
					if(kakko==2){
						target.node.get(target.node.size()-1).setRuby(
							target.node.get(target.node.size()-1)
							.getRuby()+data.charAt(i));
					}else{
						target.node.get(target.node.size()-1).setStr(
							target.node.get(target.node.size()-1)
							.getMain()+data.charAt(i));
					}
				}else{
					target.node.add(new Node(
							start, min/all, data.charAt(i)+"", this));
					counted=true;
					start+=min/all;
				}
			}
		}
		return start;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isSomethingThatMustDisplay(double time,
			double length){
		for(Line line:music){
			try{
				if(time<=line.node.get(0).getStart() &&
						line.node.get(0).getStart()<=time+length){
	//				PApplet.println(time+"\t"+(time+length)+"\t"+
	//					line.node.get(0).getStart());
					return true;
				}
			}catch(IndexOutOfBoundsException e)	{
			}
		}
		return false;
	}

	public boolean isSomethingThatMustDisplay(double time, 
			double length, int loc) {
		if(time<=music.get(loc).node.get(0).getStart() &&
				music.get(loc).node.get(0).getStart()<=time+length){
			return true;
		}
		return false;
	}
	
	/**
	 * 今は１行１つ。
	 * @param location
	 * @param time
	 * @param length
	 * @return
	 */
	public Line getLineThatMustDisplay(int locLine,
			double time,double length){
		for(Line line:music){
			try{
				if(time<=line.node.get(0).getStart() &&
						line.node.get(0).getStart()<=time+length)
					if(line.getLine()==locLine) return line;
			}catch(IndexOutOfBoundsException e)	{
			}
		}
		return null;
	}

}