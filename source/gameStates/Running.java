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
		player = new Player(loader.loadImage("BlackCircle"), new Circle(0,0,50), 200, 200, 45, 100, 100, 100);
		Random random = new Random();
		for(int i = 0; i < 100; i++){
			if((random.nextInt() & 1) == 0){
				int width= random.nextInt(100);
				width = width < 20? 20 : width;
				int height= random.nextInt(100);
				height = height < 20? 20 : height;
				
				new Entity(loader.loadImage("basicbutton_released"), new Rectangle(0,0,width,height),
						random.nextInt(Display.getHeight()), random.nextInt(Display.getWidth()), random.nextInt(360), width, height, random.nextFloat()*5);
			}
			else{
				int width= random.nextInt(100);
				width = width < 20? 20 : width;
				new Entity(loader.loadImage("BlackCircle"), new Circle(0,0,width/2),
						random.nextInt(Display.getHeight()), random.nextInt(Display.getWidth()), random.nextInt(360), width, width, random.nextFloat()*5);
			}
		}
		new Entity(loader.loadImage("BlackCircle"), new Circle(0,0,200), 400, 400, 45, 400, 400, 1).setMovable(false);

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
//		g.setAntiAlias(true);
		g.setColor(Color.green);
		g.fillRect(0, 0, Display.getWidth(), Display.getHeight());		
		for(Entity entity : entities){
			entity.render(g);
		}
		
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

