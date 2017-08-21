package textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import tools.TexturedModel;

public class HandModelPack extends DrawableObject{
	public DrawableObject 	handDown,
							thumbDown,
							handUp,
							thumbUp;
	public boolean up = true;
	public final Vector2f downAnchor, upAnchor;
	
	public HandModelPack(TexturedModel side, TexturedModel handDown, TexturedModel thumbDown, Vector2f downAnchor, TexturedModel handUp, TexturedModel thumbUp, Vector2f upAnchor, Vector2f scaling) {
		super(side, new Vector2f(), new Vector2f(1, 1), 0, null);
		this.handDown = new DrawableObject(handDown, this);
		this.thumbDown = new DrawableObject(thumbDown, this);
		this.handUp = new DrawableObject(handUp, this);
		this.thumbUp = new DrawableObject(thumbUp, this);
		this.upAnchor = new Vector2f(upAnchor.x*scaling.x, upAnchor.y*scaling.y);
		this.downAnchor = new Vector2f(downAnchor.x*scaling.x, downAnchor.y*scaling.y);
	}
	
//	@Override
//	public void render(Graphics g, Vector2f size){
//		super.render(g, size);
//	}
	public void renderUpper(Graphics g, Vector2f position, float rotation){
		if(up){
			thumbUp.offset.set(upAnchor);
			thumbUp.position.set(position);
			thumbUp.setRotationRadians(rotation);
			thumbUp.render(g);
		}else{
			handDown.offset.set(downAnchor);
			handDown.position.set(position);
			handDown.setRotationRadians(rotation);
			handDown.render(g);
		}
	}
	public void renderLower(Graphics g, Vector2f position, float rotation){
		
		if(up){
			handUp.offset.set(upAnchor);
			handUp.position.set(position);
			handUp.setRotationRadians(rotation);
			handUp.render(g);
		}else{
			thumbDown.offset.set(downAnchor);
			thumbDown.position.set(position);
			thumbDown.setRotationRadians(rotation);
			thumbDown.render(g);
		}
	}
//	private void render(Graphics g, Image image, Vector2f pos, float rotation, Vector2f anchor){
//		Image toDraw = image.copy();
//		toDraw.setCenterOfRotation(anchor.x, anchor.y);
//		toDraw.rotate(rotation);
//		toDraw.draw(pos.x-anchor.x, pos.y-anchor.y);
//	}

	public void turnUp(){
		up = true;
	}
	public void turnDown(){
		up = false;
	}

}