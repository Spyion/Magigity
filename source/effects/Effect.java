package effects;

import org.newdawn.slick.Graphics;

import enitities.Entity;
import tools.Loader;

public class Effect {
	protected Loader loader = new Loader();
	public final Entity entity;
	private int lifeTime;
	public Effect(Entity entity, int lifeTime){
		this.entity = entity;
		this.lifeTime = lifeTime;
		entity.effects.add(this);
	}
	public void update(int delta){
		if(lifeTime < 1000000)
			lifeTime -= delta;
	}
	public void render(Graphics g){
	}
	public int getLifeTime() {
		return lifeTime;
	}
}
