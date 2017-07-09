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
					double distance = Math.sqrt(Math.pow(position.x-entity.position.x, 2)+Math.pow(position.y-entity.position.y, 2));
					double collidingDistance = entityCircle.radius+myCircle.radius;
					if(distance<collidingDistance)
						CollideCircleCircle(entity, delta);
				}
	}
	
	private void CollideCircleCircle(Entity entity, int delta){
		Circle entityCircle = (Circle) entity.getHitbox();
		Circle myCircle = (Circle) hitbox;
		float deltaWeight1 = weight/entity.weight*delta/10f;
		float deltaWeight2 = entity.weight/weight*delta/10f;
		/*float weightSign;
		if(deltaWeight1 > deltaWeight1){
			deltaWeight2 *= 1/deltaWeight1;
			deltaWeight1 = 1;
			weightSign = 1;

		}else{
			deltaWeight1 *= 1/deltaWeight2;
			deltaWeight2 = 1;
			weightSign = -1;
		}*/
		double radians = Math.atan(((entity.getPosition().y-position.y)/(entity.getPosition().x-position.x)));
		double distance = entityCircle.radius+myCircle.radius;
		float sign = Math.signum(entity.getPosition().x-position.x);
		Vector2f entityPosition = new Vector2f(entity.getPosition().x+(float)Math.cos(radians)*deltaWeight1*sign,
											   entity.getPosition().y+(float)Math.sin(radians)*deltaWeight1*sign);
		Vector2f myPosition = new Vector2f(position.x+(float)Math.cos(radians)*deltaWeight2*-sign,
				   						   position.y+(float)Math.sin(radians)*deltaWeight2*-sign);
		double newDistance = Math.sqrt(Math.pow(myPosition.x-entityPosition.x, 2)+Math.pow(myPosition.y-entityPosition.y, 2));
		if(newDistance>distance)
		{
			double deltaDistance = newDistance-distance;
			System.out.println(deltaDistance);
			Vector2f entityAdd = new Vector2f(	(float)(sign*Math.cos(radians)*deltaDistance/2.1),
												(float)(sign*Math.sin(radians)*deltaDistance/2.1));
			Vector2f myAdd = new Vector2f(		(float)(-sign*Math.cos(radians)*deltaDistance/2.1),
										  		(float)(-sign*Math.sin(radians)*deltaDistance/2.1));
			System.out.println("("+entityAdd.x+"/"+entityAdd.y+") ("+myAdd.x+"/"+myAdd.y+")");
			
			position = myPosition.add(myAdd);
			entity.position = entityPosition.add(entityAdd);
		}
		else
		{
			position = myPosition;
			entity.position = entityPosition;
		}
		
	}

	public void render(Graphics g){
		Image image = basicImage.copy();
		image.rotate(rotation);
		image.draw(position.x, position.y, size.x, size.y);
		
	}
	
	public void update(Input input, int delta){
		this.hitbox.setLocation(position);
	}
	
	public void addToPosition(float x, float y){
		position.add(new Vector2f(x, y));
	}
	public void addToRotation(float r){
		rotation += r;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public Image getBasicImage() {
		return basicImage;
	}
	public void setBasicImage(Image basicImage) {
		this.basicImage = basicImage;
	}
	public Shape getHitbox() {
		return hitbox;
	}
	public void setHitbox(Vector2f location) {
		this.hitbox.setLocation(location);
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public Vector2f getSize() {
		return size;
	}
	public void setSize(Vector2f size) {
		this.size = size;
	}
	
	
}
