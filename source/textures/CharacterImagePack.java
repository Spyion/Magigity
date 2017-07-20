package textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import tools.Loader;

public class CharacterImagePack {
	public DrawableObject	leftShoulder,
							rightShoulder,
							head,
							hat,
							leftShoe,
							rightShoe;
	public HandImagePack 	rightHand,
							leftHand;
	public WeaponImagePack  weapon;
		
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
	
	
	private final int FOOT_DISTANCE = 25;
	public void render(Graphics g, DrawableObject parent){
		g.pushTransform();
		g.translate(parent.position.x, parent.position.y);
		g.rotate(0, 0, parent.getRotationDegrees());
		g.scale(parent.size.x, parent.size.y);
		
		g.translate(-FOOT_DISTANCE, 0);
		leftShoe.render(g, leftShoe.size);
		g.translate(2*FOOT_DISTANCE, 0);
		rightShoe.render(g, rightShoe.size);
		g.translate(-FOOT_DISTANCE, 0);
		rightHand.render(g, rightHand.size);
		leftHand.render(g, leftHand.size);
		leftShoulder.render(g, leftShoulder.size);
		rightShoulder.render(g, rightShoulder.size);
		head.render(g, head.size);
		hat.render(g, hat.size);
		g.popTransform();
		weapon.render(g, weapon.size);
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
		this.leftShoe.image = Loader.loadCharacterImage(set, "leftShoe", new Vector2f(0.5f, 0.5f));
		this.rightShoe.image = Loader.loadCharacterImage(set, "rightShoe",  new Vector2f(0.5f, 0.5f));
	}

	public void setHands(String set) {
		this.rightHand = Loader.loadHand(set, "rightHand");
		this.leftHand = Loader.loadHand(set, "leftHand");
	}
	public void setWeapon(String set){
		this.weapon = Loader.loadWeapon(set);
	}
	
}
