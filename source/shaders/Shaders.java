package shaders;

import org.newdawn.slick.SlickException;

import shader.Shader;

public class Shaders {
	private static final String root = "data/shaders/";
	
	public static Shader entityShader;
	public static Shader buildingShader;
	public static Shader postProcessing;
	
	
	static{
		try {
			entityShader = makeShader("entityShader");
			postProcessing = makeShader("postProcessing");
			
			
			
			
			
			
			
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
