package connections;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

public class ClientUpdate implements Runnable{

	Client client;
	
	public ClientUpdate(Client client){
		this.client = client;
	}
	@Override
	public void run() {
		while(true)
			try {
				client.update(100);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
