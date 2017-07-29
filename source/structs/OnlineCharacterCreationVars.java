package structs;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import enitities.OnlineCharacter;
import info.Information;

public class OnlineCharacterCreationVars {
	static ArrayList<Integer> posX = new ArrayList<Integer>();
	static ArrayList<Integer> posY = new ArrayList<Integer>();
	static ArrayList<Byte> IDs = new ArrayList<Byte>();
	static ArrayList<String> name = new ArrayList<String>();
	protected final static float CM = Information.CENTIMETER;

	
	public static void addOnlineCharacter(byte ID, String n,int pX, int pY){
		IDs.add(ID);
		posX.add(pX);
		posY.add(pY);
		name.add(n);
	}
	public synchronized static void createCharacters(){
		if(!IDs.isEmpty()){
		new OnlineCharacter(IDs.remove(0), name.remove(0),new Circle(posX.remove(0), posY.remove(0), 25*CM),new Rectangle(0,0,75*CM, 25*CM), new Vector2f(1,1), 0, 1, 1000);
		}
	}
}
