package packets;

public class Attack {
	public final byte ID;
	public final byte animation;
	public Attack(){
		ID = 0;
		animation = 0;
	}
	public Attack(byte iD, byte animation) {
		super();
		ID = iD;
		this.animation = animation;
	}
}
