package enitities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidingObject;
import debug.Debug;

public class Entity extends CollidingObject{
	public static ArrayList<Entity> entities=new ArrayList<Entity>();
	private Image basicImage;
	protected final Vector2f speed = new Vector2f(0, 0);
	
	public Entity(Image basicImage, Shape hitbox, Vector2f size, float rotation, float weight) {
		super(new Vector2f(hitbox.getCenter()),size, hitbox, (float)Math.toRadians(rotation), weight < 0 ? 0.01f : weight, true, false);
		this.basicImage = basicImage;
		entities.add(this);
	}
	public Entity(Image basicImage, Shape hitbox, float rotation, float weight) {
		this(basicImage, hitbox, new Vector2f(hitbox.getWidth(), hitbox.getHeight()), rotation, weight);
	}
	public void render(Graphics g, Camera camera){
		if(basicImage != null){
			Image image = basicImage.getScaledCopy((int)(size.x*camera.size.x), (int)(size.y*camera.size.y));
			image.rotate(getRotationDegrees());
			image.rotate(camera.getRotationDegrees());
			Vector2f drawingPosition = camera.getWorldToScreenPoint(position);
			image.drawCentered(drawingPosition.x, drawingPosition.y);
		}
		if(Debug.showHitbox){
			collider.render(g, camera);
		}
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
	public Image getBasicImage() {
		return basicImage;
	}
	public Entity setBasicImage(Image basicImage) {
		this.basicImage = basicImage;
		return this;
		}
	
	
	
	
}
