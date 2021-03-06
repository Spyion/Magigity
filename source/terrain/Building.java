package terrain;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;

public class Building extends CollidableObject{
	
	public static final ArrayList<Building> buildings = new ArrayList<Building>();
	public Building(Image image, Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable){
		super(image, position, size, hitbox, rotation, 0, false, false, false);
		buildings.add(this);
	}
	public Building(Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable){
		this(null, position, size, hitbox, rotation, 0, false, false);
	}
}
