package connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import enitities.OnlineCharacter;
import enitities.Player;

public class ConnectionHandler implements Runnable{
	
	private Socket client = null;
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	private final Player player;
	
	
	public ConnectionHandler(Player player, String IP, int PORT) {
		super();
		this.player = player;
		try{
			client = new Socket(IP, PORT);
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream());
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
//		writer.println("Spyion, "+"marcoderspion");
		writer.println("Info, "+"info");
		writer.flush();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new OnlineCharacterReader(reader)).start();
		new Thread(new PlayerSender(player, writer)).start();
	}

}
