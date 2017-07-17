package debug;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Debug {
	public static ArrayList<Vector2f> debugPoints = new ArrayList<Vector2f>();
	public static Circle debugPoint = new Circle(0, 0, 3);
	public static boolean showHitbox = true;
	
	public static void setDebugPoint(float x, float y){
		debugPoint.setCenterX(x);
		debugPoint.setCenterY(y);
	}
}
