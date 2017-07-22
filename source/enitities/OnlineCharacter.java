package enitities;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import connections.mysqlconn;

public class OnlineCharacter extends SimulatedCharacter{

	public final String ID;
	public OnlineCharacter(String ID,Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, hitbox2, size, rotation, weight);
		this.ID = ID;
	}

	private int booleanUpdateTime;
	private int positionUpdateTime;
	
	private final int booleanUpdateRate = 100;
	private final int positionUpdateRate = 1000;
	public void update(int delta){
		
		booleanUpdateTime += delta;
		positionUpdateTime += delta;
		
		if(booleanUpdateTime>booleanUpdateTime){
			booleanUpdateTime-=booleanUpdateRate;
//			isMovingUp = mysqlconn.getData(ID, "up");
//			isMovingDown = mysqlconn.getDown(ID);
//			isMovingLeft = mysqlconn.getLeft(ID);
//			isMovingRight = mysqlconn.getRight(ID);
//			isSprinting = mysqlconn.getSprinting(ID);
		}
		if(positionUpdateTime > positionUpdateRate){
			positionUpdateTime -= positionUpdateRate;
//			position.set(mysqlconn.getPosX(ID), mysqlconn.getPosY(ID);
			}

		
		
		
		
		
		
		
		
		super.update(rotation, delta);
	}
	
}
