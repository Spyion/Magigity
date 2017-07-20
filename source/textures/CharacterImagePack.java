package textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import info.Information;
import tools.Loader;
import tools.Toolbox;

public class CharacterImagePack {
	private final int M = Information.METER;
	private final float CM = Information.CENTIMETER;
	
	
	public DrawableObject	leftShoulder,
							rightShoulder,
							head,
							hat,
							leftShoe,
							rightShoe;
	public HandImagePack 	rightHand,
							leftHand;
//	public WeaponImagePack  weapon;
		
	public CharacterImagePack(  String hat, 		String head,
								String shoulders, 	String hands, 
								String shoes, 		String weapon) {
		super();
		this.leftShoulder = new DrawableObject();
		this.rightShoulder = new DrawableObject();
		this.head = new DrawableObject();
		this.hat = new DrawableObject();
		this.leftShoe = new DrawableObject();
		this.rightShoe = new DrawableObject();
		
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
			 "basic",	"basic");
	}
	
	
	private final float FOOT_DISTANCE = 15*CM;
	private final float HAND_DISTANCE = 25*CM;
	public void render(Graphics g, DrawableObject parent){
		g.pushTransform();
		Toolbox.setGraphicsToParent(g, parent);
		
		g.translate(-FOOT_DISTANCE, 0);
		leftShoe.render(g, leftShoe.size);
		g.translate(2*FOOT_DISTANCE, 0);
		rightShoe.render(g, rightShoe.size);
		g.translate(-FOOT_DISTANCE, 0);
		
//		if(!weapon.isDrawn()){
			
		g.translate(-HAND_DISTANCE, 0);
		leftHand.render(g, leftHand.size);
		g.translate(2*HAND_DISTANCE, 0);
		rightHand.render(g, rightHand.size);
		g.translate(-HAND_DISTANCE, 0);
		
//		}
		leftShoulder.render(g, leftShoulder.size);
		rightShoulder.render(g, rightShoulder.size);
		
//		if(!weapon.isDrawn()){
//			weapon.render(g, weapon.size);
//		}
		
		
		head.render(g, head.size);
		hat.render(g, hat.size);
		
//		if(weapon.isDrawn()){
			
//			if(weapon.isFlipped()){
//				rightHand.position.set(0,
//						weapon.getUpperHandPosition()+
//						rightHand.handDown.getHeight()*
//						rightHand.size.y/2);
//				leftHand.position.set(0, weapon.getLowerHandPosition());
//				
//				leftHand.up = true;
//				rightHand.up = false;
//			}else{
//				leftHand.position.set(0, weapon.getUpperHandPosition()+leftHand.handDown.getHeight()*leftHand.size.y/2);
//				rightHand.position.set(0, weapon.getLowerHandPosition());
//				
//				rightHand.up = true;
//				leftHand.up = false;
//			}
//			
//			g.pushTransform();
//			g.translate(weapon.position.x, weapon.position.y);
//			g.rotate(0, 0, weapon.rotation);
//			g.scale(weapon.size.x, weapon.size.y);
//			
//			rightHand.renderLower(g, rightHand.size);
//			leftHand.renderLower(g, leftHand.size);
//			
//			g.popTransform();
//			weapon.render(g, weapon.size);
//			
//			g.translate(weapon.position.x, weapon.position.y);
//			g.rotate(0, 0, weapon.rotation);
//			g.scale(weapon.size.x, weapon.size.y);
//			rightHand.renderUpper(g, rightHand.size);
//			leftHand.renderUpper(g, leftHand.size);
//			
//		}
		g.popTransform();
	}
	
	
	public DrawableObject getLeftShoulder() {
		return leftShoulder;
	}
	public void setShoulders(String set) {
		this.leftShoulder.image = Loader.loadCharacterImage(set, "leftShoulder");
		this.rightShoulder.image = Loader.loadCharacterImage(set, "rightShoulder");
	}
	public void setHead(String set) {
		this.head.image = Loader.loadCharacterImage(set, "head");
	}
	public void setHat(String set) {
		this.hat.image = Loader.loadCharacterImage(set, "hat");
	}
	public void setShoes(String set) {
		this.leftShoe.image = Loader.loadCharacterImage(set, "leftShoe", new Vector2f(25*CM, 25*CM));
		this.rightShoe.image = Loader.loadCharacterImage(set, "rightShoe",  new Vector2f(25*CM, 25*CM));
	}

	public void setHands(String set) {
		this.rightHand = Loader.loadHand(set, "rightHand");
		this.leftHand = Loader.loadHand(set, "leftHand");
	}
	public void setWeapon(String set){
//		this.weapon = Loader.loadWeapon(set);
	}
	
}
