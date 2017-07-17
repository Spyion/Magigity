package enitities;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity {
	
	float moveSpeed;	

	public Player(Image basicImage, Shape hitbox, float rotation, float weight) {
		super(basicImage, hitbox, rotation, weight);
	}
	
	public void update(Input input, int delta){
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
		if(Math.abs(movingDirection.x) > 0 || Math.abs(movingDirection.y) > 0)
			speed.set(new Vector2f(moveSpeed*movingDirection.x*delta/1000f, moveSpeed*movingDirection.y*delta/1000f));
		System.out.print(speed.x+ " ");
		super.update(input, delta);
		System.out.println(speed.x);

	}
}
