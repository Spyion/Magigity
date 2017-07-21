package weapons;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import animations.ValueAnimation;

public class Bow extends Weapon{

	public Bow(int type, Image drawn, Image sheathed, Shape hitbox, Vector2f anchor, Vector2f upperHandPosition,
			Vector2f lowerHandPosition, float upperHandRotation, float lowerHandRotation,
			ArrayList<ArrayList<ValueAnimation>> animations) {
		super(type, drawn, sheathed, hitbox, anchor, upperHandPosition, lowerHandPosition, upperHandRotation, lowerHandRotation,
				animations);
		// TODO Auto-generated constructor stub
	}

	

	
}
