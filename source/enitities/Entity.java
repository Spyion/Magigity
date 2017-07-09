package enitities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Entity {
	public static ArrayList<Entity> entities=new ArrayList<Entity>();
	
	private Vector2f position;
	private float rotation;
	private Image basicImage;
	private Shape hitbox;
	private float weight;
	private Vector2f size;
	private boolean movable = true;
	private boolean turnable = false;
	
	public Entity(Image basicImage, Shape hitbox, Vector2f position, float rotation, Vector2f size, float weight) {
		super();
		this.position = position;
		this.rotation = rotation;
		this.basicImage = basicImage;
		this.hitbox = hitbox;
		this.weight = weight > 0 ? weight : 0.01f;
		this.size = size;
		entities.add(this);
	}
	public Entity(Image basicImage, Shape hitbox, float x, float y, float rotation ,float sx, float sy, float weight){
		this(basicImage, hitbox, new Vector2f(x, y), rotation, new Vector2f(sx, sy), weight);
	}
	
	public void collide(Entity entity, int delta){
				if(entity.getHitbox() instanceof Circle && hitbox instanceof Circle){
					Circle entityCircle = (Circle) entity.getHitbox();
					Circle myCircle = (Circle) hitbox;
					double currentDistance = Math.sqrt(Math.pow(position.x-entity.position.x, 2)+Math.pow(position.y-entity.position.y, 2));
					double collidingDistance = entityCircle.radius+myCircle.radius;
					if(currentDistance<collidingDistance)
						this.CollideCircleCircle(entity, currentDistance, collidingDistance, entityCircle, myCircle);
				}
	}
	
	private void CollideCircleCircle(Entity entity, double currentDistance, double collidingDistance, Circle entityCircle, Circle myCircle){
		double radians = Math.atan(((entity.getPosition().y-position.y)/(entity.getPosition().x-position.x)));
		double deltaDistance = collidingDistance-currentDistance;
		double entityDistance;
		double myDistance;
		if(entity.isMovable()){
			if(movable){
					double denominator = entity.weight + weight;
					entityDistance = deltaDistance*entity.weight/denominator;
					myDistance = deltaDistance*weight/denominator;
			}else{
				entityDistance = deltaDistance;
				myDistance = 0;
			}
		}else{
			entityDistance = 0;
			myDistance = deltaDistance;
		}
		
		
		float sign = Math.signum(entity.getPosition().x-position.x);
		Vector2f entityPosition = new Vector2f(entity.getPosition().x+(float)(Math.cos(radians)*entityDistance*sign),
											   entity.getPosition().y+(float)(Math.sin(radians)*entityDistance*sign));
		Vector2f myPosition = new Vector2f(position.x+(float)(Math.cos(radians)*myDistance*-sign),
				   						   position.y+(float)(Math.sin(radians)*myDistance*-sign));
		if(turnable)
			addToRotation(turn(position, myPosition, entity.position));
		if(entity.isTurnable())
			entity.addToRotation(turn(entity.position, entityPosition, position));
		
		position = myPosition;
		entity.position = entityPosition;
		
	}
	private float turn(Vector2f before, Vector2f after, Vector2f target){
		
		//TODO if Possible
		return 0;
	}

	public void render(Graphics g){
		Image image = basicImage.copy();
		image.rotate(rotation);
		image.draw(position.x, position.y, size.x, size.y);
		
	}
	
	public void update(Input input, int delta){
		this.hitbox.setLocation(position);
	}
	
	public Entity addToPosition(float x, float y){
		position.add(new Vector2f(x, y));
		return this;
		}
	public Entity addToRotation(float r){
		rotation += r;
		return this;
		}
	
	public Vector2f getPosition() {
		return position;
	}
	public Entity setPosition(Vector2f position) {
		this.position = position;
		return this;
		}
	public float getRotation() {
		return rotation;
	}
	public Entity setRotation(float rotation) {
		this.rotation = rotation;
		return this;
		}
	public Image getBasicImage() {
		return basicImage;
	}
	public Entity setBasicImage(Image basicImage) {
		this.basicImage = basicImage;
		return this;
		}
	public Shape getHitbox() {
		return hitbox;
	}
	public Entity setHitbox(Vector2f location) {
		this.hitbox.setLocation(location);
		return this;
		}
	public float getWeight() {
		return weight;
	}
	public Entity setWeight(float weight) {
		this.weight = weight;
		return this;
		}
	public Vector2f getSize() {
		return size;
	}
	public Entity setSize(Vector2f size) {
		this.size = size;
		return this;
		}
	public boolean isMovable() {
		return movable;
	}
	public Entity setMovable(boolean movable) {
		this.movable = movable;
		return this;
	}
	public boolean isTurnable() {
		return turnable;
	}
	public Entity setTurnable(boolean turnable) {
		this.turnable = turnable;
		return this;
	}
	
	
	
	
}
