package components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import debug.Debug;
import enitities.Camera;
import enitities.Entity;
import tools.Toolbox;

public class Collider {

	public final CollidingObject object;
	public final Vector2f position;
	private Shape hitbox;
	private boolean enabled = true;
	private boolean dynamicCollision = Debug.dynamicCollision;
	public Collider(CollidingObject object, Shape hitbox){
		this.object = object;
		this.hitbox = hitbox;
		this.position = object.position;
	}
	public void collide(Entity e){
		collide(e.collider);
	}
	
	public void collide(Collider collider){
		if(collider.position!=null && position != null && (collider.object.isMovable() ||object.isMovable())&&collider.isEnabled()&& enabled){
				final Vector2f myObjectPosition = object.position;
				final Vector2f objectPosition = collider.position;
				if(collider.getHitboxType() instanceof Circle && getHitboxType() instanceof Circle){
					Circle objectCircle = (Circle) collider.getHitboxType();
					Circle myObjectCircle = (Circle) getHitboxType();
					double currentDistance = Toolbox.getDistance(myObjectPosition, objectPosition);
					double collidingDistance = objectCircle.radius+myObjectCircle.radius;
					
					if(currentDistance<collidingDistance)
						this.collideCircleCircle(collider, currentDistance, collidingDistance, objectCircle, myObjectCircle);
				
			
				}
				
				//Rectangle collides with Rectangle
				else if(collider.getHitboxType() instanceof Rectangle && getHitboxType() instanceof Rectangle){
					
					Polygon objectRectangle = (Polygon) collider.getHitbox();
					Polygon myObjectRectangle = (Polygon) getHitbox();
					
					double minDistance = Toolbox.getDistance(new Vector2f(myObjectRectangle.getCenter()), new Vector2f(myObjectRectangle.getPoint(0)))+
										Toolbox.getDistance(new Vector2f(objectRectangle.getCenter()), new Vector2f(objectRectangle.getPoint(0)));

					
					if(Toolbox.getDistance(collider.getHitbox().getCenterX(),collider.getHitbox().getCenterY(),
							getHitbox().getCenterX(),getHitbox().getCenterY()) < minDistance){
						
						
						Vector2f[] objectVertex = new Vector2f[4];
						Vector2f[] myObjectVertex = new Vector2f[4];
						for(int i = 0; i < objectVertex.length; i++){
							float[] point = objectRectangle.getPoint(i);
							objectVertex[i] = new Vector2f(point);
						}
						for(int i = 0; i < myObjectVertex.length; i++){
							float[] point = myObjectRectangle.getPoint(i);
							myObjectVertex[i] = new Vector2f(point);
						}
						
						Vector2f objectNearestVertex = null;
						Vector2f myObjectIntersectingPoint1 = null;
						Vector2f myObjectIntersectingPoint2 = null;
						for(int i = 0; i < objectVertex.length; i++){
							if(Toolbox.isPointInRectangle(objectVertex[i], (Rectangle)getHitboxType(), object.getRotationRadians()))
							{
								objectNearestVertex= objectVertex[i];
								for(int j = 0; j < myObjectVertex.length; j++){
									if(Toolbox.getIntersectionPoint(objectNearestVertex, new Vector2f(objectRectangle.getCenterX(), objectRectangle.getCenterY()),
											myObjectVertex[j%myObjectVertex.length], myObjectVertex[(j+1)%myObjectVertex.length]) != null)
										{
										myObjectIntersectingPoint1 = myObjectVertex[j%myObjectVertex.length];
										myObjectIntersectingPoint2 = myObjectVertex[(j+1)%myObjectVertex.length];
										break;
										}
								}
								break;
							}
						}
						Vector2f myObjectNearestVertex = null;
						Vector2f objectIntersectingPoint1 = null;
						Vector2f objectIntersectingPoint2 = null;
						for(int i = 0; i < myObjectVertex.length; i++){
							if(Toolbox.isPointInRectangle(myObjectVertex[i], (Rectangle)collider.getHitboxType(), collider.object.getRotationRadians()))
								{
									myObjectNearestVertex= myObjectVertex[i];
									for(int j = 0; j < objectVertex.length; j++){
										if(Toolbox.getIntersectionPoint(myObjectNearestVertex, new Vector2f(myObjectRectangle.getCenterX(), myObjectRectangle.getCenterY()),
												objectVertex[j%objectVertex.length], objectVertex[(j+1)%objectVertex.length]) != null)
											{
											objectIntersectingPoint1 = objectVertex[j%objectVertex.length];
											objectIntersectingPoint2 = objectVertex[(j+1)%objectVertex.length];
											break;
											}
									}
									break;
								}
							}
						
						
						if(objectNearestVertex != null && myObjectIntersectingPoint1 != null && myObjectIntersectingPoint2 != null && myObjectNearestVertex != null&&objectIntersectingPoint1 != null && objectIntersectingPoint2 != null)
						{
							collideRectRect(collider, myObjectNearestVertex, objectNearestVertex, myObjectIntersectingPoint1, myObjectIntersectingPoint2, objectIntersectingPoint1, objectIntersectingPoint2);
						}
						else{
						    if(objectNearestVertex != null && myObjectIntersectingPoint1 != null && myObjectIntersectingPoint2 != null){
						    	collider.collideRectRect(this, objectNearestVertex, myObjectIntersectingPoint1, myObjectIntersectingPoint2, myObjectVertex, objectVertex);	
						    }
						    if(myObjectNearestVertex != null&&objectIntersectingPoint1 != null && objectIntersectingPoint2 != null){
						    	collideRectRect(collider, myObjectNearestVertex, objectIntersectingPoint1, objectIntersectingPoint2, objectVertex, myObjectVertex);
						    }
						}
					}
					
					//Rectangle collides with Circle
				}else{
					Circle circle;
					Polygon rect;
					if(getHitboxType() instanceof Circle){
						circle = (Circle) getHitboxType();
						rect = (Polygon) collider.getHitbox();
					}
					else{
						circle = (Circle) collider.getHitboxType();
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
							if(getHitboxType() instanceof Circle){
								collider.collideRectangleCircle(this, intersectingPoint, circle);
							}
							else{							
								collideRectangleCircle(collider, intersectingPoint, circle);
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
								if(getHitboxType() instanceof Circle){
									collider.collideRectangleCircle(this, intersectingPoint, circle);
								}
								else{
									collideRectangleCircle(collider, intersectingPoint, circle);
								}
							}
						}
					}
				}
			}
		}
	
	private void collideCircleCircle(Collider collider, double currentDistance, double collidingDistance, Circle objectCircle, Circle myObjectCircle){
		double angle = Toolbox.getAngle(position, collider.position);
		double deltaDistance = collidingDistance-currentDistance;
		double objectDistance;
		double myObjectDistance;
		
		double sign = Math.signum(collider.position.x-object.position.x);	
		if(collider.object.isMovable()){
			if(object.isMovable()){
					double denominator = collider.object.getWeight() + object.getWeight();
					objectDistance = deltaDistance*object.getWeight()/denominator*sign;
					myObjectDistance = deltaDistance*collider.object.getWeight()/denominator*-sign;
			}else{
				objectDistance = deltaDistance*sign;
				myObjectDistance = 0;
			}
		}else{
			objectDistance = 0;
			myObjectDistance = deltaDistance*-sign;
		}
		
		
		Vector2f objectAdd = new Vector2f((float)(Math.cos(angle)*objectDistance),
											   (float)(Math.sin(angle)*objectDistance));
		Vector2f myObjectAdd = new Vector2f((float)(Math.cos(angle)*myObjectDistance),
				   						   (float)(Math.sin(angle)*myObjectDistance));
		
		if(dynamicCollision){
			object.speed.set(myObjectAdd);
			collider.object.speed.set(objectAdd);

		}else{
		position.add(myObjectAdd);
		collider.object.position.add(objectAdd);
		}
	}
	
	private void collideRectRect(Collider collider, Vector2f point, Vector2f intersectingPoint1, Vector2f intersectingPoint2, Vector2f[] objectVertex, Vector2f[] myObjectVertex){
		
		
		Vector2f orthogonal = Toolbox.getOrthogonalPoint(intersectingPoint1, intersectingPoint2, point);

		
		float weightDenominator = object.getWeight()+ collider.object.getWeight();
		float myObjectTurn = 0;
		float objectTurn = 0;
		
		
		
		Vector2f distance = new Vector2f(orthogonal.x-point.x, orthogonal.y-point.y);
		Vector2f myObjectDistance;
		Vector2f objectDistance;
		
		
		
		if(object.isTurnable()){
			
			double distances[] = new double[3];
			for(int i = 0, j = 0; i < 4; i++){
				if(!myObjectVertex[i].equals(point)){
					distances[j++]=Toolbox.getDistance(myObjectVertex[i],
							Toolbox.getOrthogonalPoint(new Vector2f(point.x+intersectingPoint2.x-intersectingPoint1.x,
													point.y+intersectingPoint2.y-intersectingPoint1.y), point, myObjectVertex[i]));
				}
			}
			
			int j = 0;
			for(int i = 1; i < 3; i++){
				if(distances[i]<distances[j]){
					j = i;
				}
			}
			myObjectTurn = (float)(Toolbox.getDistance(point, myObjectVertex[j])/distances[j]);
			
			
			if(myObjectTurn<0)
				myObjectTurn = 0;
			if(myObjectTurn>0.8)
				myObjectTurn = 0.8f;	
		}
		if(collider.object.isTurnable()){
		objectTurn = (float)(Toolbox.getDistance(intersectingPoint1, intersectingPoint2)/2/
							 Toolbox.getDistance(Toolbox.getLineHalfingPoint(intersectingPoint1, intersectingPoint2), orthogonal));
		if(objectTurn<0)
			objectTurn = 0;
		if(objectTurn>0.8)
			objectTurn = 0.8f;
		}
		
		float myObjectFactor = collider.object.getWeight() / weightDenominator *(1-myObjectTurn);
		float objectFactor = object.getWeight() / weightDenominator * (1-objectTurn);
		if(collider.object.isMovable()){
			if(object.isMovable()){
				myObjectDistance = new Vector2f(distance.x * objectFactor,distance.y * objectFactor);
				objectDistance = new Vector2f(-distance.x * myObjectFactor,-distance.y * myObjectFactor);
			}else{
				myObjectDistance = new Vector2f(0, 0);
				objectDistance = new Vector2f(-distance.x *(1-objectTurn),-distance.y * (1-objectTurn));
			}
		}else{
			myObjectDistance = new Vector2f(distance.x * (1-myObjectTurn),distance.y * (1-myObjectTurn));
			objectDistance = new Vector2f(0, 0);
		}
		if(object.isTurnable())
		{
			Vector2f turnDistance = new Vector2f(distance.x * myObjectFactor *myObjectTurn, distance.y * myObjectFactor *myObjectTurn);
			Vector2f center = new Vector2f(getHitbox().getCenterX(), getHitbox().getCenterY());
			double angle1 = Toolbox.getAngle(center, new Vector2f(point.x-turnDistance.x, point.y-turnDistance.y));
			double angle2 = Toolbox.getAngle(center, point);
			
			object.addToRotationRadians((float) (angle1-angle2));
		}
		if(collider.object.isTurnable())
		{
			Vector2f turnDistance = new Vector2f(objectDistance.x * objectFactor *objectTurn, objectDistance.y *objectFactor*objectTurn);
			Vector2f center = new Vector2f(collider.getHitbox().getCenterX(), collider.getHitbox().getCenterY());
			double angle1 = Toolbox.getAngle(center, new Vector2f(point.x-turnDistance.x, point.y-turnDistance.y));
			double angle2 = Toolbox.getAngle(center, point);
			
			collider.object.addToRotationRadians((float) ((angle2-angle1)*100));
		}
		
		
		if(dynamicCollision){
			object.speed.set(myObjectDistance);
			collider.object.speed.set(objectDistance);

		}else{
		position.add(myObjectDistance);
		collider.object.position.add(objectDistance);
		}
		
		
		
	}
	private void collideRectRect(Collider collider, Vector2f myObjectNearestVertex, Vector2f objectNearestVertex, Vector2f myObjectIntersectionPoint1, Vector2f myObjectIntersectionPoint2, Vector2f objectIntersectionPoint1 ,Vector2f objectIntersectionPoint2){
		
		Vector2f orth = Toolbox.getOrthogonalPoint(myObjectIntersectionPoint1, myObjectIntersectionPoint2, objectNearestVertex);
		Vector2f distance = Toolbox.getDistanceVector(objectNearestVertex, orth);
		Vector2f orth2 = Toolbox.getOrthogonalPoint(objectIntersectionPoint1, objectIntersectionPoint2, myObjectNearestVertex);
		Vector2f distance2 = Toolbox.getDistanceVector(orth2, myObjectNearestVertex);
		
		if(distance2.length()<distance.length())
			distance = distance2;
		
		Vector2f myObjectDistance;
		Vector2f objectDistance;
		
		if(object.isMovable()){
			if(collider.object.isMovable()){
				float denominator = object.getWeight()+collider.object.getWeight();
				myObjectDistance = new Vector2f(distance.x* collider.object.getWeight()/denominator, distance.y* object.getWeight()/denominator);
				objectDistance = new Vector2f(-distance.x* object.getWeight()/denominator, -distance.y* object.getWeight()/denominator);		
			}else{
				myObjectDistance = new Vector2f(distance.x, distance.y);
				objectDistance = new Vector2f(0, 0);
			}
		}else{
			myObjectDistance = new Vector2f(0, 0);
			objectDistance = new Vector2f(-distance.x, -distance.y);

		}
			
			
		collider.position.add(objectDistance);
		position.add(myObjectDistance);
	}
	
	private void collideRectangleCircle(Collider collider, Vector2f point, Circle circle){
		
		Vector2f distance = Toolbox.getDistanceVector(point, new Vector2f(circle.getCenter()));
		Vector2f distance2 = distance.copy();
		distance.scale(circle.radius/distance.length()).sub(distance2);
		
		Vector2f myObjectDistance;
		Vector2f objectDistance;		
		if(isMovable()){
			if(collider.isMovable()){
				float denominator = object.getWeight()+collider.object.getWeight();
				myObjectDistance = new Vector2f(distance.x* collider.object.getWeight()/denominator, distance.y* collider.object.getWeight()/denominator);
				objectDistance = new Vector2f(-distance.x* object.getWeight()/denominator, -distance.y* object.getWeight()/denominator);		
			}else{
				myObjectDistance = distance;
				objectDistance = new Vector2f(0, 0);		
			}
		}else{
			myObjectDistance = new Vector2f(0, 0);
			objectDistance = distance;		
		}
		if(dynamicCollision){
			object.speed.set(myObjectDistance);
			collider.object.speed.set(objectDistance);

		}else{
		position.add(myObjectDistance);
		collider.object.position.add(objectDistance);
		}

	}
	/**
	 * gets the rotated hitbox, therefore it will always 
	 * be a polygon and cannot be used to identify the Shape.
	 * use getHitboxType instead
	 * @return
	 */
	public Polygon getHitbox() {
		setHitboxLocation();
		return (Polygon) hitbox.transform(Transform.createRotateTransform(object.getRotationRadians(), hitbox.getCenterX(), hitbox.getCenterY()));
	}
	public Shape getHitboxType(){
		setHitboxLocation();
		return hitbox;
	}
	public void setHitboxLocation(){
		hitbox.setCenterX(position.x);
		hitbox.setCenterY(position.y);
	}
	public boolean isEnabled() {
		return enabled;
	}
	public Collider setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}
	public void render(Graphics g, Camera camera){
		g.setColor(Color.red);
		Polygon toDraw = (Polygon) getHitbox().transform(Transform.createScaleTransform(camera.size.x, camera.size.y)).
				transform(Transform.createRotateTransform((float)(camera.getRotationRadians()),position.x, position.y));
		Vector2f drawingPosition = camera.getWorldToScreenPoint(position);
		toDraw.setCenterX(drawingPosition.x);
		toDraw.setCenterY(drawingPosition.y);
		g.draw(toDraw);
	}
	public boolean isMovable(){
		return object.isMovable();
	}
	public Collider setMovable(boolean movable){
		object.setMovable(movable);
		return this;
	}
	
}
