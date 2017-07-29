package settings;

import java.util.ArrayList;

import tools.Loader;

public class KeySettings {
	public static int up, down, left, right, sprint, draw;
	
	public static final String location = "data/settings/keys";
	static{
		loadKeySettings();
	}
	
	public static void loadKeySettings(){
		ArrayList<ArrayList<String>> settings = Loader.csv.readCSV(location);
		ArrayList<String> list;
		list = Loader.getVar("up", settings);
		up = Integer.parseInt(list.get(1));
		list = Loader.getVar("down", settings);
		down = Integer.parseInt(list.get(1));
		list = Loader.getVar("right", settings);
		right = Integer.parseInt(list.get(1));
		list = Loader.getVar("left", settings);
		left = Integer.parseInt(list.get(1));
		list = Loader.getVar("sprint", settings);
		sprint = Integer.parseInt(list.get(1));
		list = Loader.getVar("draw", settings);
		draw = Integer.parseInt(list.get(1));
		
	}
	public static void writeCurrent(){
		
		ArrayList<ArrayList<String>> settings = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 6; i++)
			settings.add(new ArrayList<String>());
		settings.get(0).add("up");
		settings.get(0).add(Integer.toString(up));
		settings.get(1).add("down");
		settings.get(1).add(Integer.toString(down));
		settings.get(2).add("left");
		settings.get(2).add(Integer.toString(left));
		settings.get(3).add("right");
		settings.get(3).add(Integer.toString(right));
		settings.get(4).add("sprint");
		settings.get(4).add(Integer.toString(sprint));	
		settings.get(5).add("draw");
		settings.get(5).add(Integer.toString(draw));	
		Loader.csv.writeCSV(location, settings);
	}
}
