package enitities;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import info.Information;
import tools.Toolbox;

public class SimulatedCharacter extends Character{
	float moveSpeed;	
	
	public boolean isMovingUp;
	public boolean isMovingDown;
	public boolean isMovingLeft;
	public boolean isMovingRight;
	public boolean isSprinting;
	
	public float cameraRotation;
	
	public SimulatedCharacter(Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, hitbox2, size, rotation, weight);
		moveSpeed = 5*M;
	}


		
	public void update(float cameraRotation ,int delta){
		Vector2f movingDirection = new Vector2f(0,0);
		if(isSprinting){
			moveSpeed = Toolbox.approachValue(moveSpeed, 10*M, delta);
		}else{
			moveSpeed = Toolbox.approachValue(moveSpeed, 5*M, delta);
		}
		
		if(isMovingUp){
			movingDirection.y-=1;
		}
		if(isMovingDown){
			movingDirection.y+=1;
		}
		if(isMovingLeft){
			movingDirection.x-=1;
		}
		if(isMovingRight){
			movingDirection.x+=1;
		}
		movingDirection.normalise();
		movingDirection.sub(Math.toDegrees(cameraRotation));
		if(Math.abs(movingDirection.x) > 0 || Math.abs(movingDirection.y) > 0){
			Toolbox.approachVector(speed, new Vector2f(moveSpeed*movingDirection.x, moveSpeed*movingDirection.y), delta);
		}

	}
}
