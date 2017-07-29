package enitities;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;
import effects.Effect;
import shaders.Shaders;

public class Entity extends CollidableObject{
	public static final ArrayList<Entity> entities=new ArrayList<Entity>();
	public static final ArrayList<Entity> toAdd=new ArrayList<Entity>();
	public static final ArrayList<Entity> toRemove=new ArrayList<Entity>();
	public final ArrayList<Effect> effects = new ArrayList<Effect>();
	
	
	private float maxHealth;
	private float health;
	
	
	
	
	
	
	
	
	
	
	public Entity(Image image, Shape hitbox, Vector2f size, float rotation, float weight, float health) {
		super(image ,new Vector2f(hitbox.getCenter()),size, hitbox, (float)Math.toRadians(rotation), weight < 0 ? 0.01f : weight, true, false, false);
		this.maxHealth = health;
		this.health = health;
		toAdd.add(this);
	}
	public Entity(Image image, Shape hitbox, float rotation, float weight, float health) {
		this(image, hitbox, new Vector2f(hitbox.getWidth(), hitbox.getHeight()), rotation, weight, health);
	}
	public Entity(Shape hitbox, Vector2f size, float rotation, float weight, float health) {
		this(null, hitbox, size, rotation, weight, health);
	}
	public void render(Graphics g){
		super.render(g, size);
	}
	public void renderEffects(Graphics g){
		for(Effect effect : effects){
			effect.render(g);
		}
	}
	protected void loseHealth(float health){
		this.health -= health;
		if(health < 0)
			die();
	}
	protected void gainHealth(float health){
		this.health += health;
		if(health > maxHealth)
			health = maxHealth;
	}
	protected void die(){
		position.set(0,0);
		health = maxHealth;
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
