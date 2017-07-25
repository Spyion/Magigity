package enitities;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import info.Information;
import packets.CharacterBooleans;
import packets.CharacterShorts;
import tools.Toolbox;

public class Player extends SimulatedCharacter {
	private final Input input;
	private final Camera camera;
	public final byte ID;
	public Player(byte ID,Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight, Input input, Camera camera) {
		super(hitbox, hitbox2, size, rotation, weight);
		this.ID = ID;
		this.input = input;
		this.camera = camera;
	}
	public void update(int delta){		
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
	public CharacterBooleans getBools(){
		return new CharacterBooleans(ID, isMovingUp, isMovingDown, isMovingLeft, isMovingRight, isSprinting, isBlocking, isAttacking);
	}
	public CharacterShorts getShorts(){
		return new CharacterShorts(ID, (short)Math.toDegrees(targetRotation), (int)position.x, (int)position.y);
	}
}
