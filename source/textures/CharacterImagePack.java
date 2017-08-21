package textures;

import static info.Information.CM;

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
	
	public DrawableObject parent;
	
	public CharacterImagePack(  String hat, 		String head,
								String shoulders, 	String hands, 
								String shoes, 		String weapon) {
		super();
//		this.leftShoulder = new DrawableObject();
//		this.rightShoulder = new DrawableObject();
//		this.head = new DrawableObject();
//		this.hat = new DrawableObject();
//		this.leftShoe = new DrawableObject();
//		this.rightShoe = new DrawableObject();
//		
		setHands(hands);
		setHat(hat);
		setHead(head);
		setShoes(shoes);
		setShoulders(shoulders);
		setWeapon(weapon);
	}
	public CharacterImagePack(){
		this("basic",	"basic",
			 "basic", 	"basic",
			 "basic",	"basicLongSword");
	}
	
	
	private final float FOOT_DISTANCE = 15*CM;
	private final float HAND_DISTANCE = 25*CM;
	private final float HAND_DISTANCEY = -10*CM;

	public void render(Graphics g, DrawableObject parent){
		g.pushTransform();
		Toolbox.setGraphicsToParent(g, parent);
		
		g.translate(-FOOT_DISTANCE, 0);
		leftShoe.render(g);
		g.translate(2*FOOT_DISTANCE, 0);
		rightShoe.render(g);
		g.translate(-FOOT_DISTANCE, 0);
		
		if(!weapon.isDrawn()){
			
//		g.translate(-HAND_DISTANCE, HAND_DISTANCEY);
		leftHand.render(g);
//		g.translate(2*HAND_DISTANCE, 0);
		rightHand.render(g);
//		g.translate(-HAND_DISTANCE, -HAND_DISTANCEY);
		
		}else{
			
			rightHand.up = weapon.isRightHandFlipped();
			leftHand.up = weapon.isLeftHandFlipped();
			g.pushTransform();
			g.translate(weapon.relativePosition.x, weapon.relativePosition.y);
			g.rotate(0, 0, (float)Math.toDegrees(weapon.relativeRotation));
			

			rightHand.renderLower(g, weapon.getRightHandPosition(), weapon.getRightHandRotation());
			leftHand.renderLower(g, weapon.getLeftHandPosition(), weapon.getLeftHandRotation());

			
			
			g.popTransform();
			weapon.render(g);
			
			g.pushTransform();
			g.translate(weapon.relativePosition.x, weapon.relativePosition.y);
			g.rotate(0, 0, (float)Math.toDegrees(weapon.relativeRotation));
			rightHand.renderUpper(g, weapon.getRightHandPosition(), weapon.getRightHandRotation());
			leftHand.renderUpper(g, weapon.getLeftHandPosition(), weapon.getLeftHandRotation());
			g.popTransform();

		}
		leftShoulder.render(g);
		rightShoulder.render(g);
		
		if(!weapon.isDrawn()){
			weapon.render(g);
		}
		
		
		head.render(g);
		hat.render(g);
		
		g.popTransform();
	}
	
	
	public DrawableObject getLeftShoulder() {
		return leftShoulder;
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
		this.rightHand = Loader.loadHand(set, "rightHand");
		rightHand.offset.set(HAND_DISTANCE, HAND_DISTANCEY);
		this.leftHand = Loader.loadHand(set, "leftHand");
		leftHand.offset.set(-HAND_DISTANCE, HAND_DISTANCEY);

	}
	public void setWeapon(String name){
		this.weapon = Loader.loadWeapon(name);
	}
	
}
