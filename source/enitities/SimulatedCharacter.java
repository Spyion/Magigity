package enitities;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tools.Toolbox;

public class SimulatedCharacter extends Character{
	float moveSpeed;	
	
	public boolean isMovingUp = false;
	public boolean isMovingDown = false;
	public boolean isMovingLeft = false;
	public boolean isMovingRight = false;
	public boolean isSprinting = false;
	
	public float cameraRotation;
	
	public SimulatedCharacter(Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight, float health) {
		super(hitbox, hitbox2, size, rotation, weight, health);
		moveSpeed = 5*M;
	}


		
	public void update(int delta){
		if(clientSided){
		Vector2f movingDirection = new Vector2f(0,0);
		if(isSprinting){
			moveSpeed = Toolbox.approachValue(moveSpeed, 5*M, delta);
		}else{
			moveSpeed = Toolbox.approachValue(moveSpeed, 2*M, delta);
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
		super.update(delta);
	}
}
