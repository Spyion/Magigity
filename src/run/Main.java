package run;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import GameStates.Fight;
import GameStates.Menu;

public class Main extends StateBasedGame{
	public Main(String name) {
		super(name);
	}

	public static void main(String[] args){
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Main("Hello"));
			app.setDisplayMode(1080, 720, false);
			app.setAlwaysRender(true);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new Menu());
		addState(new Fight());
		
	}
	public void changeState(int s, GameContainer gc){
		gc.getInput().clearKeyPressedRecord();
		enterState(s);
	}
}
