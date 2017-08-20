package terrain;

import textures.MipMapTexture;

public class TerrainTexturePack {
	public final MipMapTexture backgroundTexture;
	public final MipMapTexture rTexture;
	public final MipMapTexture gTexture;
	public final MipMapTexture bTexture;
	public TerrainTexturePack(MipMapTexture backgroundTexture, MipMapTexture rTexture, MipMapTexture gTexture, MipMapTexture bTexture) {
		super();
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}
}
