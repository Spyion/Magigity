package tools;

import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

import animations.ValueAnimation;
import textures.HandImagePack;
import textures.WeaponImagePack;

public class Loader {
	public static Image loadImage(String name, String type){
		try {
			return new Image("/resources/images/"+name+"."+type);
		} catch (SlickException e) {
			System.out.println("Could not load Image");
			e.printStackTrace();
		}
		return null;
	}
	public static Image loadImage(String name){
		return loadImage(name,"png");
	}
	
	public static Sound loadSound(String name, String type){
		try {
			return new Sound("/resources/sounds/"+name+"."+type);
		} catch (SlickException e) {
			System.out.println("Could not load Sound  " + "path: /resources/sounds/"+name+"."+type);
			e.printStackTrace();
		}
		return null;
	}
	public static Sound loadSound(String name){
		return loadSound(name,"ogg");
	}
	
	public static ConfigurableEmitter loadEmitter(String name, String type){
		try {
			return ParticleIO.loadEmitter("/resources/particleSystems/"+name+"."+type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static HandImagePack loadHand(String set, String dirType, Vector2f size){
		set += "/"+dirType+"/";
		HandImagePack pack = new HandImagePack(	loadCharacterImage(set, "handDown", size),
												loadCharacterImage(set, "thumbDown", size),
												loadCharacterImage(set, "handUp", size),
												loadCharacterImage(set, "thumbDown", size));
		pack.turnDown();
		return pack;
	}
	public static HandImagePack loadHand(String set, String dirType){
		return loadHand(set, dirType, new Vector2f(0.5f, 0.5f));
	}
	public static WeaponImagePack loadWeapon(String set){
		set+="/weapon/";
		Image hitboxImage = loadCharacterImage(set, "hitbox");
		Image handleImage = loadCharacterImage(set, "handle");
		WeaponImagePack pack = new WeaponImagePack(	loadCharacterImage(set, "sheathed"),
													loadCharacterImage(set, "drawn"),
													new Rectangle(0, 0, hitboxImage.getWidth(), hitboxImage.getHeight()),
													new Rectangle(0, 0, handleImage.getWidth(), handleImage.getHeight()));
		pack.setDrawn();
		return pack;
		
	}
	public static Image loadCharacterImage(String set, String dirType, Vector2f size){
		Image image = loadImage("character/"+set+"/"+dirType);
		image = image.getScaledCopy((int)(size.x*image.getWidth()), (int)(size.y*image.getHeight()));
		return image;
	}
	public static Image loadCharacterImage(String set, String dirType){
		return loadCharacterImage(set, dirType, new Vector2f(1, 1));
	}
//	public static Image loadCharacterImage(String dirType){
//		return loadImage("character/"+dirType);
//
//	}
	public static ValueAnimation loadValueAnimation(String ref){
		return new ValueAnimation("data/ValueAnimations/"+ref);
	}
	public static ConfigurableEmitter loadEmitter(String name){
		return loadEmitter(name,"xml");
	}
	
}
