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
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.pbuffer.FBOGraphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import connections.ConnectionHandler;
//import connections.ConnectionHandler;
import debug.Debug;
import effects.ParticleEffect;
import enitities.Camera;
import enitities.Entity;
import enitities.OnlineCharacter;
import enitities.Player;
import info.Information;
import shader.Shader;
import shaders.Shaders;
import structs.OnlineCharacterCreationVars;
import terrain.Building;
//import shaders.EntityShader;
import tools.Loader;
import tools.States;
import tools.Toolbox;
public class Running extends BasicGameState{
	
	Input input;
	ArrayList<Entity> entities = Entity.entities;
	ArrayList<Building> buildings = Building.buildings;
	Player player;
	OnlineCharacter test;
	ConnectionHandler connectionHandler;
	Camera camera = new Camera();
	//EntityShader entityShader = new EntityShader();
	private final int M = Information.METER;
	private final float CM = Information.CENTIMETER;
	
	@Override

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//Debug
		States.runningState = this;
		
	
		
		Information.currentCamera = camera;
		input = new Input(Input.ANY_CONTROLLER);


				
	}
	public boolean customInit(){
		connectionHandler = ConnectionHandler.instance;
		player = new Player(Information.PlayerID,new Circle(0,0,25*CM),new Rectangle(0,0,75*CM, 25*CM), new Vector2f(1,1), 0, 1, input, camera);
//		for(int i = 1; i < 10; i++){
//			new ParticleEffect("torch", new Entity(Loader.loadImage("BlackCircle", new Vector2f(50*CM, 50*CM)), new Circle(100*CM*i,100*CM*i,25*CM), new Vector2f(1f, 1f), 0, 1), 1000);
//		}
		new ParticleEffect("torch",player, 1000000);
		
		connectionHandler.getCharacters();

		
		return true;
	}
	
	int boolCount = 0;
	int intCount = 0;
	public static final int boolRate = 100;
	public static final int intRate = 1000;
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		boolCount+=delta;
		intCount+=delta;
		
		
		Entity.add();
		Entity.remove();
		OnlineCharacterCreationVars.createCharacters();
		
		
		
		
		for(int i = 0; i < entities.size()-1; i++)
		{
			for(int j = i+1; j < entities.size(); j++)
			entities.get(i).collide(entities.get(j));
		}
		for(int i = 0; i < entities.size(); i++){
			for(int j = 0; j < buildings.size(); j++){
				entities.get(i).collide(buildings.get(j));
			}
		}
		
		for(Entity entity:entities){
			entity.update(delta);
		}
		
		
		
		camera.update(input, delta);
		if(Information.isMouseInactive()){
			camera.targetPosition.set(player.position);
		}else{
			camera.targetPosition.set(Toolbox.getLineDivision(player.position, 3, camera.getScreenToWorldPoint(Information.getMouse()), 1));
		}
		camera.targetPosition.add(player.pack.leftShoulder.position.copy().add(player.getRotationDegrees()).add(90));
		Toolbox.approachVector(camera.position, camera.targetPosition, delta);
		Toolbox.approachVector(camera.size, camera.zoom, delta);
		camera.setRotationRadians(Toolbox.approachValue(camera.getRotationRadians(), camera.getTargetRotationRadians(), delta));
		Information.update(delta);
		
		if(boolCount > boolRate){
			boolCount -= boolRate;
//			connectionHandler.uploadBools(player);
			connectionHandler.uploadShorts(player);
		}
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
//		Image fboImage = Loader.loadImage("terrain/grass");
//		FBOGraphics fbo = new FBOGraphics(fboImage);
		//ONSCREEN
//		g.setAntiAlias(false);
		g.setColor(Color.green);
		g.fillRect(0, 0, Display.getWidth(), Display.getHeight());		
		
		
		//ONWORLD
		Vector2f translation = camera.getWorldToScreenPoint(new Vector2f(0, 0));
		g.translate(translation.x, translation.y);
		g.rotate(0, 0, camera.getRotationDegrees());
		g.scale(camera.size.x, camera.size.y);
		Shaders.entityShader.startShader();
		for(Entity entity : entities){
			entity.render(g);
		}
		Shader.forceFixedShader();
		for(Entity entity : entities){
			entity.renderEffects(g);
		}
//		Shader.forceFixedShader();
		for(Building building : buildings){
//			building.render(g);
		}

		//Debug
		g.setColor(Color.red);
		for(Vector2f point : Debug.debugPoints){
			Debug.setDebugPoint(point.x, point.y);
			g.fillOval(point.x, point.y, 10, 10);
			}
		Debug.debugPoints.clear();
		g.resetTransform();
		
		
		//OnScreen
//		fboImage.draw(0, 0, Display.getWidth(), Display.getHeight());
		
	}
	private boolean actionPerformed = false;
	@Override
	public void mousePressed(int button, int x, int y) {
		if(!actionPerformed){
			if(button == Input.MOUSE_LEFT_BUTTON){
				player.setAttacking();
			}else if(button == Input.MOUSE_RIGHT_BUTTON){
				player.setBlocking();

			}
			actionPerformed = true;
		}
		super.mousePressed(button, x, y);
	}
	@Override
	public void mouseReleased(int button, int x, int y) {
		actionPerformed = false;
		super.mouseReleased(button, x, y);
	}

	@Override
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_LEFT)
			camera.addToTargetRotationDegrees(-90);
		if(key == Input.KEY_RIGHT)
			camera.addToTargetRotationDegrees(90);
		if(key == Input.KEY_R){
			boolean b = player.pack.weapon.isDrawn();
			if(b){
				player.sheatheWeapon();
			}else{
				player.drawWeapon();
			}
				
				
				
			connectionHandler.uploadDrawn(!b);
		}
		super.keyPressed(key, c);
	}
	
	@Override
	public int getID() {
		return States.running;
	}
}

