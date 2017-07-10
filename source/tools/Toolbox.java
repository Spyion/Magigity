package tools;

import org.newdawn.slick.geom.Vector2f;

public class Toolbox {
	public static Vector2f getIntersection(Vector2f point1, Vector2f point2, Vector2f point3, Vector2f point4){
		
		if(point1.x > point2.x)
		{
			Vector2f temp = point1;
			point1 = point2;
			point2 = temp;
		}
		if(point3.x > point4.x)
		{
			Vector2f temp = point3;
			point3 = point4;
			point4 = temp;
		}
		if(point1.x == point2.x)
		{
			point2.x+= 0.01f;
		}
		if(point3.x == point4.x)
		{
			point4.x+= 0.01f;

		}
		double pitch1 = (point2.y-point1.y)/(point2.x-point1.x);
		double b1 = (point1.y+point2.y-pitch1*(point1.x+point2.x))/2;
		double pitch2 = (point4.y-point3.y)/(point4.x-point3.x);
		double b2 = (point3.y+point4.y-pitch2*(point3.x+point4.x))/2;
		double x = (b2-b1)/(pitch1-pitch2);
		Vector2f intersection = new Vector2f((float)x, (float)(pitch1*x+b1));
		
		if(intersection.x > point1.x&&intersection.x < point2.x&&
		   intersection.x > point3.x&&intersection.x < point4.x){
			return intersection;
		}else
			return null;
	}
	public static double getDistance(Vector2f point1, Vector2f point2){
		return Math.sqrt(Math.pow(point1.x-point2.x, 2)+Math.pow(point1.y-point2.y, 2));
	}
	public static double getDistance(float x1, float y1, float x2, float y2){
		return getDistance(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	
}
