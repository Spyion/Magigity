package enitities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import animations.ValueAnimation;
import components.Collider;
import components.DrawableObject;
import components.CollidableObject;
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
	private final int M = Information.METER;
	private final float CM = Information.CENTIMETER;
	
	public Character(CharacterImagePack pack, Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight) {
		super(hitbox, size, rotation, weight);
		collider = new Collider(this, hitbox2);
		this.pack = pack;
		this.pack.setWeapon("basic");
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
		
//		pack.weapon.update(delta, this);
//		CollidableObject object = Collision.getCollidedObject(pack.weapon);
		
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

//		if(!pack.weapon.isDrawn())
			animateHands(delta);
		
//		animateWeapon(delta);
	}
	
	private void animateHands(int delta){
		
		Vector2f position = pack.rightShoe.position.copy();
		position.set(position.x*0.0f, position.y*0.5f);
		pack.rightHand.position.set(position);
		pack.leftHand.position.set(position);
		
		position = pack.leftShoe.position.copy();
		position.set(position.x*0.0f, position.y*0.5f);
		pack.rightHand.position.set(position);
		
	}
	private void animateWeapon(int delta){
		
	}
	
	
	float shoulderDistance;
	public final ValueAnimation breathing =Loader.loadValueAnimation("breathing").setPingPong(true);
	private void animateShoulders(int delta){
		
		shoulderDistance = breathing.getHeight()*CM;
		breathing.update(delta);
		
		if(breathing.getHeight()==0){
			System.out.println("ERROR");
		}
		pack.leftShoulder.position.set(-25*CM, 0);
		pack.rightShoulder.position.set(25*CM, 0);
		
		pack.rightShoulder.position.add(pack.leftShoe.position.copy().scale(0.2f));
		pack.rightShoulder.position.normalise().scale(shoulderDistance);
		pack.leftShoulder.position.add(pack.rightShoe.position.copy().scale(0.2f));
		pack.leftShoulder.position.normalise().scale(shoulderDistance);
		
		pack.rightShoulder.setRotationRadians((float) Toolbox.getAngle(pack.rightShoulder.position));
		pack.leftShoulder.setRotationRadians((float) Toolbox.getAngle(pack.leftShoulder.position.copy().scale(-1)));
	
		
//		if(!pack.weapon.isDrawn()){
//			pack.weapon.position.set(0, 20);
//			pack.weapon.position.add(pack.rightShoulder.getRotationDegrees());
//			pack.weapon.rotation = pack.rightShoulder.getRotationRadians();
//		}
	}
	
	
	ValueAnimation lookAround = Loader.loadValueAnimation("lookAround");
	private void animateHead(int delta){
		float rotation = targetRotation-getRotationRadians();
		rotation /= 2;
		if(Information.isMouseInactive()){
			lookAround.update(delta);
			rotation += Math.toRadians(lookAround.getHeight());
			if(lookAround.isCompleted())
				lookAround.setTime(-10000);
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
	private final float LEGLENGTH = 40*CM;
	private void animateFeet(int delta){
		
		footTime += delta*speed.length()/(150f*CM)+1;
		
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
//		pack.weapon.collide(object);
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
