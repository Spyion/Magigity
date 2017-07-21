package enitities;

import java.util.ArrayList;

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
		this.pack.weapon.setSheathed();
		if(pack.weapon.animations != null){
			animations = pack.weapon.animations;
			idleAnimation = animations.get(0);
			setAnimationPingPong(idleAnimation);
		}
		else 
			play = false;
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
			pack.weapon.collider.render(g);
		}
	}
	@Override
	public void update(int delta){
		
		pack.weapon.update(delta, this);
		CollidableObject object = Collision.getCollidedObject(pack.weapon);
		if(object != null){
			System.out.println("hit");
		}
		
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
		
		Vector2f position = pack.rightShoe.position.copy();
		position.set(position.x*0.0f, position.y*0.5f);
		pack.leftHand.position.set(position);
		
		position = pack.leftShoe.position.copy();
		position.set(position.x*0.0f, position.y*0.5f);
		pack.rightHand.position.set(position);

	}
	
	
	
	
	// initialized above
	ArrayList<ArrayList<ValueAnimation>> animations;
	ArrayList<ValueAnimation> idleAnimation;
	boolean play = true;
	// initialized above
	
	public boolean isAttacking = false;
	int currentAnimation;
	
	private void animateWeapon(int delta){
		if(play && !animations.isEmpty()){
			
			Vector2f targetPosition = new Vector2f();
			float targetRotation = 0;
			float toTurn = 0;
			float toScale = 0;
			if(isAttacking){
				
				updateAnimation(animations.get(currentAnimation), delta);
				
				if(animations.get(currentAnimation).get(0).isCompleted()){
					setAnimationTime(animations.get(currentAnimation), 0);
					isAttacking = false;
					if(++currentAnimation>animations.size()-1){
						currentAnimation = 2;
					}
					setAnimationTime(idleAnimation, 0);
	
					}
				
			}else{
				updateAnimation(idleAnimation, delta);
			}
			for(ValueAnimation anim : animations.get(currentAnimation)){
				if(anim.name.contains("x")){
					targetPosition.x = anim.getHeight()*CM;
				}else if(anim.name.contains("y")){
					targetPosition.y = anim.getHeight()*CM;
				}else if(anim.name.contains("rot")){
					targetRotation = (float) Math.toRadians(anim.getHeight());
				
				}else if(anim.name.contains("dis")||anim.name.contains("rad")){
					toScale = anim.getHeight()*CM;
				}else if(anim.name.contains("ang")){
					toTurn = (float) Math.toRadians(anim.getHeight());
				}
			}
			if(toTurn != 0 && toScale != 0){
				targetPosition.set(0, toScale);
				targetPosition.add(toTurn);
			}
			Toolbox.approachVector(pack.weapon.relativePosition, targetPosition, 0.99f, delta);
			pack.weapon.relativeRotation = Toolbox.approachValue(pack.weapon.relativeRotation, targetRotation, delta);
		}
	}
	private void updateAnimation(ArrayList<ValueAnimation> anim, int delta){
		for(ValueAnimation ani : anim){
			ani.update(delta);
		}
	}	private void setAnimationTime(ArrayList<ValueAnimation> anim, int time){
		for(ValueAnimation ani : anim){
			ani.setTime(time);
		}
	}
	private void setAnimationPingPong(ArrayList<ValueAnimation> anim){
		for(ValueAnimation ani : anim){
			ani.setPingPong(true);
		}
	}
	
	public void Attack(){
		isAttacking = true;
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
	
		
		if(!pack.weapon.isDrawn()){
			pack.weapon.relativePosition.set(0, 15*CM);
			pack.weapon.relativePosition.add(pack.rightShoulder.getRotationDegrees());
			pack.weapon.relativeRotation = pack.rightShoulder.getRotationRadians();
		}
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
