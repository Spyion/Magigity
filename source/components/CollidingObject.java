package components;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class CollidingObject extends DrawableObject{
	public final Collider collider;
	
	protected float weight;
	
	protected boolean movable;

	protected boolean turnable;
	
	public CollidingObject(Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable){
		super(position, size, rotation);
		collider = new Collider(this, hitbox);
		this.weight = weight;
		this.movable = movable;
		this.turnable = turnable;
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
