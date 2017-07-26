package tools;

public class BoolCoder {
	
	public static byte encode(boolean...bs){
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
		return b;
	}
	
	public static boolean[] decode(byte bt){
		boolean[] b = new boolean[8];
		for(int i = 0; i < 8; i++){
			byte f = 1;
			for(int j = 0; j < i; j++){
				f*=2;
			}
			
			if((bt & f)==f){
				b[i] = true;
			}
		}
		return b;
	}
}
