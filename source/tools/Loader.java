package tools;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Loader {
	public Image loadImage(String name, String type){
		try {
			return new Image("/resources/images/"+name+"."+type);
		} catch (SlickException e) {
			System.out.println("Could not load Image");
			e.printStackTrace();
		}
		return null;
	}
	public Image loadImage(String name){
		return loadImage(name,"png");
	}
	public Sound loadSound(String name, String type){
		try {
			return new Sound("/resources/sounds/"+name+"."+type);
		} catch (SlickException e) {
			System.out.println("Could not load Sound  " + "path: /resources/sounds/"+name+"."+type);
			e.printStackTrace();
		}
		return null;
	}
	public Sound loadSound(String name){
		return loadSound(name,"ogg");
	}
	
}
