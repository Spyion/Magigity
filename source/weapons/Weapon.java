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
	
	protected final Vector2f rightHandPosition;
	protected final Vector2f leftHandPosition;
	protected final Vector2f hitboxOffset; 
	private final float rightHandRotation;
	private final float leftHandRotation;
	public final Vector2f anchor;
	private final boolean rightHandFlipped;
	private final boolean leftHandFlipped;
	public final boolean doesFlip;
	public final int TYPE;
	public float damage = 400;
	
	
	public boolean flipped;
	
	public static final int LONGSWING = 0;
	public static final int SHORTSWING = 1;
	public static final int BLUNT = 2;
	public static final int THRUST = 3;
	public static final int THROW = 4;
	public static final int STAFF = 5;
	public static final int BOW = 6;
	
	private final int M = Information.METER;
	private final float CM = Information.CENTIMETER;
	private final float PI2 = (float) (2*Math.PI);
	
	public Weapon(int type, Image drawn, Image sheathed, Shape hitbox, Vector2f anchor,Vector2f rightHandPosition, Vector2f leftHandPosition, float rightHandRotation, float leftHandRotation, boolean rightHandFlipped, boolean leftHandFlipped, boolean doesFlip, ArrayList<ArrayList<ValueAnimation>> animations){
		super(null, new Vector2f(),new Vector2f(1, 1), hitbox, 0, 0, false ,false, true);
		this.TYPE = type;
		this.rightHandPosition  = rightHandPosition.sub(anchor);
		this.leftHandPosition = leftHandPosition.sub(anchor);
		this.animations = animations;
		this.anchor = anchor;
		this.rightHandRotation = rightHandRotation;
		this.leftHandRotation = leftHandRotation;
		this.rightHandFlipped = rightHandFlipped;
		this.leftHandFlipped = leftHandFlipped;
		this.doesFlip = doesFlip;
		this.drawn = drawn;
		this.sheathed = sheathed;
		hitboxOffset = hitbox.getLocation();
	}
	@Override
	public void render(Graphics g, Vector2f size){
		if(flipped)
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
	
	public boolean isRightHandFlipped(){
		if(flipped)
			return rightHandFlipped;
		else
			return leftHandFlipped;
	}
	public boolean isLeftHandFlipped(){
		if(flipped)
			return leftHandFlipped;
		else
			return rightHandFlipped;
	}
	public Vector2f getRightHandPosition() {
		if(flipped)
			return rightHandPosition.copy();
		else
			return leftHandPosition.copy();


	}
	public Vector2f getLeftHandPosition() {
		if(flipped)
			return leftHandPosition.copy();
		else
			return rightHandPosition.copy();
		
	}
	public float getRightHandRotation() {
		if(flipped)
			return rightHandRotation;
		else
			return leftHandRotation+180;
	}
	public float getLeftHandRotation() {
		if(flipped)
			return leftHandRotation;
		else
			return rightHandRotation+180;
		
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
	public boolean isFlipped() {
		return flipped;
	}
	
}
