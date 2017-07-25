package packets;

public class CharacterShorts {
	public final short rotation;
	public final int positionX;
	public final int positionY;
	
	public final byte ID;
	
	
	public CharacterShorts(byte iD, short rotation, int positionX, int positionY) {
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
