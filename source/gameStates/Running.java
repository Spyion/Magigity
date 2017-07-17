package gameStates;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import debug.Debug;
import enitities.Camera;
import enitities.Entity;
import enitities.Player;
//import shaders.EntityShader;
import tools.Loader;
import tools.States;
public class Running extends BasicGameState{
	
	Loader loader = new Loader();
	Input input;
	ArrayList<Entity> entities = Entity.entities;
	Player player;
	Camera camera = new Camera(new Vector2f(0, 0), new Vector2f(1, 1), 0);
	//EntityShader entityShader = new EntityShader();
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input = new Input(Input.ANY_CONTROLLER);
		player = new Player(loader.loadImage("basicbutton_released"), new Rectangle(0,0,50,50), 100, 1);
		new Entity(null, new Rectangle(300, 444, 50, 50), 0, 1);
		new Entity(null, new Rectangle(301, 555, 50, 50), 0, 1);
		new Entity(null, new Rectangle(302, 666, 50, 50), 0, 1);
		new Entity(null, new Rectangle(303, 277, 50, 50), 0, 1);
		new Entity(null, new Rectangle(304, 222, 50, 50), 0, 1);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for(Entity entity:entities){
			entity.update(input, delta);
		}
		for(int i = 0; i < entities.size()-1; i++)
		{
			for(int j = i+1; j < entities.size(); j++)
			entities.get(i).collide(entities.get(j));
		}
		camera.followPosition(player.position, delta);

	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(true);
		g.setColor(Color.green);
		g.fillRect(0, 0, Display.getWidth(), Display.getHeight());		
		
		
		//entityShader.start();
		for(Entity entity : entities){
			entity.render(g, camera);
		}
		
		
		
		//entityShader.stop();

		//Debug
		g.setColor(Color.red);
		for(Vector2f point : Debug.debugPoints){
			Debug.setDebugPoint(point.x, point.y);
			g.draw(Debug.debugPoint);
			}
		Debug.debugPoints.clear();
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return States.running;
	}

}

