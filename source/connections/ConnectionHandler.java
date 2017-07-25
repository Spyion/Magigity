package connections;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import enitities.Player;
import packets.CharacterBooleans;
import packets.CharacterShorts;
import packets.CharactersRequest;
import packets.LoginAnswer;
import packets.LoginRequest;
import packets.News;
import packets.Offline;
import packets.Online;

public class ConnectionHandler{
	
	public Client client;	
	public static ConnectionHandler instance;
	
	public final String IP;
	public final int PORT;
	public NetworkListener networkListener;
	public ConnectionHandler(String IP, int PORT) {
		super();
		this.IP = IP;
		this.PORT = PORT;
		instance = this;
		client = new Client();
		registerPackets();

		try {
			client.start();
			client.connect(50000, IP, PORT, PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.set(Log.LEVEL_INFO);
		networkListener = new NetworkListener();
		client.addListener(networkListener);
	}

	private void registerPackets(){
		Kryo kryo = client.getKryo();
		kryo.register(CharacterBooleans.class);
		kryo.register(CharacterShorts.class);
		kryo.register(LoginAnswer.class);
		kryo.register(LoginRequest.class);
		kryo.register(News.class);
		kryo.register(Online.class);
		kryo.register(Offline.class);
		kryo.register(String[].class);
		kryo.register(CharactersRequest.class);
	}
	public void tryToLogin(String username, String password){
		if(client!=null)
			client.sendTCP(new LoginRequest(username, password));
	}
	public void uploadBools(Player player){
		if(client!=null)
			new Thread(new Upload(client, player.getBools())).start();
	}
	
	public void uploadShorts(Player player){
		if(client!=null)
			new Thread(new Upload(client, player.getShorts())).start();
	}
	public void getCharacters(){
		new Thread(new Upload(client, new CharactersRequest())).start();
	}

}
