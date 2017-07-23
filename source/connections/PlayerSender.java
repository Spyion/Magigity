package connections;

import java.io.PrintWriter;

import enitities.Player;
import info.Information;

public class PlayerSender implements Runnable{

	
	private final Player player;
	private final PrintWriter writer;
	
	public PlayerSender(Player player, PrintWriter writer){
		this.player = player;
		this.writer = writer;
	}
	
	
	@Override
	public void run() {
		try{
			int count = 0;
			while(true){
			String toWrite = "";
				
			toWrite+=player.isMovingUp;
			toWrite+=player.isMovingDown;
			toWrite+=player.isMovingLeft;
			toWrite+=player.isMovingRight;
			toWrite+=player.isSprinting;
			toWrite+=player.isAttacking();
			toWrite+=player.isBlocking();
			toWrite+=", ";
			toWrite+=Float.toString(player.getRotationRadians())+",";
			toWrite+=Float.toString(Information.currentCamera.getRotationRadians())+", ";
			if(count > 1000){
				count-=1000;
				
				toWrite+=Float.toString(player.position.x)+",";
				toWrite+=Float.toString(player.position.y)+",";
				
			}
			
			
			
			
			
			writer.println(toWrite);
			writer.flush();
			
			Thread.sleep(100);
			count+=100;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.close();
		}
	}
	
}
