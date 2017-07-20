package weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;
import components.DrawableObject;
import tools.Toolbox;

public class Weapon extends CollidableObject{
	public Image sheathed,
	drawn;
	public Image currentImage;
	public final Vector2f position = new Vector2f();
	public float rotation=0;
//	public final Shape handle;
//	public Weapon(Image sheathed, Image drawn, Shape hitbox, Shape handle) {
//		super(new Vector2f(0, 0), new Vector2f(1, 1), hitbox, 0, 1, true, false, true);
//		this.sheathed = sheathed;
//		this.drawn = drawn;
//		this.handle = handle;
//	}
	public Weapon(){
		super(null, null, null, null, 0, 0, false, false, false);
	}
	@Override
	public void render(Graphics g, Vector2f size){
		if(isFlipped())
			super.render(g, currentImage.getFlippedCopy(true, false), size);
		else
			super.render(g, currentImage, size);
	}
	
	public void update(int delta, DrawableObject parent){
		if(parent != null){
			super.setRotationRadians(parent.getRotationRadians()+rotation);
			super.position.set(Toolbox.getParentToWorldPosition(position, parent));
		}
	}
	@Override
	public void render(Graphics g, Image image, Vector2f size, float rotation){
		if(image != null){
			Image toDraw = image.getScaledCopy((int)(size.x*image.getWidth()), (int)(size.y*image.getHeight()));
			toDraw.rotate(rotation);
			toDraw.drawCentered(position.x, position.y);
		}
	}
//	public float getUpperHandPosition(){
//		return collider.getHitboxType().getHeight()/2-handle.getHeight();
//	}
	public float getLowerHandPosition(){
		return collider.getHitboxType().getHeight()/2;
	}
	public void setSheathed(){
		currentImage = sheathed;
	}
	public void setDrawn(){
		currentImage = drawn;
	}
	public boolean isDrawn(){
		return drawn == currentImage;
	}
	public boolean isFlipped(){
		return Math.abs(rotation%(2*Math.PI))<Math.PI && isDrawn();
	}
}
