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
	
	private int booleanUpdateTime;
	private int positionUpdateTime;
	
	private final int booleanUpdateRate = 100;
	private final int positionUpdateRate = 1000;
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
		booleanUpdateTime += delta;
		positionUpdateTime += delta;
		
		if(booleanUpdateTime > booleanUpdateRate){
			booleanUpdateTime -= booleanUpdateRate;
//			mysqlconn.setUp(ID, isMovingUp);
//			mysqlconn.setDown(ID, isMovingDown);
//			mysqlconn.setLeft(ID, isMovingLeft);
//			mysqlconn.setRight(ID, isMovingRight);
//			mysqlconn.setSprinting(ID, isSprinting);
//			mysqlconn.setRotation(ID, rotation);
//			mysqlconn.setCameraRotation(ID, camera.getRotationRadians());

		}
		if(positionUpdateTime > positionUpdateRate){
			positionUpdateTime -= positionUpdateRate;
//			mysqlconn.setPosX(ID, position.x);
//			mysqlconn.setPosY(ID, position.y);
			}
		
		
		
		
		
		
		super.update(camera.getRotationRadians(), delta);
		
	}
}
