package components;

import org.newdawn.slick.geom.Vector2f;

public class DrawableObject {
	public final Vector2f position;
	public final Vector2f size;
	float rotation;
	public DrawableObject(Vector2f position, Vector2f size, float rotation) {
		super();
		this.position = position;
		this.size = size;
		this.rotation = (float) Math.toDegrees(rotation);
	}
	public DrawableObject(){
		this(new Vector2f(0, 0), new Vector2f(1,1),0);
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

	
}
