package enitities;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;
import debug.Debug;
import effects.Effect;
import info.Information;

public class Entity extends CollidableObject{
	public static final ArrayList<Entity> entities=new ArrayList<Entity>();
	public final ArrayList<Effect> effects = new ArrayList<Effect>();
	
	public Entity(Image image, Shape hitbox, Vector2f size, float rotation, float weight) {
		super(image ,new Vector2f(hitbox.getCenter()),size, hitbox, (float)Math.toRadians(rotation), weight < 0 ? 0.01f : weight, true, false, false);
		entities.add(this);
	}
	public Entity(Image image, Shape hitbox, float rotation, float weight) {
		this(image, hitbox, new Vector2f(hitbox.getWidth(), hitbox.getHeight()), rotation, weight);
	}
	public Entity(Shape hitbox, Vector2f size, float rotation, float weight) {
		this(null, hitbox, size, rotation, weight);
	}
	public void render(Graphics g){
		super.render(g, size);
		for(Effect effect : effects){
			effect.render(g);
		}
	}
	@Override
	public void update(int delta){

		Iterator<Effect> iter = effects.iterator();
		while (iter.hasNext()) {
			Effect effect = iter.next();
			effect.update(delta);
		    if (effect.getLifeTime() < 0)
		        iter.remove();
		}
		
		super.update(delta);
	}

	
}
