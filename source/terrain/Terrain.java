package terrain;

import org.newdawn.slick.Image;

import components.DrawableObject;

public class Terrain extends DrawableObject{
	
	public final int textureSize;
	
	public Terrain(Image combinedImage){
		super(combinedImage);
		this.textureSize = combinedImage.getHeight()/2;
	}
	
}
