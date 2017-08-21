package terrain;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;
import components.DrawableObject;
import tools.TexturedModel;

public class Building extends CollidableObject{
	
	public static final ArrayList<Building> buildings = new ArrayList<Building>();
	public Building(TexturedModel model, Vector2f position,Vector2f size, Shape hitbox,DrawableObject parent, float rotation, float weight, boolean movable, boolean turnable){
		super(model, position, size, hitbox, parent,rotation, 1f, false, false, false);
		buildings.add(this);
	}
	public Building(Vector2f position,Vector2f size, Shape hitbox, float rotation, float weight, boolean movable, boolean turnable){
		this(null, position, size, hitbox,null , rotation, 1f, false, false);
	}
}
