package weapons;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import animations.ValueAnimation;
import components.CollidableObject;
import components.DrawableObject;
import tools.Toolbox;

public class Weapon extends CollidableObject{
	public Image sheathed,
				 drawn;
	public Image currentImage;
	public final Vector2f position = new Vector2f();
	public float rotation=0;
	
	public ArrayList<ArrayList<ValueAnimation>> animations;
	
	protected boolean doesFlip = true;
	protected final Vector2f upperHandPosition;
	protected final Vector2f lowerHandPosition;
	protected final Vector2f hitboxOffset; 
	protected final float upperHandRotation;
	protected final float lowerHandRotation;
	protected final Vector2f anchor;
	public final int TYPE;
	public static final int LONGSWING = 0;
	public static final int SHORTSWING = 1;
	public static final int BLUNT = 2;
	public static final int THRUST = 3;
	public static final int THROW = 4;
	public static final int STAFF = 5;
	public static final int BOW = 6;


//	public final Shape handle;
//	public Weapon(Image sheathed, Image drawn, Shape hitbox, Shape handle) {
//		super(new Vector2f(0, 0), new Vector2f(1, 1), hitbox, 0, 1, true, false, true);
//		this.sheathed = sheathed;
//		this.drawn = drawn;
//		this.handle = handle;
//	}
	public Weapon(int type, Image drawn, Image sheathed, Shape hitbox, Vector2f anchor,Vector2f upperHandPosition, Vector2f lowerHandPosition, float upperHandRotation, float lowerHandRotation, ArrayList<ArrayList<ValueAnimation>> animations){
		super(null, new Vector2f(), new Vector2f(1, 1), hitbox, 0, 0, false, false, true);
		this.TYPE = type;
		this.upperHandPosition  = upperHandPosition;
		this.lowerHandPosition = lowerHandPosition;
		this.animations = animations;
		this.anchor = anchor;
		this.upperHandRotation = upperHandRotation;
		this.lowerHandRotation = lowerHandRotation;
		this.drawn = drawn;
		this.sheathed = sheathed;
		hitboxOffset = hitbox.getLocation();
	}
	@Override
	public void render(Graphics g, Vector2f size){
		if(isFlipped() && doesFlip)
			super.render(g, currentImage.getFlippedCopy(true, false), size);
		else
			super.render(g, currentImage, size);
	}
	public void update(int delta, DrawableObject parent){
		if(parent != null){
			super.setRotationRadians(parent.getRotationRadians());
			super.position.set(Toolbox.getParentToWorldPosition(position, parent).add(hitboxOffset));
		}
	}
	@Override
	public void render(Graphics g, Image image, Vector2f size, float rotation){
		if(image != null){
			Image toDraw = image.getScaledCopy((int)(size.x*image.getWidth()), (int)(size.y*image.getHeight()));
			toDraw.setCenterOfRotation(anchor.x, anchor.y);
			toDraw.rotate(rotation+90);
			toDraw.draw(position.x-anchor.x, position.y-anchor.y);
			System.out.println(position+" "+ hitboxOffset);
		}
	}
	public Vector2f getUpperHandPosition(){
		return upperHandPosition;
	}
	public Vector2f getLowerHandPosition(){
		return lowerHandPosition;
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
