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
	public HandImagePack(Image side, Image handDown, Image thumbDown, Image handUp, Image thumbUp) {
		super(side, new Vector2f(), new Vector2f(1, 1), 0);
		this.handDown = handDown;
		this.thumbDown = thumbDown;
		this.handUp = handUp;
		this.thumbUp = thumbUp;
	}
	@Override
	public void render(Graphics g, Vector2f size){
		super.render(g, size);
	}

	public void turnUp(){
		up = true;
	}
	public void turnDown(){
		up = false;
	}

}