package textures;

import static info.Information.CM;

import org.lwjgl.opengl.Drawable;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import tools.Loader;
import tools.Toolbox;
import weapons.Weapon;

public class CharacterImagePack {
	public DrawableObject	leftShoulder,
							rightShoulder,
							head,
							hat,
							leftShoe,
							rightShoe;
	public HandModelPack 	rightHand,
							leftHand;
	public Weapon  weapon;
	
	private DrawableObject parent;
	
	/**
	 * call Initialize Method after this
	 */
	public CharacterImagePack() {
		super();
	}
	public void initialize(	DrawableObject parent,
							String hat, 		String head,
							String shoulders, 	String hands, 
							String shoes, 		String weapon){
		this.parent = parent;
		setHands(hands);
		setHat(hat);
		setHead(head);
		setShoes(shoes);
		setShoulders(shoulders);
		setWeapon(weapon);
	}
	public void initialize (DrawableObject parent){
		this.parent = parent;
		setHands("basic");
		setHat("basic");
		setHead("basic");
		setShoes("basic");
		setShoulders("basic");
		setWeapon("basicLongSword");
	}
	
	
	private final float FOOT_DISTANCE = 15*CM;
	private final float HAND_DISTANCE = 25*CM;
	private final float HAND_DISTANCEY = -10*CM;

	public void render(Graphics g, DrawableObject parent){
		
		leftShoe.render(g);
		rightShoe.render(g);		
		if(!weapon.isDrawn()){
			
		leftHand.render(g);
		rightHand.render(g);
		
		}else{
			
			rightHand.up = weapon.isRightHandFlipped();
			leftHand.up = weapon.isLeftHandFlipped();

			rightHand.renderLower(g, weapon.getRightHandPosition(), weapon.getRightHandRotation());
			leftHand.renderLower(g, weapon.getLeftHandPosition(), weapon.getLeftHandRotation());
			
			weapon.render(g);
			
			rightHand.renderUpper(g, weapon.getRightHandPosition(), weapon.getRightHandRotation());
			leftHand.renderUpper(g, weapon.getLeftHandPosition(), weapon.getLeftHandRotation());

		}
		leftShoulder.render(g);
		rightShoulder.render(g);
		
		if(!weapon.isDrawn()){
			weapon.render(g);
		}
		
		head.render(g);
		hat.render(g);
		
	}
	
	
	public void setShoulders(String set) {
		this.leftShoulder = new DrawableObject(Loader.loadCharacterModel(set, "leftShoulder"), parent);
		this.rightShoulder = new DrawableObject(Loader.loadCharacterModel(set, "rightShoulder"), parent);
	}
	public void setHead(String set) {
		this.head = new DrawableObject(Loader.loadCharacterModel(set, "head"), parent);
	}
	public void setHat(String set) {
		this.hat = new DrawableObject(Loader.loadCharacterModel(set, "hat"), parent);
	}
	public void setShoes(String set) {
		this.leftShoe = new DrawableObject(Loader.loadCharacterModel(set, "leftShoe"), parent);
		leftShoe.offset.set(FOOT_DISTANCE, 0);
		this.rightShoe = new DrawableObject(Loader.loadCharacterModel(set, "rightShoe"), parent);
		rightShoe.offset.set(-FOOT_DISTANCE, 0);
	}

	public void setHands(String set) {
		this.rightHand = Loader.loadHand(set, "rightHand", parent);
		rightHand.offset.set(HAND_DISTANCE, HAND_DISTANCEY);
		this.leftHand = Loader.loadHand(set, "leftHand", parent);
		leftHand.offset.set(-HAND_DISTANCE, HAND_DISTANCEY);

	}
	public void setWeapon(String name){
		this.weapon = Loader.loadWeapon(name);
	}
	
}
