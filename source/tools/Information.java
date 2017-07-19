package tools;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.geom.Vector2f;

import enitities.Camera;

public class Information {
	
	
	private static int inactiveTime = 0;
	private static Vector2f lastMouse;
	public static Camera currentCamera;
	public static Vector2f getMouse() {
		return new Vector2f(Mouse.getX(), Display.getHeight()-Mouse.getY());
	}
	public static float getMouseX(){
		return Mouse.getX();
	}
	public static float getMouseY(){
		return Display.getHeight()-Mouse.getY();
	}
	public static Vector2f getDisplayVector(){
		return new Vector2f(Display.getWidth(), Display.getHeight());
	}
	public static Vector2f getHalfedDisplayVector(){
		return new Vector2f(Display.getWidth()/2, Display.getHeight()/2);
	}
	public static void update(int delta){
		inactiveTime += delta;
		Vector2f currentMouse = getMouse();
		if(!currentMouse.equals(lastMouse)){
			inactiveTime = 0;
		}
		lastMouse = currentMouse;
	}
	public static float getInactiveTime() {
		return inactiveTime;
	}
	public static boolean isInactive() {
		return inactiveTime > 100000;
	}
}
