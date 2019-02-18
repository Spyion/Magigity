package shaders;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.SlickException;

import shader.Shader;

public class Shaders {
	private static final String root = "data/shaders/";
	
	public static EntityShader entityShader = new EntityShader();

	public static Shader buildingShader;
	public static Shader terrainShader;
	public static Shader postProcessing;
	public static Shader verticalBlur;
	
	
	static{
			//entityShader = makeShader("entityShader");
//			postProcessing = makeShader("postProcessing");
			//verticalBlur = makeShader("verticalBlur"); ACCESS VIOLATION
//			terrainShader = makeShader("terrainShader");
			
			
			
			entityShader.start();
			entityShader.loadProjectionMatrix(createProjectionMatrix(1080, 720, 90, 0.1f, 1000));
			entityShader.stop();


	}
//	public static loadProj(){
//		
//	}
	private static Matrix4f createProjectionMatrix(float width, float height, float FOV, float nearPlane, float farPlane) {
		float aspectRatio = width / height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = farPlane - nearPlane;

		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}
	
	private static Shader makeShader(String vsh, String fsh) throws SlickException{
		Shader shader = Shader.makeShader(root + vsh+".vsh", root + fsh+".fsh");
		return shader;
	}
	private static Shader makeShader(String name) throws SlickException{
		return makeShader(name, name);
	}
}
