package connections;

import java.io.PrintWriter;

import enitities.Player;
import info.Information;
import tools.Toolbox;

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
				
			toWrite+=Toolbox.booleanToString(player.isMovingUp);
			toWrite+=Toolbox.booleanToString(player.isMovingDown);
			toWrite+=Toolbox.booleanToString(player.isMovingLeft);
			toWrite+=Toolbox.booleanToString(player.isMovingRight);
			toWrite+=Toolbox.booleanToString(player.isSprinting);
			toWrite+=Toolbox.booleanToString(player.isAttacking());
			toWrite+=Toolbox.booleanToString(player.isBlocking());
			toWrite+=", ";
			toWrite+=Float.toString(player.getRotationRadians())+", ";
			toWrite+=Float.toString(Information.currentCamera.getRotationRadians());
			if(count > 100){
				count-=100;
				
				toWrite+=", "+Float.toString(player.position.x)+", ";
				toWrite+=Float.toString(player.position.y);
			}
			if(toWrite.contains("NaN")){
				System.out.println(toWrite);
			}
			
			
			
			writer.println(toWrite);
			writer.flush();
			
			Thread.sleep(10);
			count+=10;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			writer.close();
		}
	}
	
}
