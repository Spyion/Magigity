package structs;

public class CharacterBooleans {
	public boolean isMovingUp;
	public boolean isMovingDown;
	public boolean isMovingLeft;
	public boolean isMovingRight;
	public boolean isSprinting;
	public boolean isBlocking;
	public boolean isAttacking;
	
	
	public void read(String b) throws NumberFormatException{
		
		if(!(b.contains("0") || b.contains("1")))
			throw new NumberFormatException();
		char[] array = b.toCharArray();
		if(array[0]=='0')
			isMovingUp = false;
		else
			isMovingUp = true;
		if(array[1]=='0')
			isMovingDown = false;
		else
			isMovingDown = true;
		if(array[2]=='0')
			isMovingLeft = false;
		else
			isMovingLeft = true;
		if(array[3]=='0')
			isMovingRight = false;
		else
			isMovingRight = true;
		if(array[4]=='0')
			isSprinting = false;
		else
			isSprinting = true;
		if(array[5]=='0')
			isBlocking = false;
		else
			isBlocking = true;
		if(array[6]=='0')
			isAttacking = false;
		else
			isAttacking = true;
		
	}
	public String toString(){
		return  writeBool(isMovingUp)+
				writeBool(isMovingDown)+
				writeBool(isMovingLeft)+
				writeBool(isMovingRight)+
				writeBool(isSprinting)+
				writeBool(isBlocking)+
				writeBool(isAttacking);
	}
	
	private String writeBool(boolean bool){
		if(bool)
			return "1";
		return "0";
	}
}
