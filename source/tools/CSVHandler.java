package tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVHandler {
	
	public ArrayList<ArrayList<String>> readCSV(String ref){
		ArrayList<ArrayList<String>> csv = new ArrayList<ArrayList<String>>();
		FileReader fr = null;
		try {
			fr = new FileReader(ref);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String currentLine;
		int line = 0;
		while(true){
			try {
				currentLine = br.readLine();
				if(currentLine == null)
					break;
				String [] values = currentLine.split(",");
				csv.add(new ArrayList<String>());
				for(String str : values){
					str = str.trim();
					csv.get(line).add(str);
				}
				line++;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		
		return csv;
	}
	public void writeCSV(String ref, ArrayList<ArrayList<String>> lists){
			PrintWriter writer;
			try {
				writer = new PrintWriter(ref);
			} catch (FileNotFoundException e) {
				File file = new File(ref);
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					writer = new PrintWriter(ref);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					return;
				}
			}
			for(ArrayList<String> list : lists){
				for(Object value : list){
					writer.print(value.toString()+", ");
				}
				writer.println();
			}
			
			writer.close();

	}
}
