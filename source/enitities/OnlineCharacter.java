package enitities;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import packets.CharacterBooleans;
import packets.CharacterShorts;

public class OnlineCharacter extends SimulatedCharacter{

	public final byte ID;
	public final String NAME;
	public OnlineCharacter(byte ID,String name,Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, hitbox2, size, rotation, weight);
		this.ID = ID;
		NAME = name;
	}
	
	
	public void update(int delta){
		
		
		super.update(delta);
	}
	public void setAttacking(boolean attacking){
		super.isAttacking = attacking;
	}
	public void setBlocking(boolean blocking){
		super.isBlocking = blocking;
	}
	public void set(CharacterBooleans b){
		boolean[] d = b.decode();
		isMovingUp = d[0];
		isMovingDown = d[1];
		isMovingLeft = d[2];
		isMovingRight = d[3];
		isSprinting = d[4];
		isBlocking = d[5];
		isAttacking = d[6];
	}
	public void set(CharacterShorts s){
		super.setTargetRotationDegrees(s.rotation);
		super.position.x = (float) s.positionX;
		super.position.y = (float) s.positionY;
	}
	
}
