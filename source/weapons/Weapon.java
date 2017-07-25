package weapons;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import animations.ValueAnimation;
import components.CollidableObject;
import components.DrawableObject;
import info.Information;
import tools.Toolbox;

public class Weapon extends CollidableObject{
	public Image sheathed,
				 drawn;
	public Image currentImage;
	public final Vector2f relativePosition = new Vector2f();
	public float relativeRotation=0;
	
	public ArrayList<ArrayList<ValueAnimation>> animations;
	
	protected boolean doesFlip = true;
	protected final Vector2f upperHandPosition;
	protected final Vector2f lowerHandPosition;
	protected final Vector2f hitboxOffset; 
	public final float upperHandRotation;
	public final float lowerHandRotation;
	public final Vector2f anchor;
	
	public final int TYPE;
	
	
	
	public static final int LONGSWING = 0;
	public static final int SHORTSWING = 1;
	public static final int BLUNT = 2;
	public static final int THRUST = 3;
	public static final int THROW = 4;
	public static final int STAFF = 5;
	public static final int BOW = 6;
	
	public float testr = 0;
	private final int M = Information.METER;
	private final float CM = Information.CENTIMETER;
	
	public Weapon(int type, Image drawn, Image sheathed, Shape hitbox, Vector2f anchor,Vector2f upperHandPosition, Vector2f lowerHandPosition, float upperHandRotation, float lowerHandRotation, ArrayList<ArrayList<ValueAnimation>> animations){
		super(null, new Vector2f(),new Vector2f(1, 1), hitbox, 0, 0, false ,false, true);
		this.TYPE = type;
		this.upperHandPosition  = upperHandPosition.sub(anchor);
		this.lowerHandPosition = lowerHandPosition.sub(anchor);
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
			render(g, currentImage.getFlippedCopy(true, false), size, relativeRotation);
		else
			render(g, currentImage, size, relativeRotation);
	}
	public void update(int delta, DrawableObject parent){
		if(parent != null && isDrawn()){
			collider.setEnabled(true);
			setRotationRadians(parent.getRotationRadians()+relativeRotation);
			
			Shape hitbox = collider.getHitboxType();
			Vector2f hitboxOff = new Vector2f(hitbox.getWidth()/2,hitbox.getHeight()/2);

			Vector2f pos = Toolbox.getParentToWorldPosition(relativePosition, parent);
			Vector2f pos2 = pos.copy().sub(anchor).add(hitboxOffset);
			pos2.add(hitboxOff);
			Toolbox.rotateVectorAroundPosition(pos2, pos, (float)(180+Math.toDegrees(relativeRotation+parent.getRotationRadians())));
			position.set(pos2);
		}else{
			collider.setEnabled(false);
		}
		
	}
	@Override
	public void render(Graphics g, Image image, Vector2f size, float rotation){
		if(image != null){
			if(isDrawn()){
				Image toDraw = image.getScaledCopy((int)(size.x*image.getWidth()), (int)(size.y*image.getHeight()));
				toDraw.setCenterOfRotation(anchor.x, anchor.y);
				toDraw.rotate((float) (rotation+Math.toDegrees(relativeRotation)));
				toDraw.draw(relativePosition.x-anchor.x, relativePosition.y-anchor.y);
			}else{
				Image toDraw = image.getScaledCopy((int)(size.x*image.getWidth()), (int)(size.y*image.getHeight()));
				toDraw.rotate((float) (rotation+Math.toDegrees(relativeRotation)));
				toDraw.drawCentered(relativePosition.x, relativePosition.y);
			}
		}
	}
	
	
	public Vector2f getUpperHandPosition() {
		return upperHandPosition;
	}
	public Vector2f getLowerHandPosition() {
		return lowerHandPosition;
	}
	public float getUpperHandRotation() {
		return upperHandRotation;
	}
	public float getLowerHandRotation() {
		return lowerHandRotation;
	}
	public void setSheathed(){
		currentImage = sheathed;
	}
	public void setDrawn(){
		currentImage = drawn;
	}
	public void toggleDrawn(){
		if(isDrawn())
			currentImage = sheathed;
		else
			currentImage = drawn;
	}
	public boolean isDrawn(){
		return drawn == currentImage;
	}
	public boolean isFlipped(){
		return Math.abs(relativeRotation%(2*Math.PI))<Math.PI && isDrawn();
	}
}
