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
	
	
	public void update(int delta){
		
		
		super.update(delta);
	}
	public void setAttacking(boolean attacking){
		super.isAttacking = attacking;
	}
	public void setBlocking(boolean blocking){
		super.isBlocking = blocking;
	}
	
}
