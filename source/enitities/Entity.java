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

import debug.Debug;
import tools.Toolbox;

public class Entity {
	public static ArrayList<Entity> entities=new ArrayList<Entity>();
	
	private Vector2f position;
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
		this.rotation = (float)Math.toRadians(rotation);
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
		if(entity.position!=null && position != null && (entity.isMovable() || movable)){
				if(entity.hitbox instanceof Circle && hitbox instanceof Circle){
					Circle entityCircle = (Circle) entity.hitbox;
					Circle myCircle = (Circle) hitbox;
					double currentDistance = Toolbox.getDistance(position, entity.getPosition());
					double collidingDistance = entityCircle.radius+myCircle.radius;
					
					if(currentDistance<collidingDistance)
						this.collideCircleCircle(entity, currentDistance, collidingDistance, entityCircle, myCircle);
				
			
				}
				
				//Rectangle collides with Rectangle
				else if(entity.hitbox instanceof Rectangle && hitbox instanceof Rectangle){
					
					Polygon entityRectangle = (Polygon) entity.getHitbox();
					Polygon myRectangle = (Polygon) getHitbox();
					
					double minDistance = Toolbox.getDistance(new Vector2f(myRectangle.getCenter()), new Vector2f(myRectangle.getPoint(0)))+
										Toolbox.getDistance(new Vector2f(entityRectangle.getCenter()), new Vector2f(entityRectangle.getPoint(0)));

					
					if(Toolbox.getDistance(entity.getHitbox().getCenterX(),entity.getHitbox().getCenterY(),
							getHitbox().getCenterX(),getHitbox().getCenterY()) < minDistance){
						
						
						Vector2f[] entityVertex = new Vector2f[4];
						Vector2f[] myVertex = new Vector2f[4];
						for(int i = 0; i < entityVertex.length; i++){
							float[] point = entityRectangle.getPoint(i);
							entityVertex[i] = new Vector2f(point);
						}
						for(int i = 0; i < myVertex.length; i++){
							float[] point = myRectangle.getPoint(i);
							myVertex[i] = new Vector2f(point);
						}
						
						Vector2f entityNearestVertex = null;
						Vector2f myIntersectingPoint1 = null;
						Vector2f myIntersectingPoint2 = null;
						for(int i = 0; i < entityVertex.length; i++){
							if(Toolbox.isPointInRectangle(entityVertex[i], (Rectangle)hitbox, rotation))
							{
								entityNearestVertex= entityVertex[i];
								for(int j = 0; j < myVertex.length; j++){
									if(Toolbox.getIntersectionPoint(entityNearestVertex, new Vector2f(entityRectangle.getCenterX(), entityRectangle.getCenterY()),
											myVertex[j%myVertex.length], myVertex[(j+1)%myVertex.length]) != null)
										{
										myIntersectingPoint1 = myVertex[j%myVertex.length];
										myIntersectingPoint2 = myVertex[(j+1)%myVertex.length];
										break;
										}
								}
								break;
							}
						}
						Vector2f myNearestVertex = null;
						Vector2f entityIntersectingPoint1 = null;
						Vector2f entityIntersectingPoint2 = null;
						for(int i = 0; i < myVertex.length; i++){
							if(Toolbox.isPointInRectangle(myVertex[i], (Rectangle)entity.hitbox, entity.rotation))
								{
									myNearestVertex= myVertex[i];
									for(int j = 0; j < entityVertex.length; j++){
										if(Toolbox.getIntersectionPoint(myNearestVertex, new Vector2f(myRectangle.getCenterX(), myRectangle.getCenterY()),
												entityVertex[j%entityVertex.length], entityVertex[(j+1)%entityVertex.length]) != null)
											{
											entityIntersectingPoint1 = entityVertex[j%entityVertex.length];
											entityIntersectingPoint2 = entityVertex[(j+1)%entityVertex.length];
											break;
											}
									}
									break;
								}
							}
						
						
						if(entityNearestVertex != null && myIntersectingPoint1 != null && myIntersectingPoint2 != null && myNearestVertex != null&&entityIntersectingPoint1 != null && entityIntersectingPoint2 != null)
						{
							collideRectRect(entity, myNearestVertex, entityNearestVertex, myIntersectingPoint1, myIntersectingPoint2, entityIntersectingPoint1, entityIntersectingPoint2);
						}
						else{
						    if(entityNearestVertex != null && myIntersectingPoint1 != null && myIntersectingPoint2 != null){
						    	entity.collideRectRect(this, entityNearestVertex, myIntersectingPoint1, myIntersectingPoint2, myVertex, entityVertex);	
						    }
						    if(myNearestVertex != null&&entityIntersectingPoint1 != null && entityIntersectingPoint2 != null){
						    	collideRectRect(entity, myNearestVertex, entityIntersectingPoint1, entityIntersectingPoint2, entityVertex, myVertex);
						    }
						}
					}
					
					//Rectangle collides with Circle
				}else{
					Circle circle;
					Polygon rect;
					if(hitbox instanceof Circle){
						circle = (Circle) hitbox;
						rect = (Polygon) entity.getHitbox();
					}
					else{
						circle = (Circle) entity.hitbox;
						rect = (Polygon) getHitbox();
					}
					
					Vector2f circlePosition = new Vector2f(circle.getCenter());
					
					if(Toolbox.getDistance(circlePosition, new Vector2f(rect.getCenter()))
							<circle.radius+Toolbox.getDistance(new Vector2f(rect.getCenter()), new Vector2f(rect.getPoint(0)))){
					
						Vector2f[] rectVertex = new Vector2f[4];
						for(int i = 0; i < rectVertex.length; i++){
							float[] point = rect.getPoint(i);
							rectVertex[i] = new Vector2f(point);
						}
						Vector2f intersectingPoint = null;
						
						// check if circle collides with edges
						for(int i = 0; i < rectVertex.length; i++){
							Vector2f orth = Toolbox.getOrthogonalPoint(rectVertex[i%rectVertex.length], rectVertex[(i+1)%rectVertex.length], circlePosition);
							double distance = Toolbox.getDistance(rectVertex[i%rectVertex.length], rectVertex[(i+1)%rectVertex.length]);
							// isOrthogonal on the line?
							if(Toolbox.getDistance(orth, rectVertex[i%rectVertex.length])< distance && Toolbox.getDistance(orth, rectVertex[(i+1)%rectVertex.length])<distance)
							{
								if(Toolbox.getDistance(circlePosition, orth)<circle.radius)
								{
									intersectingPoint = orth;
								}
							}
						}
						
						
						if(intersectingPoint != null){
							if(hitbox instanceof Circle){
								entity.collideRectangleCircle(this, intersectingPoint, circle);
							}
							else{							
								collideRectangleCircle(entity, intersectingPoint, circle);
							}
						}
						// check if circle collides with corners
						else{
							for(int i = 0; i < rectVertex.length; i++){
								// Does the circle touch the corner?
								if(Toolbox.getDistance(rectVertex[i%rectVertex.length], circlePosition) < circle.radius)
								{
									intersectingPoint = rectVertex[i%rectVertex.length];
								}
							}
							if(intersectingPoint != null){
								if(hitbox instanceof Circle){
									entity.collideRectangleCircle(this, intersectingPoint, circle);
								}
								else{
									collideRectangleCircle(entity, intersectingPoint, circle);
								}
							}
						}
					}
				}
			}
		}
	
	private void collideCircleCircle(Entity entity, double currentDistance, double collidingDistance, Circle entityCircle, Circle myCircle){
		double angle = Toolbox.getAngle(position, entity.getPosition());
		double deltaDistance = collidingDistance-currentDistance;
		double entityDistance;
		double myDistance;
		
		double sign = Math.signum(entity.getPosition().x-position.x);	
		if(entity.isMovable()){
			if(movable){
					double denominator = entity.weight + weight;
					entityDistance = deltaDistance*entity.weight/denominator*sign;
					myDistance = deltaDistance*weight/denominator*-sign;
			}else{
				entityDistance = deltaDistance*sign;
				myDistance = 0;
			}
		}else{
			entityDistance = 0;
			myDistance = deltaDistance*-sign;
		}
		
		
		Vector2f entityPosition = new Vector2f(entity.getPosition().x+(float)(Math.cos(angle)*entityDistance),
											   entity.getPosition().y+(float)(Math.sin(angle)*entityDistance));
		Vector2f myPosition = new Vector2f(position.x+(float)(Math.cos(angle)*myDistance),
				   						   position.y+(float)(Math.sin(angle)*myDistance));
		
		if(turnable)
			addToRotation(turn(position, myPosition, entity.position));
		if(entity.isTurnable())
			entity.addToRotation(turn(entity.position, entityPosition, position));
		
		position = myPosition;
		entity.position = entityPosition;
		
	}
	
	private void collideRectRect(Entity entity, Vector2f point, Vector2f intersectingPoint1, Vector2f intersectingPoint2, Vector2f[] entityVertex, Vector2f[] myVertex){
		
		
		Vector2f orthogonal = Toolbox.getOrthogonalPoint(intersectingPoint1, intersectingPoint2, point);

		
		float weightDenominator = weight+ entity.weight;
		float myTurn = 0;
		float entityTurn = 0;
		
		
		
		Vector2f distance = new Vector2f(orthogonal.x-point.x, orthogonal.y-point.y);
		Vector2f myDistance;
		Vector2f entityDistance;
		
		
		
		if(turnable){
			
			double distances[] = new double[3];
			for(int i = 0, j = 0; i < 4; i++){
				if(!myVertex[i].equals(point)){
					distances[j++]=Toolbox.getDistance(myVertex[i],
							Toolbox.getOrthogonalPoint(new Vector2f(point.x+intersectingPoint2.x-intersectingPoint1.x,
													point.y+intersectingPoint2.y-intersectingPoint1.y), point, myVertex[i]));
				}
			}
			
			int j = 0;
			for(int i = 1; i < 3; i++){
				if(distances[i]<distances[j]){
					j = i;
				}
			}
			myTurn = (float)(Toolbox.getDistance(point, myVertex[j])/distances[j]);
			
			
			if(myTurn<0)
				myTurn = 0;
			if(myTurn>0.8)
				myTurn = 0.8f;	
		}
		if(entity.isTurnable()){
		entityTurn = (float)(Toolbox.getDistance(intersectingPoint1, intersectingPoint2)/2/
							 Toolbox.getDistance(Toolbox.getLineHalfingPoint(intersectingPoint1, intersectingPoint2), orthogonal));
		if(entityTurn<0)
			entityTurn = 0;
		if(entityTurn>0.8)
			entityTurn = 0.8f;
		}
		
		float myFactor = weight / weightDenominator *(1-myTurn);
		float entityFactor = entity.weight / weightDenominator * (1-entityTurn);
		if(entity.isMovable()){
			if(movable){
				myDistance = new Vector2f(distance.x * entityFactor,distance.y * entityFactor);
				entityDistance = new Vector2f(-distance.x * myFactor,-distance.y * myFactor);
			}else{
				myDistance = new Vector2f(0, 0);
				entityDistance = new Vector2f(-distance.x *(1-entityTurn),-distance.y * (1-entityTurn));
			}
		}else{
			myDistance = new Vector2f(distance.x * (1-myTurn),distance.y * (1-myTurn));
			entityDistance = new Vector2f(0, 0);
		}
		if(turnable)
		{
			Vector2f turnDistance = new Vector2f(distance.x * myFactor *myTurn, distance.y * myFactor *myTurn);
			Vector2f center = new Vector2f(getHitbox().getCenterX(), getHitbox().getCenterY());
			double angle1 = Toolbox.getAngle(center, new Vector2f(point.x-turnDistance.x, point.y-turnDistance.y));
			double angle2 = Toolbox.getAngle(center, point);
			
			rotation += angle1-angle2;
		}
		if(entity.isTurnable())
		{
			Vector2f turnDistance = new Vector2f(entityDistance.x * entityFactor *entityTurn, entityDistance.y *entityFactor*entityTurn);
			Vector2f center = new Vector2f(entity.getHitbox().getCenterX(), entity.getHitbox().getCenterY());
			double angle1 = Toolbox.getAngle(center, new Vector2f(point.x-turnDistance.x, point.y-turnDistance.y));
			double angle2 = Toolbox.getAngle(center, point);
			
			entity.rotation += (angle2-angle1)*100;
		}
		
		
		entity.position.add(entityDistance);
		position.add(myDistance);
		
		
		
	}
	private void collideRectRect(Entity entity, Vector2f myNearestVertex, Vector2f entityNearestVertex, Vector2f myIntersectionPoint1, Vector2f myIntersectionPoint2, Vector2f entityIntersectionPoint1 ,Vector2f entityIntersectionPoint2){
		
		Vector2f orth = Toolbox.getOrthogonalPoint(myIntersectionPoint1, myIntersectionPoint2, entityNearestVertex);
		Vector2f distance = Toolbox.getDistanceVector(entityNearestVertex, orth);
		Vector2f orth2 = Toolbox.getOrthogonalPoint(entityIntersectionPoint1, entityIntersectionPoint2, myNearestVertex);
		Vector2f distance2 = Toolbox.getDistanceVector(orth2, myNearestVertex);
		
		if(distance2.length()<distance.length())
			distance = distance2;
			
		float denominator = weight+entity.weight;
		Vector2f myDistance = new Vector2f(distance.x* weight/denominator, distance.y* weight/denominator);
		Vector2f entityDistance = new Vector2f(-distance.x* entity.weight/denominator, -distance.y* entity.weight/denominator);		
			
		entity.position.add(entityDistance);
		position.add(myDistance);
	}
	
	private void collideRectangleCircle(Entity entity, Vector2f point, Circle circle){
		
		Vector2f distance = Toolbox.getDistanceVector(point, new Vector2f(circle.getCenter()));
		Vector2f distance2 = distance.copy();
		distance.scale(circle.radius/distance.length()).sub(distance2);
		
		Vector2f myDistance;
		Vector2f entityDistance;		
		if(movable){
			if(entity.isMovable()){
				float denominator = weight+entity.weight;
				myDistance = new Vector2f(distance.x* weight/denominator, distance.y* weight/denominator);
				entityDistance = new Vector2f(-distance.x* entity.weight/denominator, -distance.y* entity.weight/denominator);		
			}else{
				myDistance = distance;
				entityDistance = new Vector2f(0, 0);		
			}
		}else{
			myDistance = new Vector2f(0, 0);
			entityDistance = distance;		
		}
		
		entity.position.add(entityDistance);
		position.add(myDistance);
	}
	
	private float turn(Vector2f before, Vector2f after, Vector2f target){
		
		//TODO if Possible
		return 0;
	}

	public void render(Graphics g){
		Image image = basicImage.getScaledCopy((int)size.x, (int)size.y);
		image.rotate((float) Math.toDegrees(rotation));
		image.drawCentered(position.x, position.y);
		
		
		if(Debug.showHitbox){
			g.setColor(Color.red);
			g.draw(getHitbox());
		}
	}
	
	public void update(Input input, int delta){
		hitbox.setCenterX(position.x);
		hitbox.setCenterY(position.y);
		rotation += 0.1f;
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
	/**
	 * not yet working correctly.
	 * @return
	 */
	public boolean isTurnable() {
		return turnable;
	}
	/**
	 * not yet working correctly.
	 * @return
	 */
	public Entity setTurnable(boolean turnable) {
		this.turnable = turnable;
		return this;
	}
	
	
	
	
}
