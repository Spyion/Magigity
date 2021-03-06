package enitities;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import gameStates.Running;
import packets.CharacterShorts;
import tools.Toolbox;

public class OnlineCharacter extends SimulatedCharacter{

	public final byte ID;
	public final String NAME;
	public OnlineCharacter(byte ID,String name,Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight, float health) {
		super(hitbox, hitbox2, size, rotation, weight, health);
		this.ID = ID;
		NAME = name;
		clientSided = false;
	}
	
	public final Vector2f targetPosition = new Vector2f();
	public void update(int delta){
		Toolbox.approachVector(position, targetPosition, delta);
		super.update(delta);
	}
	public void setAttacking(boolean attacking){
		super.isAttacking = attacking;
	}
	public void setBlocking(boolean blocking){
		super.isBlocking = blocking;
	}
//	public void set(CharacterBooleans b){
//		boolean[] d = b.decode();
//		isMovingUp = d[0];
//		isMovingDown = d[1];
//		isMovingLeft = d[2];
//		isMovingRight = d[3];
//		isSprinting = d[4];
//		isBlocking = d[5];
//		isAttacking = d[6];
//	}
	public void set(CharacterShorts s){
		//TODO HERE SPEED CHANGE!
		super.setTargetRotationDegrees(s.rotation);
		targetPosition.x = (float) s.positionX;
		targetPosition.y = (float) s.positionY;
		Vector2f distance = Toolbox.getDistanceVector(targetPosition, position);
		Toolbox.approachVector(speed,distance.scale((float)Running.boolRate/20f),0.95f, 10);
	}
	public void setAttackAnimation(byte animation){
		currentAttackAnimation = (int) animation;
	}
	public void drawWeapon(boolean drawn){
		if(drawn)
			drawWeapon();
		else
			sheatheWeapon();
	}
	
	
	
}
