package packets;

public class LoginAnswer {
	public final boolean loginSucceded;
	public final byte ID;
	public LoginAnswer(boolean loginSucceded, byte ID) {
		super();
		this.loginSucceded = loginSucceded;
		this.ID = ID;
	}
	public LoginAnswer(){
		this.loginSucceded = false;
		this.ID = 0;
	}
}
