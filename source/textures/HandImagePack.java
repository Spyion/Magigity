package textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import tools.Toolbox;

public class HandImagePack extends DrawableObject{
	public Image		 	handDown,
							thumbDown,
							handUp,
							thumbUp;
	public boolean up = true;
	public final Vector2f downAnchor, upAnchor;
	
	public HandImagePack(Image side, Image handDown, Image thumbDown, Vector2f downAnchor, Image handUp, Image thumbUp, Vector2f upAnchor, Vector2f scaling) {
		super(side, new Vector2f(), new Vector2f(1, 1), 0);
		this.handDown = handDown;
		this.thumbDown = thumbDown;
		this.handUp = handUp;
		this.thumbUp = thumbUp;
		this.upAnchor = new Vector2f(upAnchor.x*scaling.x, upAnchor.y*scaling.y);
		this.downAnchor = new Vector2f(downAnchor.x*scaling.x, downAnchor.y*scaling.y);
		}
	@Override
	public void render(Graphics g, Vector2f size){
		super.render(g, size);
	}
	public void renderUpper(Graphics g, Vector2f size, Vector2f position, float rotation){
		if(up){
			render(g, thumbUp, position, rotation, upAnchor);

		}else{
			render(g, handDown, position, rotation, downAnchor);		}
	}
	public void renderLower(Graphics g, Vector2f size, Vector2f position, float rotation){
		
		if(up){
			render(g, handUp, position, rotation, upAnchor);
		}else{
			render(g, thumbDown, position, rotation, downAnchor);
		}
	}
	private void render(Graphics g, Image image, Vector2f pos, float rotation, Vector2f anchor){
		Image toDraw = image.copy();
		toDraw.setCenterOfRotation(anchor.x, anchor.y);
		toDraw.rotate(rotation);
		toDraw.draw(pos.x-anchor.x, pos.y-anchor.y);
	}

	public void turnUp(){
		up = true;
	}
	public void turnDown(){
		up = false;
	}

}