package particles;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;

import tools.Information;
import tools.Loader;

public class ParticleSystems {
	private static Loader loader = new Loader();
	public static final ArrayList<ParticleSystem> systems = new ArrayList<ParticleSystem>();
	public static final ParticleSystem burning = makeSystem("fireParticle"); 

	
	public static void render(Graphics g){
		for(ParticleSystem system : systems){
//			Vector2f position = Information.currentCamera.getWorldToScreenPoint(new Vector2f(0, 0));
//			system.setPosition(position.x, position.y);
			system.render();
		}
	}
	private static ParticleSystem makeSystem(String ref){
		ParticleSystem system = new ParticleSystem(loader.loadImage(ref));
		systems.add(system);
		return system;
	}
}
