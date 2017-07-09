package enitities;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity {

	public Player(Image basicImage, Shape hitbox, Vector2f position, float rotation, Vector2f size, float weight) {
		super(basicImage, hitbox, position, rotation, size, weight);
	}

	public Player(Image basicImage, Shape hitbox, float x, float y, float rotation, float sx, float sy, float weight) {
		super(basicImage, hitbox, x, y, rotation, sx, sy, weight);
	}
	@Override
	public void update(Input input, int delta){
		if(input.isKeyDown(Input.KEY_W)){
			super.addToPosition(0, 400*-delta/1000f);
		}
		if(input.isKeyDown(Input.KEY_S)){
			super.addToPosition(0, 400*delta/1000f);

		}
		if(input.isKeyDown(Input.KEY_A)){
			super.addToPosition(400*-delta/1000f, 0);

		}
		if(input.isKeyDown(Input.KEY_D)){
			super.addToPosition(400*delta/1000f, 0);

		}
	}
}
