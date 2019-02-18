package tools;

import static info.Information.CM;
import static info.Information.M;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;
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
import components.DrawableObject;
import terrain.Terrain;
import textures.HandModelPack;
import textures.Texture;
import weapons.Weapon;

public class Loader {
	
	public static final CSVHandler csv = new CSVHandler();
	public static final RawModel quad; 
	static{
		float[] t = {
				0,0, 	//V0
				1,0,	//V1
				1,1,	//V2
				0,1		//V3
				};
		float size = 1f;
		float[] vertices = {            
                -size,size,0,   //V0
                size,size,0,  //V1
                size,-size,0,   //V2
                -size,-size,0     //V3
        };
         
        int[] indices = {
                0,3,2,  //Top left triangle
                0,1,2   //Bottom right triangle
        };
		Vector3f n1 = new Vector3f(1,1,1);
		n1.normalise();
		float[] n = {-n1.x,-n1.y,n1.z,n1.x,-n1.y,n1.z,-n1.x,n1.y,n1.z,n1.x,n1.y,n1.z};
		int[] i = {0,1,2,3,1,2};
		quad = VaoLoader.loadToVAO(vertices, t, n, indices);
	}
	public static TexturedModel loadModel(String name){
		return new TexturedModel(quad, loadTexture(name));
	}
	public static Terrain loadTerrain(String name, Vector2f size){
		return new Terrain(name, size);
	}
	public static Terrain loadTerrain(String name){
		return loadTerrain("terrain/"+name, new Vector2f(1,1));
	}
	public static Texture loadTexture(String name){
		return new Texture(VaoLoader.loadTexture("resources/images/"+name));
	}
	public static Image loadImage(String name, String type, Vector2f size, boolean relative){
		try {
			Image image;
			if(type.equals("")){
				image = new Image("/resources/images/"+name);
			}
			image = new Image("/resources/images/"+name+"."+type);
//			GL30.glGenerateMipmap(image.getTexture().getTextureID());
//			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			if(size != null)
				if(relative){
				return image.getScaledCopy((int)size.x*image.getWidth(), (int)size.y*image.getWidth());
				}else{
					return image.getScaledCopy((int)size.x, (int)size.y);

				}
			else
				return image;
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
		return loadImage(name,"png", size, false);
	}
	public static Image loadImage(String name){
		return loadImage(name,"png", new Vector2f(25f*CM, 25*CM), false);
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
	
	public static HandModelPack loadHand(String set, String dirType, Vector2f size, DrawableObject parent){
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
		HandModelPack pack = new HandModelPack(	loadCharacterModel(set, "side", size),
												loadCharacterModel(set, "handDown", size),
												loadCharacterModel(set, "thumbDown", size),
												downAnchor,
												loadCharacterModel(set, "handUp", size),
												loadCharacterModel(set, "thumbUp", size),
												upAnchor,
												new Vector2f(size.x / ref.getWidth(), size.y / ref.getHeight()));
		pack.turnDown();
		pack.parent = parent;
		return pack;
	}
	public static HandModelPack loadHand(String set, String dirType, DrawableObject parent){
		return loadHand(set, dirType, new Vector2f(25*CM, 25*CM), parent);
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
		TexturedModel drawn = loadModel(ref+"drawn");
		TexturedModel sheathed = loadModel(ref+"sheathed");
		if(type == Weapon.BOW || type == Weapon.STAFF || type == Weapon.THROW){
			weapon = null;
		}else{
			weapon = new Weapon(type, drawn, sheathed, hitbox, anchor, rightHandPosition, leftHandPosition, rightHandRotation, leftHandRotation, rightHandFlipped, leftHandFlipped, doesFlip,  animations);
		}
		weapon.setDrawn();
		return weapon;
		
		
	}
	public static TexturedModel loadCharacterModel(String set, String dirType, Vector2f size){
		return loadModel("character/"+set+"/"+dirType);
	}
	public static TexturedModel loadCharacterModel(String set, String dirType){
		return loadCharacterModel(set, dirType, new Vector2f(0.5f*M, 0.5f*M));
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
