package enitities;

import javax.tools.Tool;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import connections.mysqlconn;
import tools.Toolbox;

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
		
		if(booleanUpdateTime>booleanUpdateRate){
			booleanUpdateTime-=booleanUpdateRate;
			isMovingUp = Toolbox.parseBoolean(mysqlconn.getData(ID, "upBool"));
			isMovingDown = Toolbox.parseBoolean(mysqlconn.getData(ID, "downBool"));
			isMovingLeft = Toolbox.parseBoolean(mysqlconn.getData(ID, "leftBool"));
			isMovingRight = Toolbox.parseBoolean(mysqlconn.getData(ID, "rightBool"));
			isSprinting =Toolbox.parseBoolean( mysqlconn.getData(ID, "isSprinting"));
			cameraRotation=Float.parseFloat(mysqlconn.getData(ID, "cameraRotation"));
			System.out.println(isMovingDown);
		}
		if(positionUpdateTime > positionUpdateRate){
			positionUpdateTime -= positionUpdateRate;
//			position.set(Float.parseFloat(mysqlconn.getData(ID, "posX")),
//						 Float.parseFloat(mysqlconn.getData(ID, "posY")));
		}

		
		
		
		
		
		
		
		
		super.update(rotation, delta);
	}
	
}
