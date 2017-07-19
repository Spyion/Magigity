package tools;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Toolbox {
	public static Vector2f getIntersectionPoint(Vector2f point1, Vector2f point2, Vector2f point3, Vector2f point4){
		
		Vector2f intersection;
		
		if(point1.x>point2.x){
			Vector2f temp = point1;
			point1 = point2;
			point2 = temp;
		}if(point3.x>point4.x){
			Vector2f temp = point3;
			point3 = point4;
			point4 = temp;
		}
		if(point1.x == point2.x && point3.y == point4.y)
		{
			intersection = new Vector2f(point1.x, point3.y);
		}
		else if(point3.x == point4.x && point1.y == point2.y)
		{
			intersection = new Vector2f(point3.x, point1.y);
		}
		else if(point1.x == point2.x)
		{
			double pitch = (point4.y-point3.y)/(point4.x-point3.x);
			double b = point3.y-pitch*point3.x;
			intersection = new Vector2f((float)point1.x, (float)(pitch*point1.x+b));
		}
		else if(point3.x == point4.x)
		{
			double pitch = (point2.y-point1.y)/(point2.x-point1.x);
			double b = point1.y-pitch*point1.x;
			intersection = new Vector2f((float)point3.x, (float)(pitch*point3.x+b));

		}
		else if(point1.y == point2.y)
		{
			double pitch = (point4.y-point3.y)/(point4.x-point3.x);
			double b = point3.y-pitch*point3.x;
			intersection = new Vector2f((float)((point1.y-b)/pitch), (float)point1.y);
		}
		else if(point3.y == point4.y)
		{
			double pitch = (point2.y-point1.y)/(point2.x-point1.x);
			double b = point1.y-pitch*point1.x;
			intersection = new Vector2f((float)((point3.y-b)/pitch), (float)point3.y);

		}else
		{
			double pitch1 = (point2.y-point1.y)/(point2.x-point1.x);
			double b1 = point1.y-pitch1*point1.x;
			double pitch2 = (point4.y-point3.y)/(point4.x-point3.x);
			double b2 = point3.y-pitch2*point3.x;
			double x = (b2-b1)/(pitch1-pitch2);
			intersection = new Vector2f((float)x, (float)(pitch1*x+b1));
		}
		
		if(intersection.x >= point1.x&&intersection.x <= point2.x&&
		   intersection.x >= point3.x&&intersection.x <= point4.x){
			return intersection;
		}else
			return null;
	}
	public static Vector2f getIntersectionPoint(float x1, float x2, float x3, float x4, float x5, float x6, float x7, float x8){
		return getIntersectionPoint(new Vector2f(x1, x2), new Vector2f(x3, x4), new Vector2f(x5, x6), new Vector2f(x7, x8));
	}
	public static double getDistance(Vector2f point1, Vector2f point2){
		return Math.sqrt(Math.pow(point1.x-point2.x, 2)+Math.pow(point1.y-point2.y, 2));
	}
	public static double getDistance(float x1, float y1, float x2, float y2){
		return getDistance(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	public static Vector2f getDistanceVector(Vector2f point1, Vector2f point2){
		return new Vector2f(point1.x-point2.x, point1.y-point2.y);
	}public static Vector2f getDistanceVector(float x1, float y1, float x2, float y2){
		return getDistanceVector(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	public static Vector2f getLineHalfingPoint(Vector2f point1, Vector2f point2){
		return new Vector2f((point1.x+point2.x)/2,(point1.y+point2.y)/2);
	}
	public static Vector2f getLineHalfingPoint(float x1, float y1, float x2, float y2){
		return getLineHalfingPoint(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	public static Vector2f getLineDivision(Vector2f point1, float factor1, Vector2f point2, float factor2){
		return new Vector2f((point1.x*factor1+point2.x*factor2)/(factor1+factor2),
							(point1.y*factor1+point2.y*factor2)/(factor1+factor2));
	}
	public static Vector2f getOrthogonalPoint(Vector2f linePoint1, Vector2f linePoint2, Vector2f point){
		
		if(linePoint1.x > linePoint2.x)
		{
			Vector2f temp = linePoint1;
			linePoint1 = linePoint2;
			linePoint2 = temp;
		}
		if(linePoint1.x == linePoint2.x)
		{
			return new Vector2f(linePoint1.x, point.y);

		}
		if(linePoint1.y == linePoint2.y){
			return new Vector2f(point.x, linePoint1.y);
		}
		double pitch1 = (linePoint2.y-linePoint1.y)/(linePoint2.x-linePoint1.x);
		double b1 = linePoint1.y-pitch1*linePoint1.x;
		double pitch2 = -1/pitch1;
		double b2 = point.y-pitch2*point.x;
		double x = (b2-b1)/(pitch1-pitch2);
		
		Vector2f orthogonalPoint = new Vector2f((float)x, (float)(b2+pitch2*x));
		
			return orthogonalPoint;
			
	}
	public static Vector2f getOrthogonalPoint(float x, float y, float x1, float y1, float x2, float y2){
		return getOrthogonalPoint(new Vector2f(x, y), new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	public static double getAngle(Vector2f start, Vector2f end){
		double angle = Math.atan((end.y-start.y)/(end.x-start.x));
		return angle;
	}public static double getAngle(Vector2f point){
		return getAngle(new Vector2f(0,0), point);
	}
	
	
//  Working way, but higher Performance cost
	
	public static boolean isPointInRectangle(Vector2f point, Rectangle rect, float rotation){
		if(rotation == 0)
			return rect.contains(point.x, point.y);
		float[] temppoints = rect.transform(Transform.createRotateTransform(rotation, rect.getCenterX(), rect.getCenterY())).getPoints();
		Vector2f[] points = new Vector2f[temppoints.length/2];
		for(int i = 0; i < temppoints.length/2; i++){
			points[i] = new Vector2f(temppoints[2*i], temppoints[2*i+1]);
		}
	
		for(int i = 0; i < points.length ;i++){
			float width;
			if((i & 1) == 1){
				width = rect.getWidth();
			}else{
				width = rect.getHeight();
			}
			Vector2f orthPoint;
			orthPoint = getOrthogonalPoint(points[i%points.length],points[(i+1)%points.length], point);
			
			double distance = getDistance(orthPoint ,point);
			if(distance>width)
			{
				return false;
			}
		}
		return true;
	}
	
	
//	faster way but buggy
	
//	public static boolean isPointInRectangle(Vector2f point, Rectangle rect, float rotation){
//		Vector2f myPoint = getDistanceVector(new Vector2f(rect.getCenter()),point).add(Math.toDegrees(rotation));
//		if(Math.abs(myPoint.x)<=rect.getWidth()&&Math.abs(myPoint.y)<=rect.getHeight())
//			return true;
//		return false;
//	}
	
/**
 * 
 * @param approaching The approaching vector
 * @param target the vector that is being approached
 * @param factor the factor by which it is approached, 1 = never, 0 = instant, should be between 0.8 and 0.999
 * @param delta  delta time
 */
	public static void approachVector(final Vector2f approaching, final Vector2f target, float factor,int delta){
		Vector2f distance = Toolbox.getDistanceVector(target, approaching);
		distance.scale((float) Math.pow(factor, delta));
		approaching.set(new Vector2f(target.x-distance.x, target.y-distance.y));
	}
	/**
	 * 
	 * @param approaching The approaching vector
	 * @param target the vector that is being approached
	 * @param delta  delta time
	 */
	public static void approachVector(final Vector2f approaching, final Vector2f target,int delta){
		approachVector(approaching, target, 0.995f, delta);
	}
	
}
