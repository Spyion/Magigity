package gameStates;

import static tools.Loader.loadModel;
import static info.Information.*;
import static tools.Loader.loadTerrain;
import static shaders.Shaders.*;

import java.util.ArrayList;

import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import connections.ConnectionHandler;
//import connections.ConnectionHandler;
import debug.Debug;
import effects.ParticleEffect;
import enitities.Camera;
import enitities.DirectionalLight;
import enitities.Entity;
import enitities.Player;
import info.Information;
import packets.CharacterShorts;
import settings.KeySettings;
import shader.Shader;
import structs.OnlineCharacterCreationVars;
import terrain.Building;
import terrain.Terrain;
import tools.States;
import tools.Toolbox;


public class Running extends BasicGameState{
	
	Input input;
	ArrayList<Entity> entities = Entity.entities;
	ArrayList<Building> buildings = Building.buildings;
	Player player;
	ConnectionHandler connectionHandler;
	Camera camera = new Camera();
	DirectionalLight sun = new DirectionalLight();
	DirectionalLight moon = new DirectionalLight();
	Image fboImage;
	Terrain background;
	Entity test;
	
	@Override

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//Debug
		States.runningState = this;
		
		Information.currentCamera = camera;
		input = new Input(Input.ANY_CONTROLLER);

		background = loadTerrain("map", new Vector2f(50,50));
	}
	public boolean customInit(){
		connectionHandler = ConnectionHandler.instance;
		player = new Player(Information.PlayerID,new Circle(0,0,25*CM),new Rectangle(0,0,75*CM, 25*CM), new Vector2f(1,1), 0, 1,1000, input, camera);
		new Entity(loadModel("BlackCircle"), new Circle(0,0,25*CM), new Vector2f(1f, 1f), 0, 1, 100);
		
		//INCREASE SPEED
		//player.sprintingSpeed = 50;
		
		
		
		for(int i = 1; i < 1; i++){
//			new ParticleEffect("torch", new Entity(loadImage("BlackCircle", new Vector2f(50*CM, 50*CM)), new Circle(100*CM*i,100*CM*i,25*CM), new Vector2f(1f, 1f), 0, 1, 100), 1000);
		}
//		new ParticleEffect("torch",player, 1000000);
		connectionHandler.getCharacters();

		terrainShader.startShader();
//		terrainShader.setUniformFloatVariable("size", background.textureSize);
//		terrainShader.setUniformFloatVariable("totalWidth", background.image.getWidth());
//		terrainShader.setUniformFloatVariable("totalHeight", background.image.getHeight());
		terrainShader.setUniformIntVariable("backTex", 1);
		terrainShader.setUniformIntVariable("rTex", 2);
		terrainShader.setUniformIntVariable("gTex", 3);
		terrainShader.setUniformIntVariable("bTex", 4);
//		terrainShader.setUniformIntVariable("backSize", background.texturePack.backgroundTexture.getWidth());
//		terrainShader.setUniformIntVariable("rSize", background.texturePack.rTexture.getWidth());
//		terrainShader.setUniformIntVariable("gSize", background.texturePack.gTexture.getWidth());
//		terrainShader.setUniformIntVariable("bSize", background.texturePack.bTexture.getWidth());
		Shader.forceFixedShader();
		
		return true;
	}
	
	int boolCount = 0;
	int intCount = 0;
	public static final int boolRate = 10;
	public static final int intRate = 1000;
	private Vector2f lastPosition = new Vector2f();
	private float lastRotation;
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		boolCount+=delta;
		intCount+=delta;
		
		Entity.add();
		Entity.remove();
		OnlineCharacterCreationVars.createCharacters();
		
		
		//rotate sun
		Vector2f sunXZ = new Vector2f(sun.direction.x, sun.direction.z).add(delta/100f);
		sun.direction.set(sunXZ.x, sun.direction.y, sunXZ.y);
		
		moon.direction.set(sun.direction.x*-1, sun.direction.y*-1, sun.direction.z*-1);
		
		moon.setColor(25,50,200);
		
		
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
			boolCount = 0;
			CharacterShorts s = player.getShorts();
			if(!lastPosition.equals(new Vector2f(s.positionX, s.positionY)) && lastRotation != s.rotation){
				connectionHandler.upload(s);
				lastPosition.set(s.positionX, s.positionY);
				lastRotation = s.rotation;
			}
		}
		
		
		
		
		
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//ONSCREEN
//		g.setAntiAlias(false);

		
		//ONWORLD
		g.setBackground(Color.red);		
		Vector2f translation = camera.getWorldToScreenPoint(new Vector2f(0, 0));
		g.translate(translation.x, translation.y);
		g.rotate(0, 0, camera.getRotationDegrees());
		g.scale(camera.size.x, camera.size.y);
		int detailLevel = (int)(Math.pow(2,camera.getScroll()+4)-1);
		
		Vector2f turnedSunVector = new Vector2f(sun.direction.x, sun.direction.y).add(camera.getRotationDegrees());
		Color sunColor = sun.getColor();
		Vector2f turnedMoonVector = new Vector2f(moon.direction.x, moon.direction.y).add(camera.getRotationDegrees());
		Color moonColor = moon.getColor();
		
		float[] dirLight = {turnedSunVector.x, turnedSunVector.y, sun.direction.z, turnedMoonVector.x, turnedMoonVector.y, moon.direction.z};
		float[] dirColor = {sunColor.r, sunColor.g, sunColor.b, sunColor.a, moonColor.r, moonColor.g, moonColor.b, moonColor.a};
		float[] pLight = {0,0,0,
						  0,0,0,
						  0,0,0,
						  0,0,0};
		float[] pColor = {1,1,1,1,
				  		  0,0,0,1,
				  		  0,0,0,1,
				  		  0,0,0,1};
		
		
//		terrainShader.startShader();
//		terrainShader.setUniformFloatVariable("dirLight", dirLight);
//		terrainShader.setUniformFloatVariable("dirColor" , dirColor);
		
//		background.bindTextures(detailLevel);
//		background.render(g);
			
		
		
//		entityShader.startShader();
//		entityShader.setUniformFloatVariable("dirLight", dirLight);
//		entityShader.setUniformFloatVariable("dirColor" , dirColor);
//		entityShader.setUniformFloatVariable("pLight", pLight);
//		entityShader.setUniformFloatVariable("pColor" , pColor);
		for(Entity entity : entities){
			if(!(entity instanceof Player)){
			entity.render(g);
			}
		}
		Shader.forceFixedShader();
		
		for(Entity entity : entities){
			entity.renderEffects(g);
		}
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
		if(key == KeySettings.draw){
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

