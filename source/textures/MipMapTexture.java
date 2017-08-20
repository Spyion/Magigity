package textures;

import org.newdawn.slick.opengl.Texture;

public class MipMapTexture {
	public final Texture[] textures;

	public MipMapTexture(Texture... textures) {
		super();
		this.textures = textures;
	}
	public int getTextureID(int i){
		return textures[(int)Math.min(i, textures.length-1)].getTextureID();
	}
	public int getWidth(int i){
		return textures[(int)Math.min(i, textures.length-1)].getImageWidth();
	}
}
