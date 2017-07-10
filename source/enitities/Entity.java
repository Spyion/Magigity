package enitities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import tools.Toolbox;

public class Entity {
	public static ArrayList<Entity> entities=new ArrayList<Entity>();
	
	private Vector2f position;
	private Vector2f lastPosition;
	private float rotation;
	private Image basicImage;
	public Shape hitbox;
	private float weight;
	private Vector2f size;
	private boolean movable = true;
	private boolean turnable = false;
	
	public Entity(Image basicImage, Shape hitbox, Vector2f position, float rotation, Vector2f size, float weight) {
		super();
		this.position = position;
		this.lastPosition = position;
		this.rotation = rotation;
		this.basicImage = basicImage;
		this.hitbox = hitbox;
		this.weight = weight < 0 ? weight : 0.01f;
		this.size = size;
		entities.add(this);
	}
	public Entity(Image basicImage, Shape hitbox, float x, float y, float rotation ,float sx, float sy, float weight){
		this(basicImage, hitbox, new Vector2f(x, y), rotation, new Vector2f(sx, sy), weight);
	}
	
	public void collide(Entity entity){
		if(entity.position!=null && position != null){
				if(entity.hitbox instanceof Circle && hitbox instanceof Circle){
					Circle entityCircle = (Circle) entity.getHitbox();
					Circle myCircle = (Circle) hitbox;
					double currentDistance = Toolbox.getDistance(position, entity.getPosition());
					double collidingDistance = entityCircle.radius+myCircle.radius;
					if(currentDistance<collidingDistance)
						this.CollideCircleCircle(entity, currentDistance, collidingDistance, entityCircle, myCircle);
				}else if(entity.hitbox instanceof Rectangle && hitbox instanceof Rectangle){
					
					double entityWidth = entity.hitbox.getWidth()>entity.hitbox.getHeight()?entity.hitbox.getWidth():entity.hitbox.getHeight();
					double myWidth = hitbox.getWidth()>hitbox.getHeight()?hitbox.getWidth():hitbox.getHeight();
					double minDistance = Math.sqrt(2)*(entityWidth+myWidth);

					
					if(Toolbox.getDistance(entity.getHitbox().getCenterX(),entity.getHitbox().getCenterY(),
							getHitbox().getCenterX(),getHitbox().getCenterY()) < minDistance){
						
						Polygon entityRectangle = (Polygon) entity.getHitbox();
						Polygon myRectangle = (Polygon) getHitbox();
						Vector2f[] entityVertex = new Vector2f[4];
						Vector2f[] myVertex = new Vector2f[4];
						for(int i = 0; i < entityVertex.length; i++){
							float[] point = entityRectangle.getPoint(i);
							entityVertex[i] = new Vector2f(point[0], point[1]);
						}
						for(int i = 0; i < myVertex.length; i++){
							float[] point = myRectangle.getPoint(i);
							myVertex[i] = new Vector2f(point[0], point[1]);
						}
						double[] entityDistances =  new double[4];
						double[] myDistances =  new double[4];
						for(int i = 0; i < entityVertex.length; i++){
							entityDistances[i] = Toolbox.getDistance(entityVertex[i], position);
						}
						for(int i = 0; i < myVertex.length; i++){
							myDistances[i] = Toolbox.getDistance(myVertex[i], entity.getPosition());
						}
						double firstDistance = Double.MAX_VALUE;
						double secondDistance = Double.MAX_VALUE;
						int[] place = new int[4];
						for(int i = 0; i < entityDistances.length; i++){
							if(firstDistance<secondDistance){
								if(entityDistances[i]<secondDistance){
								secondDistance = entityDistances[i];
								place[1] = i;
								}
							}else{
								if(entityDistances[i]<firstDistance){
									firstDistance = entityDistances[i];
									place[0] = i;
									}
							}
						}
							firstDistance =  Double.MAX_VALUE;
							secondDistance = Double.MAX_VALUE;
						for(int i = 0; i < myDistances.length; i++){
							if(firstDistance<secondDistance){
								if(myDistances[i]<secondDistance){
								secondDistance = myDistances[i];
								place[3] = i;
								}
							}else{
								if(myDistances[i]<firstDistance){
									firstDistance = myDistances[i];
									place[2] = i;
									}
							}
						}
						Vector2f intersection = Toolbox.getIntersection(
								entityVertex[place[0]], entityVertex[place[1]], myVertex[place[2]], myVertex[place[3]]);
						if(intersection != null){
							collideRectRect(entity, intersection);
						}
						
						
				}
			}
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
	
	private void collideRectRect(Entity entity, Vector2f intersection){
		System.out.println("Collided");
	}
	
	
	
	private float turn(Vector2f before, Vector2f after, Vector2f target){
		
		//TODO if Possible
		return 0;
	}

	public void render(Graphics g){
		Image image = basicImage.getScaledCopy((int)size.x, (int)size.y);
		image.rotate((float) Math.toDegrees(rotation));
		image.drawCentered(position.x, position.y);
		g.setColor(Color.red);
		g.draw(getHitbox());
		
	}
	
	public void update(Input input, int delta){
		hitbox.setCenterX(position.x);
		hitbox.setCenterY(position.y);
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
		return hitbox.transform(Transform.createRotateTransform(rotation, hitbox.getCenterX(), hitbox.getCenterY()));
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
