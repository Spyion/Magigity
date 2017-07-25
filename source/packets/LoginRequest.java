package packets;

public class LoginRequest {
	public final String username;
	public final String password;
	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public LoginRequest(){
		this.username = null;
		this.password = null;
	}
}
