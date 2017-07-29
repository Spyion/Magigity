package enitities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import animations.ValueAnimation;
import components.CollidableObject;
import components.Collider;
import connections.ConnectionHandler;
import debug.Debug;
import info.Collision;
import info.Information;
import textures.CharacterImagePack;
import tools.Loader;
import tools.Toolbox;

public class Character extends Entity{
	
	public CharacterImagePack pack;
	protected float targetRotation = 0;
	public final Collider collider;
	
	
	
	public Character(CharacterImagePack pack, Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight, float health) {
		super(hitbox, size, rotation, weight, health);
		collider = new Collider(this, hitbox2);
		this.pack = pack;
		this.pack.setWeapon("basicLongSword");
		this.pack.weapon.setSheathed();
		if(pack.weapon.animations != null){
			animations = pack.weapon.animations;
			idleAnimation = animations.get(0);
			blockAnimation = animations.get(1);
			setAnimationPingPong(idleAnimation);
			play = true;
		}

	}
	public Character(Shape hitbox, Shape hitbox2, Vector2f size, float rotation, float weight, float health) {
		this(new CharacterImagePack(), hitbox, hitbox2, size, rotation, weight, health);
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
	
	ArrayList<CollidableObject> hitObjects = new ArrayList<CollidableObject>();
	@Override
	public void update(int delta){
		
		pack.weapon.update(delta, this);
		
		if(isAttacking){
			CollidableObject object = Collision.getCollidedObject(pack.weapon);
			if(object != null && !hitObjects.contains(object)){
				if(object instanceof Entity){
					Entity e = (Entity) object;
					e.loseHealth(pack.weapon.damage);
					hitObjects.add(e);
				}
			}
		}else{
			hitObjects.clear();
		}
		fixTargetRotation();
		if(isAttacking || isBlocking){
			addToRotationDegrees(Math.signum(targetRotation-getRotationRadians())*delta/20f);
		}
		else
			setRotationRadians(Toolbox.approachValue(getRotationRadians(), targetRotation, delta)); 
		
		updateAnimations(delta);
		super.update(delta);
	}
	
	
	private void updateAnimations(int delta){
		animateFeet(delta);
		animateHead(delta);
		animateShoulders(delta);

		if(!pack.weapon.isDrawn())
			animateHands(delta);
		else
			animateWeapon(delta);
	}
	
	private void animateHands(int delta){
		
		Vector2f position = pack.rightShoe.position.copy();
		position.set(position.x*0.0f, position.y*0.7f);
		pack.leftHand.position.set(position);
		
		position = pack.leftShoe.position.copy();
		position.set(position.x*0.0f, position.y*0.7f);
		pack.rightHand.position.set(position);

	}
	
	
	
	
	// initialized above
	ArrayList<ArrayList<ValueAnimation>> animations;
	ArrayList<ValueAnimation> idleAnimation;
	ArrayList<ValueAnimation> blockAnimation;
	boolean play = false;
	// initialized above
	
	protected boolean isAttacking = false;
	protected boolean isBlocking = false;
	public void setAttacking(){
		if(!isBlocking && weaponDrawn && (comboTime > 0 || comboTime < -1000)){
			isAttacking =true;
		}
	}
	public void setBlocking(){
		if(!isAttacking && weaponDrawn && comboTime < -500){
			isBlocking =true;
		}
	}
	int currentAttackAnimation = 2;
	int currentAnimation;
	private boolean hasUploaded = false;
	private boolean weaponDrawn = false;
	int backcount = 0;
	Vector2f comboPosition = new Vector2f();
	float comboRotation;
	int comboTime;
	float shoulderAngle;
		
	private void animateWeapon(int delta){
		if(play && !animations.isEmpty() && weaponDrawn){
			
			Vector2f targetPosition = new Vector2f();
			float targetRotation = 0;
			float toTurn = 0;
			float toScale = 0;
			boolean toFlip = true;
			
			if(isAttacking){
				comboTime = 0;
				currentAnimation = currentAttackAnimation;
				if(this instanceof Player && !hasUploaded){
					ConnectionHandler.instance.uploadAttack(currentAnimation);
					hasUploaded = true;
				}
				updateAnimation(animations.get(currentAttackAnimation), delta);
				
				if(animations.get(currentAttackAnimation).get(0).isCompleted()){
					//Combo position
					for(ValueAnimation anim : animations.get(currentAnimation)){

						if(anim.name.contains("x")){
							comboPosition.x = anim.getHeight(anim.getLength())*CM;
						}else if(anim.name.contains("y")){
							comboPosition.y = anim.getHeight(anim.getLength())*CM;
						}else if(anim.name.contains("rot")){
							comboRotation = (float) Math.toRadians(anim.getHeight(anim.getLength()));
						}else if(anim.name.contains("dis")||anim.name.contains("rad")){
							toScale = anim.getHeight(anim.getLength())*CM;
						}else if(anim.name.contains("ang")){
							toTurn = anim.getHeight(anim.getLength());
						}
					}
					if(toTurn != 0 && toScale != 0){
						comboPosition.set(0, toScale);
						comboPosition.add(toTurn);
					}
					
					
					
					isAttacking = false;
					comboTime = 500;
					setAnimationTime(animations.get(currentAttackAnimation), 0);
					if(++currentAttackAnimation>animations.size()-1){
						currentAttackAnimation = 2;
						comboTime = 0;
					}
					setAnimationTime(idleAnimation, 0);
					hasUploaded = false;
					}				
			}else if(isBlocking){
				currentAnimation = 1;
				if(this instanceof Player && !hasUploaded){
					ConnectionHandler.instance.uploadAttack(currentAnimation);
					hasUploaded = true;
				}
				updateAnimation(blockAnimation, delta);
				if(blockAnimation.get(0).isCompleted()){
					setAnimationTime(blockAnimation, 0);
					isBlocking = false;
					setAnimationTime(idleAnimation, 0);
					hasUploaded = false;
					}
			}else{
				currentAnimation = 0;
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
					toTurn = anim.getHeight();
				}else if(anim.name.contains("flip")){
					toFlip = anim.getHeight() > 0;
				}
			}
			if(pack.weapon.doesFlip)
				pack.weapon.flipped = toFlip;
			
			if(toTurn != 0 && toScale != 0){
				targetPosition.set(0, toScale);
				targetPosition.add(toTurn);
			}

			
			//animate shoulders
			float angle;
			if(currentAnimation == 0){
				angle = 0;
			}else{
			angle = (float) Math.toDegrees(Toolbox.getAngle(pack.weapon.relativePosition));
			angle += 90;
			while(angle > 180)
				angle-=360;
			}
			shoulderAngle = Toolbox.approachValue(shoulderAngle, angle, delta);
			
			pack.rightShoulder.position.add(shoulderAngle/5f);
			pack.leftShoulder.position.add(shoulderAngle/5f);

			pack.rightShoulder.setRotationRadians((float) Toolbox.getAngle(pack.rightShoulder.position));
			pack.leftShoulder.setRotationRadians((float) Toolbox.getAngle(pack.leftShoulder.position.copy().scale(-1)));
			//animate shoulders
			
			
			
			comboTime-=delta;
			if(comboTime > 0){
			Toolbox.approachVector(pack.weapon.relativePosition, comboPosition, 0.99f, delta);
			pack.weapon.relativeRotation = Toolbox.approachValue(pack.weapon.relativeRotation, comboRotation, delta);
			}
			else{
			if(!isAttacking)
				currentAttackAnimation = 2;
			Toolbox.approachVector(pack.weapon.relativePosition, targetPosition, 0.99f, delta);
			pack.weapon.relativeRotation = Toolbox.approachValue(pack.weapon.relativeRotation, targetRotation, delta);

			}
			
			//TODO : prevent sword from passing through body
			
			
		}else if(backcount > 0){
			comboTime = 0;
			backcount -= delta;
			Toolbox.approachVector(pack.weapon.relativePosition, new Vector2f(10*CM, -10*CM), 0.99f, delta);
			pack.weapon.relativeRotation = Toolbox.approachValue(pack.weapon.relativeRotation, (float) (Math.PI/2), delta);
		}else{
			comboTime = 0;
			pack.weapon.setSheathed();
			pack.weapon.relativePosition.set(0, 15*CM);
			pack.weapon.relativePosition.add(pack.rightShoulder.getRotationDegrees());
			pack.weapon.relativeRotation = pack.rightShoulder.getRotationRadians();
		}

	}
	public void drawWeapon(){
		weaponDrawn = true;
		pack.weapon.setDrawn();
		pack.weapon.relativePosition.set(10*CM, -10*CM);
		pack.weapon.relativeRotation =(float)Math.PI/2;
	}
	public void sheatheWeapon(){
		if(!isAttacking && !isBlocking){
		weaponDrawn = false;
		backcount = 400;
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
		
		pack.leftShoulder.position.set(-25*CM, 0);
		pack.rightShoulder.position.set(25*CM, 0);
		
		pack.rightShoulder.position.add(pack.leftShoe.position.copy().scale(0.2f));
		pack.rightShoulder.position.normalise().scale(shoulderDistance);
		pack.leftShoulder.position.add(pack.rightShoe.position.copy().scale(0.2f));
		pack.leftShoulder.position.normalise().scale(shoulderDistance);
		
		pack.rightShoulder.setRotationRadians((float) Toolbox.getAngle(pack.rightShoulder.position));
		pack.leftShoulder.setRotationRadians((float) Toolbox.getAngle(pack.leftShoulder.position.copy().scale(-1)));
	
	}
	
	
	ValueAnimation lookAround = Loader.loadValueAnimation("lookAround");
	private void animateHead(int delta){
		float rotation = targetRotation-getRotationRadians();
		rotation /= 2;
		if(rotation > 0.5)
			rotation = 0.5f;
		if(rotation < -0.5)
			rotation = -0.5f;
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
	float footTime = 0;
	Vector2f footPosition = new Vector2f(0, 0);
	private final float LEGLENGTH = 40*CM;
	protected void animateFeet(int delta){
		
		float timeAdd = (float) (delta*(Math.sqrt(speed.length())/(4*CM)));
		if(timeAdd < 1) timeAdd = 1;
		footTime += timeAdd;
		
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
	
	private boolean collisionInited = false;
	
	@Override
	public void collide(CollidableObject object){
		if(!collisionInited){
			
			super.collider.isTrigger = true;
			collider.isTrigger = true;
		}
		super.collide(object);
		collider.collide(object);
		pack.weapon.collide(object);
		if(object instanceof Character){
			Character c = (Character) object;
			collider.collide(c.collider);
			collider.collide(c.pack.weapon);
			c.collider.collide(this);
			pack.weapon.collide(c.collider);
		}
		if(Collision.getCollidedObject(this) != null)
		if(!collisionInited && Collision.getCollidedObject(this) == null){
			collider.isTrigger = false;
			super.collider.isTrigger = false;
			collisionInited = true;
		}
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
	public boolean isAttacking() {
		return isAttacking;
	}
	public boolean isBlocking() {
		return isBlocking;
	}
	private void fixTargetRotation(){
		if(rotation-targetRotation>Math.PI){
			rotation-=2*Math.PI;
		}else if(rotation-targetRotation<-Math.PI){
			rotation+=2*Math.PI;
		}
	}
}
