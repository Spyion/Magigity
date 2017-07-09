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

public class Button {
	
	final Image pressed;
	final Image released;
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
	
	public Button(String content, Image pressed, Image released, Sound pressSound, Sound releaseSound, Vector2f position, Dimension size) {
		super();
		this.content = content;
		this.pressSound = pressSound;
		this.releaseSound = releaseSound;
		this.pressed = pressed;
		this.released = released;
		this.size = size;
		this.position = position;
	}
	public Button(Image pressed, Image released, Sound pressSound, Sound releaseSound, Vector2f position, Dimension size) {
		this("", pressed, released, pressSound, releaseSound, position, size);
	}
	public Button(Image pressed, Image released, Vector2f position, Dimension size) {
		this(pressed, released, defaultPressSound, defaultReleaseSound, position, size);
	}
	public Button(Image pressed, Image released, Sound pressSound, Sound releaseSound, float x, float y, int w, int h) {
		this(pressed, released, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(Image pressed, Image released, float x, float y, int w, int h) {
		this(pressed, released, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	public Button(Image pressed, Image released, Sound pressSound, Sound releaseSound, int x, int y, int w, int h) {
		this(pressed, released, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(Image pressed, Image released, int x, int y, int w, int h) {
		this(pressed, released, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	public Button(String content, Image pressed, Image released, Sound pressSound, Sound releaseSound, float x, float y, int w, int h) {
		this(content, pressed, released, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(String content, Image pressed, Image released, float x, float y, int w, int h) {
		this(content, pressed, released, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	public Button(String content, Image pressed, Image released, Sound pressSound, Sound releaseSound, int x, int y, int w, int h) {
		this(content, pressed, released, pressSound, releaseSound, new Vector2f(x, y), new Dimension(w, h));
	}
	public Button(String content, Image pressed, Image released, int x, int y, int w, int h) {
		this(content, pressed, released, defaultPressSound, defaultReleaseSound, x, y, w, h);
	}
	
	public void render(Graphics g){
		g.setAntiAlias(true);
		Image imageToDraw;
		Color color;
		if(isPressed)
			imageToDraw = pressed;
		else
			imageToDraw = released;
		if(entered)
			color = Color.blue;
		else
			color = Color.white;
			
		imageToDraw.draw(position.x, position.y, size.getWidth(), size.getHeight(),color);
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
		return isPressedAndReleased;
	}
	
}
