package components;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class CollidingObject extends DrawableObject{
	public final Collider collider;
	
	protected float weight;
	
	protected boolean movable;

	protected boolean turnable;
	
	public final Vector2f speed = new Vector2f(0, 0);
	
	public CollidingObject(Image image, Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable){
		super(image, position, size, rotation);
		collider = new Collider(this, hitbox);
		this.weight = weight;
		this.movable = movable;
		this.turnable = turnable;
	}
	public CollidingObject(Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable){
		this(null, position, size, hitbox, rotation, weight, movable, turnable);
	}
	
	public void update(Input input, int delta){
		if(movable && (speed.x != 0 || speed.y != 0)){
			position.add(speed);
			speed.scale((float)Math.pow(0.99, delta));
			if(Math.abs(speed.x) < 0.0001f){
				speed.x = 0;
			}
			if(Math.abs(speed.y) < 0.0001f){
				speed.y = 0;
			}
		}
	}
	
	
	public void collide(CollidingObject object){
		collider.collide(object.collider);
	}
	public void collide(Collider collider){
		collider.collide(collider);
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public boolean isMovable(){
		return movable;
	}
	public void setMovable(boolean movable){
		this.movable = movable;
	}
	/**
	 * not yet working correctly.
	 * @return
	 */
	public boolean isTurnable(){
		return turnable;
	}
	/**
	 * not yet working correctly.
	 * @return
	 */
	public void setTurnable(boolean turnable){
		this.turnable = turnable;
	}
	
}
