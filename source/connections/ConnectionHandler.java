package connections;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import enitities.Player;
import info.Information;
import packets.Attack;
import packets.CharacterShorts;
import packets.CharactersRequest;
import packets.ChatMessage;
import packets.DrawWeapon;
import packets.LoginAnswer;
import packets.LoginRequest;
import packets.News;
import packets.Offline;
import packets.Online;
import packets.hasHit;
import tools.BoolCoder;

public class ConnectionHandler{
	
	public Client client;	
	public static ConnectionHandler instance;
	
	public final String IP;
	public final int PORT;
	public final int PORT2;
	public NetworkListener networkListener;
	public ConnectionHandler(String IP, int PORT, int PORT2) {
		super();
		this.IP = IP;
		this.PORT = PORT;
		this.PORT2 = PORT2;
		instance = this;
		client = new Client();
		registerPackets();

		try {
			client.start();
			client.connect(50000, IP, PORT, PORT2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.set(Log.LEVEL_INFO);
		networkListener = new NetworkListener();
		client.addListener(networkListener);
	}

	private void registerPackets(){
		Kryo kryo = client.getKryo();
		kryo.register(CharacterShorts.class);
		kryo.register(LoginAnswer.class);
		kryo.register(LoginRequest.class);
		kryo.register(News.class);
		kryo.register(Online.class);
		kryo.register(Offline.class);
		kryo.register(ChatMessage.class);
		kryo.register(String.class);
		kryo.register(String[].class);
		kryo.register(CharactersRequest.class);
		kryo.register(Attack.class);
		kryo.register(DrawWeapon.class);
		kryo.register(hasHit.class);
	}
	public void tryToLogin(String username, String password){
		if(client!=null)
			client.sendTCP(new LoginRequest(username, password));
	}
//	public void uploadBools(Player player){
//		if(client!=null)
//			new Thread(new Upload(client, player.getBools())).start();
//	}
	
	public void upload(Object object){
		if(client!=null)
			new Thread(new Upload(client, object)).start();
	}
	public void getCharacters(){
		new Thread(new Upload(client, new CharactersRequest())).start();
	}
	public void uploadDrawn(boolean drawn){
		System.out.println(Information.PlayerID);
		new Thread(new Upload(client, new DrawWeapon(Information.PlayerID ,BoolCoder.encode(drawn)))).start();
	}
	public void uploadAttack(int animation){
		new Thread(new Upload(client, new Attack(Information.PlayerID ,(byte)animation))).start();

	}

}
