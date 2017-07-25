package connections;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import enitities.Entity;
import enitities.OnlineCharacter;
import info.Information;
import packets.CharacterBooleans;
import packets.CharacterShorts;
import packets.LoginAnswer;
import packets.Offline;
import packets.Online;
import structs.OnlineCharacterCreationVars;

public class NetworkListener extends Listener {
	private final float CM = Information.CENTIMETER;
	public boolean answered = false;
	@Override
	public void connected(Connection connection) {
		Log.info("Connected");
		super.connected(connection);
	}
	@Override
	public void disconnected(Connection connection) {
		Log.info("Server closed connection");

		super.disconnected(connection);
	}
	@Override
	public void received(Connection connection, Object object) {
		if(Information.loggedIn){
			if(object instanceof CharacterBooleans){
				CharacterBooleans b = (CharacterBooleans) object;
				for(Entity entity : Entity.entities){
					if(entity instanceof OnlineCharacter){
						OnlineCharacter c = (OnlineCharacter) entity;
						if(b.ID==c.ID)
							c.set(b);
					}
				}
			}
			else if(object instanceof CharacterShorts){
				CharacterShorts s = (CharacterShorts) object;
				System.out.println(s.ID);
				for(Entity entity : Entity.entities){
					if(entity instanceof OnlineCharacter){
						OnlineCharacter c = (OnlineCharacter) entity;
						if(s.ID==c.ID)
							c.set(s);
					}
				}
			}
			else if(object instanceof Offline){
				Offline off = (Offline) object;
				for(Entity entity : Entity.entities){
					if(entity instanceof OnlineCharacter){
						OnlineCharacter c = (OnlineCharacter) entity;
						if(off.ID==c.ID)
							Entity.remove(entity);
					}
				}
				
			}
			else if(object instanceof Online){
				Online on = (Online) object;
				OnlineCharacterCreationVars.addOnlineCharacter(on.ID, on.name, on.positionX, on.positionY);
			}
		}else if(object instanceof LoginAnswer){
			LoginAnswer answer = (LoginAnswer) object;
			if(answer.loginSucceded == true){
			Information.loggedIn = true;
			Information.PlayerID = answer.ID;
			System.out.println(Information.PlayerID);
			}
			answered = true;
		}
		super.received(connection, object);
	}
}
