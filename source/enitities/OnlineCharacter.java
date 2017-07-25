package enitities;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import gameStates.Running;
import packets.CharacterBooleans;
import packets.CharacterShorts;
import tools.Toolbox;

public class OnlineCharacter extends SimulatedCharacter{

	public final byte ID;
	public final String NAME;
	public OnlineCharacter(byte ID,String name,Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, hitbox2, size, rotation, weight);
		this.ID = ID;
		NAME = name;
		clientSided = false;
	}
	
	public final Vector2f targetPosition = new Vector2f();
	public void update(int delta){
		Vector2f distance = Toolbox.getDistanceVector(position, targetPosition);
		
		position.add(distance.scale(delta/Running.boolRate));
		speed.set(distance.scale(1000/delta));
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
		targetPosition.x = (float) s.positionX;
		targetPosition.y = (float) s.positionY;
	}
	
}
