package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import info.Information;
import shaders.Shaders;

public class DrawableObject {
	public final Vector2f position;
	public final Vector2f size;
	public Image image;
	protected float rotation;
	protected final int M = Information.METER;
	protected final static float CM = Information.CENTIMETER;
	public DrawableObject(Image image, Vector2f position, Vector2f size, float rotation) {
		super();
		this.image = image;
		this.position = position;
		this.size = size;
		setRotationDegrees(rotation);
	}
	public DrawableObject( Vector2f position, Vector2f size, float rotation) {
		this(null, position, size, rotation);
	}
	public DrawableObject(){
		this(new Vector2f(0, 0), new Vector2f(1,1),0);
	}
	public DrawableObject(Image image){
		this(image, new Vector2f(0, 0), new Vector2f(1,1),0);
	}
	public float getRotationDegrees() {
		return (float)Math.toDegrees(rotation);
	}
	public float getRotationRadians() {
		return rotation;
	}
	public void setRotationDegrees(float rotation) {
		this.rotation = (float)Math.toRadians(rotation);
	}
	public void setRotationRadians(float rotation) {
		this.rotation = rotation;
	}
	public void addToRotationDegrees(float rotation) {
		this.rotation += Math.toRadians(rotation);
	}
	public void addToRotationRadians(float rotation) {
		this.rotation += rotation;
	}
	public Image getImage() {
		return image;
	}
	public void render(Graphics g, Image image, Vector2f size, float rotation){
		if(image != null){
						
			Image toDraw = image.getScaledCopy((int)(size.x*image.getWidth()), (int)(size.y*image.getHeight()));
			Shaders.entityShader.setUniformFloatVariable("center", toDraw.getWidth()/2, toDraw.getHeight()/2);
			toDraw.rotate(getRotationDegrees()+rotation);
			toDraw.drawCentered(position.x, position.y);
		}
	}
	public void render(Graphics g, Image image, Vector2f size){
		render(g, image, size, 0);
	}
	public void render(Graphics g, Vector2f size){
		render(g, image, size);
	}
	public void render(Graphics g, Vector2f size,float rotation){
		render(g, image, size, rotation);
	}

	
}
