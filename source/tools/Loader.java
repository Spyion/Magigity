package tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

import animations.ValueAnimation;
import info.Information;
import terrain.Terrain;
import textures.HandImagePack;
import weapons.Weapon;

public class Loader {
	
	private static final int M = Information.METER;
	private static final float CM = Information.CENTIMETER;
	public static final CSVHandler csv = new CSVHandler();
	
	public static Image loadImage(String name, String type, Vector2f size){
		try {
			if(size != null)
				return new Image("/resources/images/"+name+"."+type).getScaledCopy((int)size.x, (int)size.y);
			else
				return new Image("/resources/images/"+name+"."+type);
		} catch (SlickException e) {
			System.out.println("Could not load Image");
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image loadImage(String name, String type, float x, float y){
		try {
			
				return new Image("/resources/images/"+name+"."+type).getScaledCopy((int)x, (int)y);
				
		} catch (SlickException e) {
			System.out.println("Could not load Image");
			e.printStackTrace();
		}
		return null;
	}
	public static Image loadImage(String name, Vector2f size){
		return loadImage(name,"png", size);
	}
	public static Image loadImage(String name){
		return loadImage(name,"png", new Vector2f(25f*CM, 25*CM));
	}
	public static Terrain loadTerrain(String name){
		return new Terrain(loadImage("terrain/"+name, null));
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
		set += "/"+dirType;
		ArrayList<ArrayList<String>> specs = csv.readCSV("resources/images/character/"+set+"/type");
		for(ArrayList<String> list : specs)
			for(int i = 0; i < list.size()-1; i++)
				list.set(i, list.get(i).toLowerCase());
		ArrayList<String> list;
		list = getVar("upanchor", specs);
		Image ref = null;
		try {
			ref = new Image("resources/images/character/"+set+"/handUp.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		Vector2f upAnchor = new Vector2f(Float.parseFloat(list.get(1)), Float.parseFloat(list.get(2)));
		list = getVar("downanchor", specs);
		Vector2f downAnchor = new Vector2f(Float.parseFloat(list.get(1)), Float.parseFloat(list.get(2)));
		HandImagePack pack = new HandImagePack(	loadCharacterImage(set, "side", size),
												loadCharacterImage(set, "handDown", size),
												loadCharacterImage(set, "thumbDown", size),
												downAnchor,
												loadCharacterImage(set, "handUp", size),
												loadCharacterImage(set, "thumbUp", size),
												upAnchor,
												new Vector2f(size.x / ref.getWidth(), size.y / ref.getHeight()));
		pack.turnDown();
		return pack;
	}
	public static HandImagePack loadHand(String set, String dirType){
		return loadHand(set, dirType, new Vector2f(25*CM, 25*CM));
	}
	public static Weapon loadWeapon(String name){
		String ref = "character/weapons/"+name+"/";
		ArrayList<ArrayList<String>> specs = csv.readCSV("resources/images/"+ref+"type");
		for(ArrayList<String> list : specs)
			for(int i = 0; i < list.size()-1; i++)
				list.set(i, list.get(i).toLowerCase());
		
		String str= specs.get(0).get(0);
		int type = 0;
		ArrayList<ArrayList<ValueAnimation>> animations = loadAnimations(str);
		switch(str){
			case "longswing": type = Weapon.LONGSWING;break;
			case "shortswing": type = Weapon.SHORTSWING;  break;
			case "thrust": type = Weapon.THRUST; 	break;
			case "staff": type = Weapon.STAFF;  	break;
			case "throw": type = Weapon.THROW;  	break;
			case "blunt": type = Weapon.BLUNT; 	 	break;
			case "bow": type = Weapon.BOW;  		break;
			default:  type = Weapon.LONGSWING; 		break;
		}
		
		ArrayList<String> list = getVar("righthandposition", specs);
		Vector2f rightHandPosition = new Vector2f(Float.parseFloat(list.get(1)), Float.parseFloat(list.get(2)));
		list = getVar("lefthandposition", specs);
		Vector2f leftHandPosition = new Vector2f(Float.parseFloat(list.get(1)), Float.parseFloat(list.get(2)));
		list = getVar("anchor", specs);
		Vector2f anchor = new Vector2f(Float.parseFloat(list.get(1)), Float.parseFloat(list.get(2)));
		list = getVar("righthandrotation", specs);
		float rightHandRotation = Float.parseFloat(list.get(1));
		list = getVar("lefthandrotation", specs);
		float leftHandRotation = Float.parseFloat(list.get(1));
		list = getVar("righthandflipped", specs);
		boolean rightHandFlipped = Boolean.parseBoolean(list.get(1));
		list = getVar("lefthandflipped", specs);
		boolean leftHandFlipped = Boolean.parseBoolean(list.get(1));
		list = getVar("doesflip", specs);
		boolean doesFlip = Boolean.parseBoolean(list.get(1));
		list = getVar("hitbox", specs);
		Shape hitbox;
		if(list.get(1).equals("rectangle")){
			float One = Float.parseFloat(list.get(2));
			float Two = Float.parseFloat(list.get(3));
			hitbox = new Rectangle(One, Two, Float.parseFloat(list.get(4))-One, Float.parseFloat(list.get(5))-Two);
		}else{
			hitbox = new Circle(Float.parseFloat(list.get(2)), Float.parseFloat(list.get(3)), Float.parseFloat(list.get(4)));
		}
		
		Weapon weapon;
		Image drawn = loadImage(ref+"drawn", null);
		Image sheathed = loadImage(ref+"sheathed", null);
		if(type == Weapon.BOW || type == Weapon.STAFF || type == Weapon.THROW){
			weapon = null;
		}else{
			weapon = new Weapon(type, drawn, sheathed, hitbox, anchor, rightHandPosition, leftHandPosition, rightHandRotation, leftHandRotation, rightHandFlipped, leftHandFlipped, doesFlip,  animations);
		}
		weapon.setDrawn();
		return weapon;
		
		
	}
	public static Image loadCharacterImage(String set, String dirType, Vector2f size){
		return loadImage("character/"+set+"/"+dirType, size);
	}
	public static Image loadCharacterImage(String set, String dirType){
		return loadCharacterImage(set, dirType, new Vector2f(0.5f*M, 0.5f*M));
	}
	public static ValueAnimation loadValueAnimation(String ref){
		return new ValueAnimation("data/ValueAnimations/"+ref);
	}
	public static ConfigurableEmitter loadEmitter(String name){
		return loadEmitter(name,"xml");
	}
	public static ArrayList<String> getVar(String name, ArrayList<ArrayList<String>> array){
		
		for(ArrayList<String> list : array){
			if(list.get(0).equals(name))
				return list;
		}
		return null;
	}
	private static ArrayList<ArrayList<ValueAnimation>> loadAnimations(String ref){
		ref = "weapon/"+ref;
		ArrayList<ArrayList<ValueAnimation>> animations = new ArrayList<ArrayList<ValueAnimation>>();
		
		ArrayList<File> folders = new ArrayList<File>();
		
		for(int i = 0; true; i++){
			File folder = new File("data/ValueAnimations/"+ref+"/"+Integer.toString(i));
			if(folder.exists())
				folders.add(folder);
			else
				break;
		}
		
		for(File folder : folders){
			animations.add(new ArrayList<ValueAnimation>());
			File[] files = folder.listFiles();
			for(File file : files){
				animations.get(animations.size()-1).add(loadValueAnimation(ref+"/"+folder.getName()+"/"+file.getName()));
			}
			
		}
		
		
		
		
		
		
		return animations;
	}
}
