package enitities;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import debug.Debug;
import tools.Information;
import tools.Toolbox;

public class Camera extends DrawableObject{
	
	public final Vector2f zoom = new Vector2f(0.5f,0.5f);
	public final Vector2f targetPosition = new Vector2f(1,1);
	private float scroll = 1;
	private boolean workCamera = false;
	public Camera(){
		
	}
	public Camera(boolean workCamera){
		this.workCamera = workCamera;
	}
	
	public Vector2f getScreenToWorldPoint(Vector2f point){

		point = new Vector2f((point.x-Display.getWidth()/2)/size.x, (point.y-Display.getHeight()/2)/size.y);
		point = point.copy().sub(getRotationDegrees()).add(position);
		return point;
	}
	public Vector2f getWorldToScreenPoint(Vector2f point){
		point = point.copy().sub(position).add(getRotationDegrees());
		point = new Vector2f(point.x*size.x+Display.getWidth()/2, point.y*size.y+Display.getHeight()/2);
		return point;
	}
	public void update(Input input, int delta){
		
		if(workCamera){
			if(input.isKeyDown(Input.KEY_LEFT))
				position.add(new Vector2f(-delta/size.y, 0));
			if(input.isKeyDown(Input.KEY_RIGHT))
				position.add(new Vector2f(delta/size.y, 0));
			if(input.isKeyDown(Input.KEY_UP))
				position.add(new Vector2f(0, -delta/size.x));
			if(input.isKeyDown(Input.KEY_DOWN))
				position.add(new Vector2f(0, delta/size.x));
		}else{
			if(input.isKeyDown(Input.KEY_LEFT))
				addToRotationDegrees(delta/10f);
			if(input.isKeyDown(Input.KEY_RIGHT))
				addToRotationDegrees(delta/-10f);
		}
		float mouseWheel = Mouse.getDWheel();
		scroll += mouseWheel/1000f;
		float scale;
		if(workCamera){
			if(scroll > 2f)
				scroll = 2f;
			if(scroll < 0.1f)
				scroll = 0.1f;
			System.out.println(scroll);
			if(scroll<0){
				scale = (float) (Math.pow(Math.E, scroll)+0.0);
			}else{
				scale = (float) scroll;	
			}
		}else{
			if(scroll > 2f)
				scroll = 2f;
			if(scroll < -4f)
				scroll = -4f;
			
			if(scroll<0){
				scale = (float) (Math.pow(Math.E, scroll)+0.2);
			}else{
				scale = (float) (Math.pow(Math.E, -scroll)*-1+2.2);	
			}
		}
		zoom.set(new Vector2f(scale, scale));
		
		
	}

	
}
