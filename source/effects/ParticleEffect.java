package effects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleSystem;

import enitities.Entity;
import info.Information;
import tools.Loader;
import tools.Toolbox;

public class ParticleEffect extends Effect{
	
	private static final int M = Information.METER;
	private static final float CM = Information.CENTIMETER;
	
	private ParticleSystem system;
	private ConfigurableEmitter emitter;
	public ParticleEffect(String particleRef, String particleSystemRef,Entity entity, int lifeTime){
		super(entity, lifeTime+1000);
		system = new ParticleSystem(Loader.loadImage(particleRef));
		emitter = Loader.loadEmitter(particleSystemRef);
		Toolbox.scaleEmitter(emitter, CM);
		
		system.addEmitter(emitter);
		system.setBlendingMode(ParticleSystem.BLEND_COMBINE);
	}

	public ParticleEffect(String particleSystemRef,Entity entity, int lifeTime){
		this("particle", particleSystemRef, entity, lifeTime);
	}
	public ParticleEffect(String particleSystemRef, Entity entity){
		this(particleSystemRef, entity, 5000);
	}
	public ParticleEffect(String particleRef, String particleSystemRef, Entity entity){
		this(particleRef, particleSystemRef, entity, 5000);
	}
	@Override
	public void update(int delta){	
		
		system.update(delta);
		
		if(super.getLifeTime() < 1000)
			emitter.initialLife.setMax(0);
			emitter.initialLife.setMin(0);
		
		super.update(delta);
	}

	@Override
	public void render(Graphics g){
		Vector2f entityPosition = entity.position;
		emitter.setPosition(entityPosition.x, entityPosition.y, false);
		system.render(0, 0);
	}

}
