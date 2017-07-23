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
	public final Vector2f downAnchor, upAnchor;
	
	public HandImagePack(Image side, Image handDown, Image thumbDown, Vector2f downAnchor, Image handUp, Image thumbUp, Vector2f upAnchor) {
		super(side, new Vector2f(), new Vector2f(1, 1), 0);
		this.handDown = handDown;
		this.thumbDown = thumbDown;
		this.handUp = handUp;
		this.thumbUp = thumbUp;
		this.upAnchor = upAnchor;
		this.downAnchor = downAnchor;
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