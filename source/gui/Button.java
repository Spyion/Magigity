package gui;


import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import tools.Loader;

public class Button {
	
	final Image pressed;
	final Sound hit = Loader.loadSound("clap");
	final Sound pressSound;
	final Sound releaseSound;
	public static Sound defaultPressSound = null;
	public static Sound defaultReleaseSound = null;
	String content;
	Dimension size;
	Vector2f position;
	boolean isPressed = false;
	boolean soundPlayed = false;
	boolean isPressedAndReleased = false;
	boolean entered = false;
	boolean isPlaying = false;
	
	public Button(String content, Image pressed, Sound pressSound, Sound releaseSound, Vector2f position, Dimension size) {
		super();
		this.content = content;
		this.pressSound = pressSound;
		this.releaseSound = releaseSound;
		this.pressed = pressed;
		this.size = size;
		this.position = position;

	}
	public Button(Image pressed, Sound pressSound, Sound releaseSound, Vector2f position, Dimension size) {
		this("", pressed, pressSound, releaseSound, position, size);
	}
	public Button(Image pressed, Vector2f position, Dimension size) {
		this(pressed, defaultPressSound, defaultReleaseSound, position, size);
	}
	public Button(Image pressed, Sound pressSound, Sound releaseSound, float x, float y, int w, int h) {
		this(pressed, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(Image pressed, float x, float y, int w, int h) {
		this(pressed, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	public Button(Image pressed, Sound pressSound, Sound releaseSound, int x, int y, int w, int h) {
		this(pressed, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(Image pressed, int x, int y, int w, int h) {
		this(pressed, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	public Button(String content, Image pressed, Sound pressSound, Sound releaseSound, float x, float y, int w, int h) {
		this(content, pressed, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(String content, Image pressed, float x, float y, int w, int h) {
		this(content, pressed, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	public Button(String content, Image pressed, Sound pressSound, Sound releaseSound, int x, int y, int w, int h) {
		this(content, pressed, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(String content, Image pressed, int x, int y, int w, int h) {
		this(content, pressed, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	
	public void render(Graphics g){
		g.setAntiAlias(true);
		Image imageToDraw = pressed;
		
		if(isPressed) {
			if(entered)
				imageToDraw.draw(position.x, position.y, size.getWidth(), size.getHeight());
			else
				imageToDraw.draw(position.x, position.y, size.getWidth(), size.getHeight());
		}
		else {
			if(entered) {
				imageToDraw.draw(position.x-20, position.y-5, size.getWidth()+40, size.getHeight()+10);
				if(isPlaying == false) {
					
					hit.play();
					isPlaying = true;
					
				}
				
			}
			else {
				imageToDraw.draw(position.x, position.y, size.getWidth(), size.getHeight());
				isPlaying = false;
			}
		}

			
		g.setColor(Color.black);
		if(!content.isEmpty())
			g.drawString(content, position.x+(size.getWidth()-g.getFont().getWidth(content))/2, position.y+size.getHeight()/2-g.getFont().getHeight(content)/2);

	}
	public void update(Input input){
		int mouseY = Display.getHeight()-Mouse.getY();
		if(Mouse.getX()>position.x&&Mouse.getX()<position.x+size.getWidth()&&
				mouseY >position.y&&mouseY<position.y+size.getHeight())
		{
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				if(isPressed&&!soundPlayed){
					if(pressSound != null)
						pressSound.play();
					soundPlayed = true;
				}
				isPressed = true;
			}
			else {
				soundPlayed=false;
				if(isPressed){
					releaseSound.play();
					isPressedAndReleased = true;
					}
				isPressed = false;
			}
			entered = true;
		}
		   else {
			   entered = false;
			   if(!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			isPressed = false;
			soundPlayed = false;
		}
	}
	}
	
	public boolean isPressed(){
		return isPressed;
	}
	public boolean isPressedAndReleased(){
		if(isPressedAndReleased){
			isPressedAndReleased = false;
			return true;
		}else
			return false;
	}
	public void setSize() {
		
		
		
	}
	
}
