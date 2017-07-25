package packets;

public class Online {
	public final byte ID;
	public final int positionX;
	public final int positionY;
	public final String name;
	public Online(byte iD, String name, int positionX, int positionY) {
		super();
		ID = iD;
		this.positionX = positionX;
		this.positionY = positionY;
		this.name = name;
	}
	public Online() {
		super();
		ID = 0;
		this.positionX = 0;
		this.positionY = 0;
		this.name = null;
	}

	
	
}
