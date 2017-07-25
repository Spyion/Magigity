package components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import debug.Debug;

public class CollidableObject extends DrawableObject{
	public final Collider collider;
	
	protected float weight;
	
	protected boolean movable;

	protected boolean turnable;
	
	protected boolean clientSided = true;
	
	public final Vector2f speed = new Vector2f(0, 0);
	
	public CollidableObject(Image image, Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable, boolean isTrigger){
		super(image, position, size, rotation);
		collider = new Collider(this, hitbox, isTrigger);
		this.weight = weight;
		this.movable = movable;
		this.turnable = turnable;
	}
	public CollidableObject(Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable){
		this(null, position, size, hitbox, rotation, weight, movable, turnable, false);
	}
	public CollidableObject(Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable, boolean isTrigger){
		this(null, position, size, hitbox, rotation, weight, movable, turnable, isTrigger);
	}
	
	public void update(int delta){
		if(clientSided && movable && (speed.x != 0 || speed.y != 0)){
			position.add(speed.copy().scale(delta/1000f));
			speed.scale((float)Math.pow(0.99, delta));
			if(Math.abs(speed.x) < 0.0001f){
				speed.x = 0;
			}
			if(Math.abs(speed.y) < 0.0001f){
				speed.y = 0;
			}
		}
	}
	@Override
	public void render(Graphics g, Vector2f size){
		render(g, image, size);
	}
	public void render(Graphics g, Image image,Vector2f size){
		super.render(g, image, size);
		g.setColor(Color.red);
		if(Debug.showHitbox){
			collider.render(g);
		}
	}
	
	
	public void collide(CollidableObject object){
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
