package terrain;

import textures.Texture;

public class TerrainTexturePack {
	public final Texture backgroundTexture;
	public final Texture rTexture;
	public final Texture gTexture;
	public final Texture bTexture;
	public TerrainTexturePack(Texture backgroundTexture, Texture rTexture, Texture gTexture, Texture bTexture) {
		super();
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}
}
