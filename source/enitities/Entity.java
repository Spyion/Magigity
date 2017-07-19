package enitities;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidingObject;
import debug.Debug;
import effects.Effect;

public class Entity extends CollidingObject{
	public static final ArrayList<Entity> entities=new ArrayList<Entity>();
	public final ArrayList<Effect> effects = new ArrayList<Effect>();
	
	public Entity(Image image, Shape hitbox, Vector2f size, float rotation, float weight) {
		super(image ,new Vector2f(hitbox.getCenter()),size, hitbox, (float)Math.toRadians(rotation), weight < 0 ? 0.01f : weight, true, false);
		entities.add(this);
	}
	public Entity(Image image, Shape hitbox, float rotation, float weight) {
		this(image, hitbox, new Vector2f(hitbox.getWidth(), hitbox.getHeight()), rotation, weight);
	}
	public void render(Graphics g, Camera camera){
		if(image != null){
			Image toDraw = image.getScaledCopy((int)(size.x*camera.size.x), (int)(size.y*camera.size.y));
			toDraw.rotate(getRotationDegrees());
			toDraw.rotate(camera.getRotationDegrees());
			Vector2f drawingPosition = camera.getWorldToScreenPoint(position);
			toDraw.drawCentered(drawingPosition.x, drawingPosition.y);
		}
		for(Effect effect : effects){
			effect.render(g);
		}
		if(Debug.showHitbox){
			collider.render(g, camera);
		}
	}
	@Override
	public void update(Input input, int delta){

		Iterator<Effect> iter = effects.iterator();
		while (iter.hasNext()) {
			Effect effect = iter.next();
			effect.update(delta);
		    if (effect.getLifeTime() < 0)
		        iter.remove();
		}
		
		super.update(input, delta);
	}

	
}
