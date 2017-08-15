package enitities;

import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;
import connections.ConnectionHandler;
import info.Collision;
import info.Information;
import packets.CharacterShorts;
import packets.hasHit;
import settings.KeySettings;
import tools.Toolbox;

public class Player extends SimulatedCharacter {
	private final Input input;
	private final Camera camera;
	public final byte ID;
	public Player(byte ID,Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight, float health,Input input, Camera camera) {
		super(hitbox, hitbox2, size, rotation, weight, health);
		this.ID = ID;
		this.input = input;
		this.camera = camera;
	}
	ArrayList<CollidableObject> hitObjects = new ArrayList<CollidableObject>();

	public void update(int delta){	
		
		if(isAttacking){
			CollidableObject object = Collision.getCollidedObject(pack.weapon);
			if(object != null && !hitObjects.contains(object)){
				if(object instanceof OnlineCharacter){
					OnlineCharacter e = (OnlineCharacter) object;
					e.increaseHealth(-pack.weapon.damage);
					hitObjects.add(e);
					ConnectionHandler.instance.upload(new hasHit(ID, e.ID, pack.weapon.damage));
				}
			}
		}else{
			hitObjects.clear();
		}
		if(input.isKeyDown(KeySettings.up))
			isMovingUp = true;
		else
			isMovingUp = false;
		if(input.isKeyDown(KeySettings.down))
			isMovingDown = true;
		else
			isMovingDown = false;
		if(input.isKeyDown(KeySettings.left))
			isMovingLeft = true;
		else
			isMovingLeft = false;
		if(input.isKeyDown(KeySettings.right))
			isMovingRight = true;
		else
			isMovingRight = false;
		if(input.isKeyDown(KeySettings.sprint))
			isSprinting = true;
		else
			isSprinting = false;
		
		cameraRotation = camera.getRotationRadians();
		targetRotation = (float)(Toolbox.getAngle(position, Information.currentCamera.getScreenToWorldPoint(Information.getMouse()))+Math.PI/2);
		super.update(delta);
		
	}
//	public CharacterBooleans getBools(){
//		return new CharacterBooleans(ID, isMovingUp, isMovingDown, isMovingLeft, isMovingRight, isSprinting, isBlocking, isAttacking);
//	}
	public CharacterShorts getShorts(){
		return new CharacterShorts(ID, (short)Math.toDegrees(targetRotation), (int)position.x, (int)position.y);
	}
}
