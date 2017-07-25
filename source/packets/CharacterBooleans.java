package packets;

public class CharacterBooleans {
	
	public final byte booleans;
	
	public final byte ID;
	
	public CharacterBooleans(byte ID, boolean...bs){
		this.ID = ID;
		byte b = 0;
		for(int i = 0; i < bs.length; i++){
			if(bs[i]){
				byte f  = 1;
				for(int j = 0; j < i; j++){
					f*=2;
				}
				b += f;
			}
		}
		booleans = b;
	}
	
	public boolean[] decode(){
		boolean[] b = new boolean[8];
		for(int i = 0; i < 8; i++){
			byte f = 1;
			for(int j = 0; j < i; j++){
				f*=2;
			}
			
			if((booleans & f)==f){
				b[i] = true;
			}
		}
		return b;
	}
	
	public CharacterBooleans(){
		this.ID = 0;
		booleans = 0;
	}
}
