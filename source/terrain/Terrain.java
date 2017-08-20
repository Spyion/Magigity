package terrain;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static tools.Loader.loadImage;
import static tools.Loader.loadTexture;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;

public class Terrain extends DrawableObject{
	public static final String root = "terrain/";
	
	public final TerrainTexturePack texturePack;
	
	public Terrain(String name, Vector2f size){
		super(loadImage(root+name+"/blendMap", "png",size, true));
		texturePack = new TerrainTexturePack(	loadTexture(root+name+"/backgroundTexture"),	
												loadTexture(root+name+"/rTexture"),
												loadTexture(root+name+"/gTexture"),
												loadTexture(root+name+"/bTexture"));
		
	}
	
	public void bindTextures(){
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, texturePack.backgroundTexture.getTextureID());
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, texturePack.rTexture.getTextureID());
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, texturePack.gTexture.getTextureID());
		glActiveTexture(GL_TEXTURE4);
		glBindTexture(GL_TEXTURE_2D, texturePack.bTexture.getTextureID());
	}
}
