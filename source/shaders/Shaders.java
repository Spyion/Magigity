package shaders;

import org.newdawn.slick.SlickException;

import shader.Shader;

public class Shaders {
	private static final String root = "data/shaders/";
	
	public static Shader entityShader;
	public static Shader buildingShader;
	public static Shader terrainShader;
	public static Shader postProcessing;
	public static Shader verticalBlur;
	
	static{
		try {
			entityShader = makeShader("entityShader");
			postProcessing = makeShader("postProcessing");
			verticalBlur = makeShader("verticalBlur");
			terrainShader = makeShader("terrainShader");
			
			
			
			
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
	private static Shader makeShader(String vsh, String fsh) throws SlickException{
		Shader shader = Shader.makeShader(root + vsh+".vsh", root + fsh+".fsh");
		return shader;
	}
	private static Shader makeShader(String name) throws SlickException{
		return makeShader(name, name);
	}
}
