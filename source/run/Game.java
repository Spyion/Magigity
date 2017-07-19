package run;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import gameStates.Menu;
import gameStates.Running;

public class Game extends StateBasedGame{
	public Game(String name) {
		super(name);
	}

	public static void main(String[] args){
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Game("Hello"));
			app.setDisplayMode(1080, 720, false);
			app.setAlwaysRender(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
//		addState(new Menu());
		addState(new Running());
		
	}
	@Override
	public boolean closeRequested(){
		//clean up
		
		//EntityShader.cleanUp();
		return true;
	}
}
