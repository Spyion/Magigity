package components;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import info.Information;
import shaders.EntityShader;
import shaders.ShaderProgram;
import shaders.Shaders;
import tools.TexturedModel;
import tools.Toolbox;

public class DrawableObject {
	public final Vector2f position;
	public final Vector2f size;
	public final TexturedModel model;
	public Vector2f offset = new Vector2f();
	protected float rotation;
	protected final int M = Information.M;
	protected final static float CM = Information.CM;
	protected ShaderProgram shader = Shaders.entityShader;
	public DrawableObject parent;

	public DrawableObject(TexturedModel model, Vector2f position, Vector2f size, float rotation, DrawableObject parent) {
		super();
		this.model = model;
		this.position = position;
		this.size = size;
		setRotationDegrees(rotation);
	}
//	public DrawableObject( Vector2f position, Vector2f size, float rotation) {
//		this(null, position, size, rotation, );
//	}
	public DrawableObject(){
		this(null, new Vector2f(0, 0), new Vector2f(1,1),0, null);
	}
	public DrawableObject(TexturedModel image, DrawableObject parent){
		this(image, new Vector2f(0, 0), new Vector2f(1,1),0,parent);
	}
	public DrawableObject(TexturedModel image, Vector2f size, DrawableObject parent){
		this(image, new Vector2f(0, 0), size,0,parent);
	}
	public float getRotationDegrees() {
		return (float)Math.toDegrees(rotation);
	}
	public float getRotationRadians() {
		return rotation;
	}
	public void setRotationDegrees(float rotation) {
		this.rotation = (float)Math.toRadians(rotation);
	}
	public void setRotationRadians(float rotation) {
		this.rotation = rotation;
	}
	public void addToRotationDegrees(float rotation) {
		this.rotation += Math.toRadians(rotation);
	}
	public void addToRotationRadians(float rotation) {
		this.rotation += rotation;
	}
	public void render(Graphics g, TexturedModel model){
		if(model != null){
			
			GL30.glBindVertexArray(model.model.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.texture.textureID);
			bindTextures();
			shader.start();
			loadShaderVars();
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.model.getVertexCount(),
					GL11.GL_UNSIGNED_INT, 0);
			
			shader.stop();
			
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
			//Shaders.entityShader.setUniformIntVariable("tex", 0);
		}
	}
	public void render(Graphics g){
		render(g, model);
	}
	protected void loadShaderVars(){
		if(shader instanceof EntityShader){
			Shaders.entityShader.loadTransformationMatrix(getParentMatrix());
			Shaders.entityShader.loadShineVariables(10, 1);
			Shaders.entityShader.loadViewMatrix(Information.currentCamera.getParentMatrix());
		}
	}
	protected void bindTextures(){}
	
	public Matrix4f getParentMatrix(){
		if(parent == null)
			return getMatrix();
		else{
			Matrix4f matrix = new Matrix4f();
			Matrix4f.mul(parent.getParentMatrix(), getMatrix(), matrix);
			return matrix;
		}
	}
	
	protected Matrix4f getMatrix(){
		Matrix4f matrix = Toolbox.createTransformationMatrix(position, size, getRotationRadians());
		Matrix4f.mul(matrix, Toolbox.createTransformationMatrix(offset, new Vector2f(1,1), 0), matrix);
		return matrix;
	}
//	public void render(Graphics g, TexturedModel model, Vector2f size){
//		render(g, model, size, 0);
//	}
//	public void render(Graphics g, Vector2f size){
//		render(g, model, size);
//	}
//	public void render(Graphics g){
//		render(g, model, new Vector2f(1,1));
//	}
//	public void render(Graphics g, Vector2f size,float rotation){
//		render(g, model, size, rotation);
//	}
	
}
