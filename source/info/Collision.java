package info;

import java.util.ArrayList;

import components.CollidableObject;

public class Collision {
	public static ArrayList<Collision> collisions = new ArrayList<Collision>();
	
	private final ArrayList<CollidableObject> objects = new ArrayList<CollidableObject>();
	
	public Collision(CollidableObject object1, CollidableObject object2) {
		super();
		objects.add(object1);
		objects.add(object2);
		boolean add = true;
		for(Collision collision : collisions){
			if(this.equals(collision)){
				add = false;
				break;
			}
		}
		if(add){
			collisions.add(this);
		}
	}
	public Collision(CollidableObject object1, CollidableObject object2, boolean remove) {
		super();
		objects.add(object1);
		objects.add(object2);
		if(remove){
			Collision toRemove = null;
			for(Collision collision : collisions){
				if(this.equals(collision)){
					toRemove = collision;
				}
			}
			if(toRemove != null)
				collisions.remove(toRemove);
		}
	}
	public static CollidableObject getCollidedObject(CollidableObject object){
		for(Collision collision : collisions){
			if(collision.objects.contains(object)){
				return collision.objects.get((collision.objects.indexOf(object)+1)%2);
			}
		}
		return null;
	}
	
	public boolean equals(Collision collision) {
		return objects.containsAll(collision.objects);
	}
}
