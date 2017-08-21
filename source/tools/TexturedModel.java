package tools;

import textures.Texture;

public class TexturedModel {
	
	public final RawModel model;
	public final Texture texture;

	
	public TexturedModel(RawModel model, Texture texture){
		this.model = model;
		this.texture = texture;
	}


}
