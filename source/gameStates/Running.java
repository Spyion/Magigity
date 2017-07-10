package gameStates;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import enitities.Entity;
import enitities.Player;
import tools.Loader;
import tools.States;

public class Running extends BasicGameState{
	
	Loader loader = new Loader();
	Input input;
	ArrayList<Entity> entities = Entity.entities;
	Player player;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input = new Input(Input.ANY_CONTROLLER);
		player = new Player(loader.loadImage("basicbutton_released"), new Rectangle(0,0,100,100), 100, 100, 45, 100, 100, 1);
		new Entity(loader.loadImage("basicbutton_released"), new Rectangle(0,0,100,100), 200, 100, 0, 100, 100, 1);
		new Entity(loader.loadImage("basicbutton_released"), new Rectangle(0,0,100,100), 200, 200, 0, 100, 100, 1);
		new Entity(loader.loadImage("basicbutton_released"), new Rectangle(0,0,100,100), 200, 300, 0, 100, 100, 1);
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
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(true);
		g.setColor(Color.green);
		g.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		
		
		
		for(Entity entity : entities){
			entity.render(g);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return States.running;
	}

}

