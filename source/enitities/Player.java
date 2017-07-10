package enitities;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity {
	
	float moveSpeed;
	
	public Player(Image basicImage, Shape hitbox, Vector2f position, float rotation, Vector2f size, float weight) {
		super(basicImage, hitbox, position, rotation, size, weight);
	}

	public Player(Image basicImage, Shape hitbox, float x, float y, float rotation, float sx, float sy, float weight) {
		super(basicImage, hitbox, x, y, rotation, sx, sy, weight);
	}
	@Override
	public void update(Input input, int delta){
		super.update(input, delta);
//		super.addToRotation(delta/1000f);
		Vector2f movingDirection = new Vector2f(0,0);
		moveSpeed = input.isKeyDown(Input.KEY_LSHIFT) ? 500 : 100;
		
		if(input.isKeyDown(Input.KEY_W)){
			movingDirection.y-=1;
		}
		if(input.isKeyDown(Input.KEY_S)){
			movingDirection.y+=1;
		}
		if(input.isKeyDown(Input.KEY_A)){
			movingDirection.x-=1;
		}
		if(input.isKeyDown(Input.KEY_D)){
			movingDirection.x+=1;
		}
		movingDirection.normalise();
		
		super.addToPosition(movingDirection.x*moveSpeed*delta/1000f, movingDirection.y*moveSpeed*delta/1000f);
	}
}
