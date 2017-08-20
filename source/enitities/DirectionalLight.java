package enitities;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

public class DirectionalLight {
	public final Vector3f direction;
	public Color color;
	public DirectionalLight(Vector3f direction, Color color) {
		super();
		this.direction = direction;
		this.color = color;
	}
	public DirectionalLight(){
		this(new Vector3f(0,0,-1), Color.white);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
		color.a = 1;
	}
	public void setColor(float r, float g, float b) {
		this.color = new Color(r,g,b,1);
	}
	public void setColor(int r, int g, int b) {
		this.color = new Color(r,g,b,1);
	}


}
