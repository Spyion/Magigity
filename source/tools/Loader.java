package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

import animations.ValueAnimation;
import info.Information;
import textures.HandImagePack;
import weapons.Weapon;

public class Loader {
	
	private static final int M = Information.METER;
	private static final float CM = Information.CENTIMETER;
	
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
		return loadHand(set, dirType, new Vector2f(25*CM, 25*CM));
	}
	public static Weapon loadWeapon(String set){
		set+="/weapon/";
		FileReader fr;
		try {
			fr = new FileReader(set+"type");
		} catch (FileNotFoundException e1) {
			fr = null;
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String str;
		try {
			str = br.readLine();
		} catch (IOException e1) {
			str = "";
			e1.printStackTrace();
		}
		str = str.toLowerCase();
		switch(str){
			case "longswing":
				
				
				
				break;
			case "shortswing":
				
				
				
				break;
			case "thrust":
			
				
				
				break;
			case "staff":
			
				
				
				break;
			case "throw":
					
					
					
				break;
			case "magic":
					
					
					
				break;
			case "blunt":
					
					
					
				break;
			case "bow":
					
					
					
				break;
		}
		
//		Weapon weapon = new Weapon(	loadCharacterImage(set, "sheathed"),
//													loadCharacterImage(set, "drawn"),
//													new Rectangle(0, 0, hitboxImage.getWidth(), hitboxImage.getHeight()),
//													new Rectangle(0, 0, handleImage.getWidth(), handleImage.getHeight()),
//													);
//		weapon.setDrawn();
		return null;
		
	}
	public static Image loadCharacterImage(String set, String dirType, Vector2f size){
		Image image = loadImage("character/"+set+"/"+dirType);
		image = image.getScaledCopy((int)(size.x), (int)(size.y));
		return image;
	}
	public static Image loadCharacterImage(String set, String dirType){
		return loadCharacterImage(set, dirType, new Vector2f(0.5f*M, 0.5f*M));
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
