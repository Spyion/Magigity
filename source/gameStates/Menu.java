package gameStates;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import connections.mysqlconn;
import gui.Button;
import tools.Loader;
import tools.States;

public class Menu extends BasicGameState{
	Input input;
	Button start;
	Image background;
	Image magigity;
	Loader loader = new Loader();
	TextField name;
	TextField pw;
	Button submit;
	Boolean pwtrue = true;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		Button.defaultPressSound = loader.loadSound("clap");
		Button.defaultReleaseSound = loader.loadSound("knock");

		input = new Input(Input.ANY_CONTROLLER);
		start = new Button("Start", loader.loadImage("basicbutton_pressed"), loader.loadImage("basicbutton_released"),
							Display.getWidth()/2-100,Display.getHeight()/2-50,200,100);
		magigity = loader.loadImage("Magigity","png",358,105);
		background = loader.loadImage("MenuBackground");
		
        name = new TextField(gc, gc.getDefaultFont(), Display.getWidth()/2-50, Display.getHeight()/2-40, 100, 30);
        pw = new TextField(gc, gc.getDefaultFont(), Display.getWidth()/2-50, Display.getHeight()/2, 100, 30);
        name.setText("Gamename");
        pw.setText("Password");
        pw.setMaskEnabled(true);
        submit = new Button("Submit", loader.loadImage("basicbutton_pressed"), loader.loadImage("basicbutton_released"),
        					Display.getWidth()/2-100,Display.getHeight()/2+50,200,100);
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {	
		//start.update(input);
		//if(start.isPressedAndReleased())
			//sbg.enterState(States.running);
		

		submit.update(input);
		if(submit.isPressedAndReleased()) {
			
			String pass = mysqlconn.checkPw(name.getText(),pw.getText());
			if(pass.equals("true")) {
				
				sbg.enterState(States.running);
				
			}
			else {
				
				System.out.println("Wrong Combination, Dude");
				pwtrue = false;
				
			}
			
		}
		
		background.rotate(0.01f*delta);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.drawCentered(Display.getWidth()/2, Display.getHeight()/2);
		g.setColor(Color.white);
		magigity.drawCentered(Display.getWidth()/2, magigity.getHeight()/2+10);
		//start.render(g);
		name.render(gc, g);
		pw.render(gc, g);
		submit.render(g);
		g.setBackground(new Color(230,0,0));
		if(pwtrue == false) {
			g.setColor(Color.white);
			g.drawString("Wrong Combination, Dude!",Display.getWidth()/2-100 , Display.getHeight()/2+200);
			
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return States.menu;
	}

}
