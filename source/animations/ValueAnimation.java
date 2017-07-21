package animations;

import java.util.ArrayList;
import java.util.Collections;

import tools.CSVHandler;

public class ValueAnimation {
	private static CSVHandler csv = new CSVHandler();
	public final String name;
	private int time = 0;
	public final ArrayList<ValueAnimationPoint> points = new ArrayList<ValueAnimationPoint>();
	public final ArrayList<ValueAnimationCurve> curves = new ArrayList<ValueAnimationCurve>();
	private boolean pingPong, loop;
	public ValueAnimation(String ref){
		name = ref.split("/")[ref.split("/").length-1];
		ArrayList<ArrayList<String>> list = csv.readCSV(ref);
		for(ArrayList<String> l : list){
			points.add(new ValueAnimationPoint( Float.parseFloat(l.get(0)),
												Float.parseFloat(l.get(1)),
												Float.parseFloat(l.get(2))));
		}
		for(int i = 0; i < points.size()-1; i++){
			curves.add(new ValueAnimationCurve(points.get(i), points.get(i+1)));
		}
	}
	public ValueAnimation(){
		name = "";
	}
	
	/**
	 * Use only for creating of the animation, resource demanding
	 */
	public void updateCurves(){
		if(!points.isEmpty()&&points.get(0).position.x != 0)
			points.get(0).position.x = 0;
		curves.clear();
		for(int i = 0; i < points.size()-2; i++){
			for(int j = i; j < points.size()-1; j++){
				if(points.get(j).position.x==points.get(j+1).position.x){
					points.get(j+1).position.x+=0.01f;
				}
				else if(points.get(j).position.x>points.get(j+1).position.x)
					Collections.swap(points, j, j+1);
			}
		}
		for(int i = 0; i < points.size()-1;i++){
			curves.add(new ValueAnimationCurve(points.get(i), points.get(i+1)));
		}
		for(int i = 0; i < curves.size(); i++){
			curves.get(i).update();
		}
	}
	
	public float getHeight(float time){
		if(getLength() != 0){
			if(pingPong){
				while(time > getLength()*2){
					time-= getLength()*2;
				}
				if(time > getLength()){
					time-=2*(time-getLength());
				}
			}
			else if(loop){
				while(time > getLength()){
					time-= getLength();
				}
			}
			for(ValueAnimationCurve curve : curves){
				if(curve.isInRange(time))
					return curve.getHeight(time);
			}
			return points.get(0).position.y;
		}
		return -0.111f;
	}
	public float getHeight(){
		return getHeight(time);
	}
	public float getLength(){
		if(!points.isEmpty())
			return points.get(points.size()-1).position.x;
		else
			return 0;
	}
	public ArrayList<ArrayList<String>> getStringArray(){
		ArrayList<ArrayList<String>> array= new ArrayList<ArrayList<String>>();
		for(ValueAnimationPoint point : points){
			ArrayList<String> list = new ArrayList<String>();
			list.add(Float.toString(point.position.x));
			list.add(Float.toString(point.position.y));
			list.add(Float.toString(point.pitch));
			array.add(list);
		}
		return array;
	}
	public int getTime(){
		int time = this.time;
		if(pingPong){
			while(time > getLength()*2){
				time-= getLength()*2;
			}
			if(time > getLength()){
				time-=2*(time-getLength());
			}
		}
		else if(loop){
			while(time > getLength()){
				time-= getLength();
			}
		}
		return time;
	}
	public void setTime(int time){
		this.time = time;
	}
	public boolean update(int delta){
		time += delta;
		if(!loop && !pingPong){
		return isCompleted();
		}
		return false;
	}
	
	public boolean isPingPong() {
		return pingPong;
	}
	public ValueAnimation setPingPong(boolean pingPong) {
		this.pingPong = pingPong;
		return this;
	}
	public boolean isLoop() {
		return loop;
	}
	public ValueAnimation setLoop(boolean loop) {
		this.loop = loop;
		return this;
	}
	public boolean isCompleted(){
		if(time > points.get(points.size()-1).position.x){
			return true;
		}
		return false;
	}
}
