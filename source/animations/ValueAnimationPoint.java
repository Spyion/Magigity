package animations;

import org.newdawn.slick.geom.Vector2f;

public class ValueAnimationPoint {
	public final Vector2f position;
	public float pitch;
	public ValueAnimationPoint(Vector2f position, float pitch) {
		super();
		this.position = position;
		this.pitch = pitch;
	}
	public ValueAnimationPoint(float x, float y, float pitch){
		super();
		this.position = new Vector2f(x, y);
		this.pitch = pitch;
	}
	
}
