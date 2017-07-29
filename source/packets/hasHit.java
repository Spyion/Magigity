package packets;

public class hasHit {
	public final byte IDhasHit;
	public final byte IDwasHit;
	public final float damage;
	public hasHit(byte iDhasHit, byte iDwasHit, float damage) {
		super();
		IDhasHit = iDhasHit;
		IDwasHit = iDwasHit;
		this.damage = damage;
	}
	public hasHit() {
		super();
		IDhasHit = 0;
		IDwasHit = 0;
		damage = 0;
	}
}
