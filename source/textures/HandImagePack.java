package textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;

public class HandImagePack extends DrawableObject{
	public Image		 	handDown,
							thumbDown,
							handUp,
							thumbUp;
	public boolean up = true;
	public HandImagePack(Image handDown, Image thumbDown, Image handUp, Image thumbUp) {
		super();
		this.handDown = handDown;
		this.thumbDown = thumbDown;
		this.handUp = handUp;
		this.thumbUp = thumbUp;
	}
	@Override
	public void render(Graphics g, Vector2f size){
		renderLower(g, size);
		renderUpper(g, size);
	}
	public void renderUpper(Graphics g, Vector2f size){
		if(up){
			super.render(g, thumbUp, size);
		}else{
			super.render(g, handDown, size);
		}
	}
	public void renderLower(Graphics g, Vector2f size){
		if(up){
			super.render(g, handUp, size);
		}else{
			super.render(g, thumbDown, size);
		}
	}
	public void turnUp(){
		up = true;
	}
	public void turnDown(){
		up = false;
	}

}