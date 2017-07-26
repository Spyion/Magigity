package packets;

public class Online {
	public final byte ID;
	public final int positionX;
	public final int positionY;
	public final String name;
	public final byte booleans;
	// 0: isWeaponDrawn
	// 1:
	// 2:
	// 3:
	// 4:
	// 5:
	// 6:
	// 7:
	public Online(byte iD, String name, int positionX, int positionY, byte booleans) {
		super();
		ID = iD;
		this.positionX = positionX;
		this.positionY = positionY;
		this.name = name;
		this.booleans = booleans;
	}
	public Online() {
		super();
		ID = 0;
		this.positionX = 0;
		this.positionY = 0;
		this.name = null;
		this.booleans = 0;
	}

	
	
}
