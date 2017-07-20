package textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import components.CollidableObject;
import components.DrawableObject;
import tools.Toolbox;

public class WeaponImagePack extends CollidableObject{
	public Image sheathed,
					drawn;
	public Image currentImage;
	public final Vector2f position = new Vector2f();
	private float rotation=0;
	public final Shape handle;
	public WeaponImagePack(Image sheathed, Image drawn, Shape hitbox, Shape handle) {
		super(new Vector2f(0, 0), new Vector2f(1, 1), hitbox, 0, 1, true, false, true);
		this.sheathed = sheathed;
		this.drawn = drawn;
		this.handle = handle;
	}
	@Override
	public void render(Graphics g, Vector2f size){
		if(rotation%Math.PI<0)
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
	public void setSheathed(){
		currentImage = sheathed;
	}
	public void setDrawn(){
		currentImage = drawn;
	}
	public boolean isDrawn(){
		return drawn == currentImage;
	}
	
}
