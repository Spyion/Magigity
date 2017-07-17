package enitities;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import tools.Toolbox;

public class Camera extends DrawableObject{
	
	public Camera(Vector2f position, Vector2f size, float rotation){
		super(position, size, rotation);
	}
	
	public Vector2f getScreenToWorldPoint(Vector2f point){
		
		
		
		return point;
	}
	public Vector2f getWorldToScreenPoint(Vector2f point){
		point = point.copy().sub(position).add(getRotationDegrees());
		point = new Vector2f(point.x*size.x+Display.getWidth()/2, point.y*size.y+Display.getHeight()/2);
		return point;
	}
	public void followPosition(final Vector2f targetPosition, int delta){
		Vector2f distance = Toolbox.getDistanceVector(targetPosition, position);
		distance.scale((float) Math.pow(0.995, delta));
		position.set(new Vector2f(targetPosition.x-distance.x, targetPosition.y-distance.y));
	}
	
}
