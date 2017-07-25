package enitities;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import connections.mysqlconn;
import info.Information;
import tools.Toolbox;

public class Player extends SimulatedCharacter {

	public final String ID;
	public Player(String ID,Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, hitbox2, size, rotation, weight);
		this.ID = ID;
	}
	public void update(Input input, Camera camera, int delta){
		if(input.isKeyDown(Input.KEY_W))
			isMovingUp = true;
		else
			isMovingUp = false;
		if(input.isKeyDown(Input.KEY_S))
			isMovingDown = true;
		else
			isMovingDown = false;
		if(input.isKeyDown(Input.KEY_A))
			isMovingLeft = true;
		else
			isMovingLeft = false;
		if(input.isKeyDown(Input.KEY_D))
			isMovingRight = true;
		else
			isMovingRight = false;
		if(input.isKeyDown(Input.KEY_LSHIFT))
			isSprinting = true;
		else
			isSprinting = false;
		
		cameraRotation = camera.getRotationRadians();
		targetRotation = (float)(Toolbox.getAngle(position, Information.currentCamera.getScreenToWorldPoint(Information.getMouse()))+Math.PI/2);
		super.update(delta);
		
	}
}
