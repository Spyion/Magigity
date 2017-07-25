package connections;

import java.io.BufferedReader;
import java.io.IOException;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import enitities.Entity;
import enitities.OnlineCharacter;
import info.Information;
import structs.CharacterBooleans;
import structs.CharacterFloats;

public class OnlineCharacterReader implements Runnable{

	private BufferedReader reader;
	private final float CM = Information.CENTIMETER;

	public OnlineCharacterReader(BufferedReader reader){
		this.reader = reader;
		
	}
	@Override
	public void run() {
		try{
		String s = "";
		while((s = reader.readLine()) != null){
			if(s.equals("f"))
				break;
			String[] strs = s.split(",");
			for(int i = 0; i < strs.length; i++){
				strs[i] = strs[i].trim();
			}
			System.out.println(s);
			new OnlineCharacter(strs[0],new Circle(Float.parseFloat(strs[1]),Float.parseFloat(strs[2]),25*CM),new Rectangle(0,0,75*CM, 25*CM), new Vector2f(1,1), 0, 1);
		}
		while((s = reader.readLine()) != null){
			String[] strs = s.split(",");
			for(int i = 0; i < strs.length; i++){
				strs[i] = strs[i].trim();
			}
			if(strs[0].equals("Online")){
				new OnlineCharacter(strs[1],new Circle(Float.parseFloat(strs[2]),Float.parseFloat(strs[3]),25*CM),new Rectangle(0,0,75*CM, 25*CM), new Vector2f(1,1), 0, 1);
			}
			else if(strs[0].equals("Offline")){
				for(Entity entity : Entity.entities){
					if(entity instanceof OnlineCharacter){
						OnlineCharacter c = (OnlineCharacter) entity;
						if(c.ID.equals(strs[1]))
							Entity.toRemove.add(c);
					}
				}
			}else{
				for(Entity entity : Entity.entities){
					if(entity instanceof OnlineCharacter){
						OnlineCharacter c = (OnlineCharacter) entity;
						if(c.ID.equals(strs[0])){
							CharacterBooleans bools = new CharacterBooleans();
							bools.read(strs[1]);
							c.isMovingDown = bools.isMovingDown;
							c.isMovingUp = bools.isMovingUp;
							c.isMovingLeft = bools.isMovingLeft;
							c.isMovingRight = bools.isMovingRight;
							c.isSprinting = bools.isSprinting;
							c.setAttacking(bools.isAttacking);
							c.setBlocking(bools.isBlocking);
							
							if(strs.length>2)
								c.setTargetRotationRadians(Float.parseFloat(strs[2]));
							if(strs.length>3)
								c.cameraRotation = Float.parseFloat(strs[3]);
							if(strs.length>4)
								c.position.x = Float.parseFloat(strs[4]);
							if(strs.length>5)
								c.position.y = Float.parseFloat(strs[5]);
						}
							
					}
				}
			}
			
		}
		
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
