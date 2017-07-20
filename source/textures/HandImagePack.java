package textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;

public class HandImagePack extends DrawableObject{
	private Image		 	handDown,
							thumbDown,
							handUp,
							thumbUp;
	public Image currentImage = handDown;
	public HandImagePack(Image handDown, Image thumbDown, Image handUp, Image thumbUp) {
		super();
		this.handDown = handDown;
		this.thumbDown = thumbDown;
		this.handUp = handUp;
		this.thumbUp = thumbUp;
	}
	@Override
	public void render(Graphics g, Vector2f size){
		super.render(g, currentImage, size);
		if(currentImage == handDown)
			super.render(g, thumbDown, size);
		else
			super.render(g, thumbUp, size);
	}
	public void turnUp(){
		currentImage = handUp;
	}
	public void turnDown(){
		currentImage = handDown;
	}

}