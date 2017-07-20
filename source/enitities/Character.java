package enitities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import animations.ValueAnimation;
import components.CollidableObject;
import components.Collider;
import debug.Debug;
import info.Collision;
import info.Information;
import textures.CharacterImagePack;
import tools.Loader;
import tools.Toolbox;

public class Character extends Entity{
	
	public CharacterImagePack pack;
	private float targetRotation = 0;
	public final Collider collider;
	
	public Character(CharacterImagePack pack, Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, size, rotation, weight);
		collider = new Collider(this, hitbox2);
		this.pack = pack;
		this.pack.setWeapon("basic");
		pack.leftShoulder.position.set(-25, 0);
		pack.rightShoulder.position.set(25, 0);
		pack.weapon.position.set(30, -50);
		pack.leftHand.position.set(-50, 0);
		pack.rightHand.position.set(50, 0);
		pack.leftShoe.position.set(25, 25);
		pack.rightShoe.position.set(-25, -25);
		
		
	}
	public Character(Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		this(new CharacterImagePack(), hitbox, hitbox2, size, rotation, weight);
	}
	@Override
	public void render(Graphics g){
		pack.render(g, this);
		super.render(g);
		
		if(Debug.showHitbox){
			g.draw(collider.getHitbox());
		}
	}
	@Override
	public void update(int delta){
		
		pack.weapon.update(delta, this);
		CollidableObject object = Collision.getCollidedObject(pack.weapon);
		
		setRotationRadians(Toolbox.approachValue(getRotationRadians(), targetRotation, delta)); 
		targetRotation = (float) (Toolbox.getAngle(position, Information.currentCamera.getScreenToWorldPoint(Information.getMouse()))+Math.PI/2);
		fixTargetRotation();
		
		updateAnimations(delta);
		super.update(delta);
	}
	
	
	private void updateAnimations(int delta){
		animateFeet(delta);
		animateHead(delta);
		animateShoulders(delta);

		if(!pack.weapon.isDrawn())
			animateHands(delta);
		
		animateWeapon(delta);
	}
	
	private void animateHands(int delta){
		
	}
	private void animateWeapon(int delta){
		
	}
	
	
	float shoulderDistance;
	ValueAnimation breathing =Loader.loadValueAnimation("breathing").setPingPong(true);
	private void animateShoulders(int delta){
		
		shoulderDistance = breathing.getHeight();
		breathing.update(delta);
		pack.leftShoulder.position.set(-shoulderDistance, 0);
		pack.rightShoulder.position.set(shoulderDistance, 0);
	}
	
	
	ValueAnimation lookAround = Loader.loadValueAnimation("lookAround");
	private void animateHead(int delta){
		float rotation = targetRotation-getRotationRadians();
		rotation /= 2;
		if(Information.isMouseInactive()){
			lookAround.update(delta);
			rotation += Math.toRadians(lookAround.getHeight());
			if(lookAround.isCompleted())
				lookAround.setTime(-5000);
		}else{
			lookAround.setTime(0);
		}
		pack.hat.setRotationRadians(rotation);
		pack.head.setRotationRadians(rotation);
	}
	
	boolean leftFoot = true;
	boolean leftFootIn = true;
	boolean rightFootIn = true;
	int footTime = 0;
	Vector2f footPosition = new Vector2f(0, 0);
	private final float LEGLENGTH = 40;
	private void animateFeet(int delta){
		
		footTime += delta*speed.length()/150f+1;
		
		if(footTime > 500){
			
			footTime -= 500;
			if(leftFoot){
				footPosition = Toolbox.getParentToWorldPosition(pack.leftShoe.position, this);
			}else{
				footPosition = Toolbox.getParentToWorldPosition(pack.rightShoe.position, this);
			}
			leftFoot = !leftFoot;
		}
		
		Vector2f target = speed.copy().scale(0.2f).sub(getRotationDegrees());
		
		if(leftFoot){
			Toolbox.approachVector(pack.leftShoe.position, target, delta);
			pack.rightShoe.position.set(Toolbox.getWorldToParentPosition(footPosition, this));
		}
		else{
			Toolbox.approachVector(pack.rightShoe.position, target, delta);
			pack.leftShoe.position.set(Toolbox.getWorldToParentPosition(footPosition, this));
			if(pack.leftShoe.position.length()>LEGLENGTH){
				pack.leftShoe.position.normalise().scale(LEGLENGTH);
			}
		}
		if(pack.leftShoe.position.length()>LEGLENGTH){
			pack.leftShoe.position.normalise().scale(LEGLENGTH);
		}
		if(pack.rightShoe.position.length()>LEGLENGTH){
			pack.rightShoe.position.normalise().scale(LEGLENGTH);
		}
	}
	
	@Override
	public void collide(CollidableObject object){
		super.collide(object);
		collider.collide(object);
		pack.weapon.collide(object);
	}
	public void addToTargetRotationDegrees(float rotation){
		targetRotation += Math.toRadians(rotation);
	}
	public void addToTargetRotationRadians(float rotation){
		targetRotation += rotation;
	}
	public void setTargetRotationDegrees(float rotation){
		targetRotation = (float) Math.toRadians(rotation);
	}
	public void setTargetRotationRadians(float rotation){
		targetRotation = rotation;
	}
	private void fixTargetRotation(){
		if(rotation-targetRotation>Math.PI){
			rotation-=2*Math.PI;
		}else if(rotation-targetRotation<-Math.PI){
			rotation+=2*Math.PI;
		}
	}
}
