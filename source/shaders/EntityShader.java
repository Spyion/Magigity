package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class EntityShader extends ShaderProgram{
	
	private static final int MAX_LIGHTS = 4;
	
	private static final String VERTEX_FILE = root+"entityShader.vsh";
	private static final String FRAGMENT_FILE = root+"entityShader.fsh";
	
	private int location_transformationMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;

	public EntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		for(int i=0;i<MAX_LIGHTS;i++){
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	
	
	public void loadShineVariables(float damper,float reflectivity){
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	public void loadViewMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
//	public void loadLights(List<Light> lights){
//		for(int i=0;i<MAX_LIGHTS;i++){
//			if(i<lights.size()){
//				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
//				super.loadVector(location_lightColour[i], lights.get(i).getColour());
//				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
//			}else{
//				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
//				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
//				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
//			}
//		}
//	}
	
//	public void loadViewMatrix(Camera camera){
//		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
//		super.loadMatrix(location_viewMatrix, viewMatrix);
//	}

	
	

}
