package enitities;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;
import effects.Effect;

public class Entity extends CollidableObject{
	public static final ArrayList<Entity> entities=new ArrayList<Entity>();
	public static final ArrayList<Entity> toAdd=new ArrayList<Entity>();
	public static final ArrayList<Entity> toRemove=new ArrayList<Entity>();
	public final ArrayList<Effect> effects = new ArrayList<Effect>();
	
	public Entity(Image image, Shape hitbox, Vector2f size, float rotation, float weight) {
		super(image ,new Vector2f(hitbox.getCenter()),size, hitbox, (float)Math.toRadians(rotation), weight < 0 ? 0.01f : weight, true, false, false);
		toAdd.add(this);
	}
	public Entity(Image image, Shape hitbox, float rotation, float weight) {
		this(image, hitbox, new Vector2f(hitbox.getWidth(), hitbox.getHeight()), rotation, weight);
	}
	public Entity(Shape hitbox, Vector2f size, float rotation, float weight) {
		this(null, hitbox, size, rotation, weight);
	}
	public void render(Graphics g){
		super.render(g, size);
	}
	public void renderEffects(Graphics g){
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
	public static synchronized void add(){
		for(Entity entity:toAdd){
			entities.add(entity);
		}
		toAdd.clear();
	}
	public static synchronized void remove(){
		for(Entity entity:toRemove){
			entities.remove(entity);
		}
		toRemove.clear();
	}
	public static void remove(Entity e){
		toRemove.add(e);
	}
	
}
