
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import animations.ValueAnimation;
import animations.ValueAnimationPoint;
import enitities.Camera;
import tools.CSVHandler;
import tools.Toolbox;

public class ValueAnimationCreator extends BasicGame {
	
	ValueAnimation animation = new ValueAnimation();
	public ValueAnimationCreator(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	Input input;
	Camera camera;
	CSVHandler csv = new CSVHandler();
	boolean play = false;
	final String root = "data/ValueAnimations/"; 
	final Vector2f mouse = new Vector2f(0, 0);
	boolean[] mousePressed = new boolean[3];
	public static void main (String[] args){
		AppGameContainer app;
		try {
			app = new AppGameContainer(new ValueAnimationCreator("Hello"));
			app.setDisplayMode(1080, 720, false);
			app.setAlwaysRender(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		g.setColor(Color.black);
		Vector2f todraw = camera.getWorldToScreenPoint(new Vector2f(0,0));
		Vector2f todraw2 = camera.getWorldToScreenPoint(new Vector2f(1000000,0));
		g.drawLine(todraw.x, todraw.y, todraw2.x, todraw2.y);
		todraw = camera.getWorldToScreenPoint(new Vector2f(0, 100000));
		todraw2 = camera.getWorldToScreenPoint(new Vector2f(0, -10000));
		g.drawLine(todraw.x, todraw.y, todraw2.x, todraw2.y);
		
		
		g.setColor(Color.blue);
		
		ArrayList<Float> points= new ArrayList<Float>();
		for(int i = 0; i < Display.getWidth(); i++){
				points.add(-animation.getHeight(camera.getScreenToWorldPoint(new Vector2f(i, 0)).x));
		}
		for(int i = 0; i < points.size()-2; i++){
			float height1 = camera.getWorldToScreenPoint(new Vector2f(0, Float.valueOf(points.get(i)))).y;
			float height2 = camera.getWorldToScreenPoint(new Vector2f(0, Float.valueOf(points.get(i+1)))).y;
			g.drawLine(i, height1, i+1, height2);
		}
		
		for(ValueAnimationPoint point: animation.points){
			Vector2f position = point.position.copy();
			position = new Vector2f(camera.getWorldToScreenPoint(position));
			position.y = Display.getHeight()-position.y;
			g.setColor(Color.red);
			Vector2f pitch= new Vector2f(1, -point.pitch);
			pitch.normalise().scale(30).add(position);
			g.drawLine(position.x, position.y, pitch.x, pitch.y);
			g.setColor(Color.blue);
			g.draw(new Circle(position.x, position.y, 30));
		}
		
		
		for(int i = 0; i < Display.getWidth(); i+=50){
				todraw = camera.getScreenToWorldPoint(new Vector2f(i, 0));
				g.drawString(Integer.toString((int)todraw.x), i, Display.getHeight()-20);
		}
		for(int i = 0; i < Display.getHeight()-50; i+=50){
			todraw = camera.getScreenToWorldPoint(new Vector2f(0, i));
			g.drawString(Integer.toString((int)-todraw.y), 10, i);
		}
		if(play){
			g.setColor(Color.green);
			todraw = camera.getWorldToScreenPoint(new Vector2f(animation.getTime(),0));

			todraw2 = camera.getWorldToScreenPoint(new Vector2f(animation.getTime(),0));
			g.drawLine(todraw.x, 0, todraw2.x, Display.getHeight());
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		input = new Input(Input.ANY_CONTROLLER);
		camera = new Camera(true);
		camera.position.set(100, 0);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		camera.update(input, delta);
		camera.size.set(camera.zoom.scale(2f));
		camera.position.y = 0;
		ValueAnimationPoint toRemove = null;
		if(play){
			animation.update(delta);
		}
		if(mousePressed[0])
			if(input.isKeyDown(Input.KEY_LSHIFT)){
				boolean inRange = false;
				for(ValueAnimationPoint point: animation.points){
					if(Math.abs(point.position.x - camera.getScreenToWorldPoint(mouse).x)<5||Toolbox.getDistance(camera.getWorldToScreenPoint(point.position),mouse )<30){
						inRange = true;
						break;
					}
				}
				if(!inRange)
				animation.points.add(new ValueAnimationPoint(camera.getScreenToWorldPoint(mouse),0));
			}
		ArrayList<ValueAnimationPoint> index = new ArrayList<ValueAnimationPoint>();
		for(ValueAnimationPoint point: animation.points){
			if(mousePressed[0])
				if(!input.isKeyDown(Input.KEY_LSHIFT))
				{
						if(Toolbox.getDistance(camera.getWorldToScreenPoint(point.position),mouse )<30){
								point.position.set(camera.getScreenToWorldPoint(mouse));
								index.add(point);
						}
				}
			if(mousePressed[1]){
				Vector2f screenPoint = camera.getWorldToScreenPoint(point.position);
				if(Toolbox.getDistance(screenPoint, mouse)<30){
					Vector2f distance = Toolbox.getDistanceVector(screenPoint, mouse);
					point.pitch = distance.y/distance.x;
				}
			}
			if(mousePressed[2])
				if(Toolbox.getDistance(camera.getWorldToScreenPoint(point.position),mouse )<30)
					toRemove = point;
		}
		
		for(int i = 1; i < index.size(); i++){
			animation.points.remove(index.get(i));
		}
		
		if(toRemove != null)
			animation.points.remove(toRemove);
		animation.updateCurves();
	}
	@Override
	public void mousePressed(int button, int x, int y) {
		mousePressed[button] = true;
		mouse.set(x, Display.getHeight()-y);
	}
	@Override
	public void mouseReleased(int button, int x, int y) {
		mousePressed[button] = false;
	}
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		mouse.set(newx, Display.getHeight()-newy);
		super.mouseDragged(oldx, oldy, newx, newy);
	}
	@Override
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_S){
			save();
		}
		if(key == Input.KEY_D||key == Input.KEY_L){
			load();
		}
		if(key == Input.KEY_A|| key == Input.KEY_P || key == Input.KEY_ENTER){
			play = true;
			animation.setTime(0);
		}

	}
	private void save(){
		String s = JOptionPane.showInputDialog("Save Name:");
		if(s != null && !s.equals("")){
			try {
				FileReader fr = new FileReader(root+s);
				int override = JOptionPane.showConfirmDialog(new JPanel(), "File exist, Override?");
				if(override == 0){
					csv.writeCSV(root+s, animation.getStringArray());
				}
			} catch (FileNotFoundException e) {
				csv.writeCSV(root+s, animation.getStringArray());
			}
		}
	}
	private void load(){
		String s = JOptionPane.showInputDialog("Load Name:");
		if(s != null && !s.equals("")){
			try {
				FileReader fr = new FileReader(root+s);
				animation = new ValueAnimation(root+s);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
