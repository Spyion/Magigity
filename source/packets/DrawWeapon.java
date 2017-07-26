package packets;

public class DrawWeapon {
	public final byte drawn;
	public final byte ID;
	public DrawWeapon(byte iD, byte drawn) {
		super();
		this.drawn = drawn;
		ID = iD;
	}
	public DrawWeapon() {
		super();
		drawn = 0;
		ID = 0;
	}
}
