package connections;

import com.esotericsoftware.kryonet.Client;

import enitities.Player;

	public class Upload implements Runnable{

	Client client;
	Object object;
	public Upload(Client client, Object object){
		this.client = client;
		this.object = object;
	}
	@Override
	public void run() {
		client.sendUDP(object);
	}

}
