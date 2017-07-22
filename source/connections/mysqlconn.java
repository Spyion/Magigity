package connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class mysqlconn {
	
	static String name;
	static String pw;
	static String b;
	
	
	
	static public String checkPw(String qname, String qpw) {
		
		name = qname;
		pw = qpw;
		
		
		try{
       
         BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://magigity.bplaced.net/WebService/webservice.php?gamename="+name+"&gamepw="+pw).openStream()));
                 b = br.readLine();
                 System.out.println(b); // print the string b
                 System.out.println(name);
               

         } catch(IOException e){
             System.out.println("error");
         }
	
		return b;
}
	
	static public String getData(String qgamename, String qcolumn) {
		
		String back = "";
		String column = qcolumn;
		String gamename = qgamename;
		
		try{
		       
	         BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://magigity.bplaced.net/WebService/getOnlinePosition.php?gamename="+gamename+"&column="+column).openStream()));
	                 back = br.readLine();               

	         } catch(IOException e){
	             System.out.println("error");
	         }
		
		return back;
		
	}
	
	static public void setData(String qgamename, String qcolumn, String qvalue) {
		
		String value = qvalue;
		String column = qcolumn;
		String gamename = qgamename;
		
		try{
		       
	         BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://magigity.bplaced.net/WebService/setOnlinePosition.php?gamename="+gamename+"&column="+column+"&value="+value).openStream()));


	         } catch(IOException e){
	             System.out.println("error");
	         }
		
	}
	
}
