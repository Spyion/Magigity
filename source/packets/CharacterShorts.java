package packets;

public class CharacterShorts {
	public final short rotation;
	public final short positionX;
	public final short positionY;
	
	public final byte ID;
	
	
	public CharacterShorts(byte iD, short rotation, short positionX, short positionY) {
		super();
		this.rotation = rotation;
		this.positionX = positionX;
		this.positionY = positionY;
		ID = iD;
	}


	public CharacterShorts(){
		this.ID = 0;
		this.rotation = 0;
		this.positionX = 0;
		this.positionY = 0;
	}

}
