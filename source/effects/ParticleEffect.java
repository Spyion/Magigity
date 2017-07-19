package effects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;

import enitities.Entity;
import particles.ParticleSystems;
import tools.Information;

public class ParticleEffect extends Effect{
	
	private ParticleSystem system;
	private ConfigurableEmitter emitter;
	public ParticleEffect(String particleRef, String particleSystemRef,Entity entity, int lifeTime){
		super(entity, lifeTime);
		system = new ParticleSystem(loader.loadImage(particleRef));
		emitter = loader.loadEmitter(particleSystemRef);
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
		super.update(delta);
	}
	private int i;

	@Override
	public void render(Graphics g){
		
		Vector2f position = Information.currentCamera.getWorldToScreenPoint(new Vector2f(0, 0));
		Vector2f entityPosition = entity.position;
		g.translate(position.x, position.y);
		g.rotate(0, 0, Information.currentCamera.getRotationDegrees());
		g.scale(Information.currentCamera.size.x, Information.currentCamera.size.y);
		emitter.setPosition(entityPosition.x, entityPosition.y, false);
		system.render(0, 0);
		
		g.resetTransform();
	}

}
