package enitities;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tools.Toolbox;

public class Player extends Character {
	
	float moveSpeed;	

	
	public Player(Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, hitbox2, size, rotation, weight);
	}


	public void update(Input input,Camera camera ,int delta){
		Vector2f movingDirection = new Vector2f(0,0);
		moveSpeed = input.isKeyDown(Input.KEY_LSHIFT) ? 1000 : 500;
		
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
		movingDirection.sub(camera.getRotationDegrees());
		if(Math.abs(movingDirection.x) > 0 || Math.abs(movingDirection.y) > 0){
			Toolbox.approachVector(speed, new Vector2f(moveSpeed*movingDirection.x, moveSpeed*movingDirection.y), delta);
		}

	}
}
