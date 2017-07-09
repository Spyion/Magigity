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
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input = new Input(Input.ANY_CONTROLLER);
		new Player(loader.loadImage("BlackCircle"), new Circle(0,0,50), 100, 100, 0, 100, 100, 1);
		new Entity(loader.loadImage("BlackCircle"), new Circle(0,0,50), 200, 100, 0, 100, 100, 1).setMovable(false);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for(Entity entity:entities){
			entity.update(input, delta);
		}
			entities.get(0).collide(entities.get(1), delta);
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

