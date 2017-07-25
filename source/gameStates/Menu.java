package gameStates;

import org.lwjgl.input.Mouse;
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
	int isText = 0;
	Image text;
	int y = 100;
	Button quit;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		Button.defaultPressSound = loader.loadSound("clap");
		Button.defaultReleaseSound = loader.loadSound("knock");

		input = new Input(Input.ANY_CONTROLLER);
		start = new Button("Start", loader.loadImage("btnstart"),
							Display.getWidth()/2-100,Display.getHeight()/2-50,200,100);
		magigity = loader.loadImage("Magigity","png",358,105);
		background = loader.loadImage("MenuBackground");
		
        name = new TextField(gc, gc.getDefaultFont(), 880-50, Display.getHeight()/2-40, 100, 30);
        pw = new TextField(gc, gc.getDefaultFont(), 880-50, Display.getHeight()/2, 100, 30);
        name.setText("Gamename");
        pw.setText("Password");
        pw.setMaskEnabled(true);
        submit = new Button(loader.loadImage("btnstart"),
        					880-100,Display.getHeight()/2+50,200,50);
        String path = "/resources/images/magigityIcon16x16.png"; 
        gc.setIcon(path);
        
        text = loader.loadImage("Magigity", "png", 358, 105);
        	
        quit = new Button(loader.loadImage("btnquit"),780,480,200,50);
        
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {	
		//start.update(input);
		//if(start.isPressedAndReleased())
			//sbg.enterState(States.running);
		
		quit.update(input);
		submit.update(input);
		if(submit.isPressedAndReleased()) {
			
			String pass = mysqlconn.checkPw(name.getText(),pw.getText());
			if(pass.equals("true")) {
				
				sbg.enterState(States.running);
				
			}
			else {
				
				System.out.println("Wrong Combination, Dude");
				isText = 2000;
				
			}
			
		}
		
		background.rotate(0.01f*delta);
		
		if(isText > 0) {
			
			isText--;
			
		}
		
		if(quit.isPressedAndReleased()) {
			
			gc.exit();
			
		}
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(false);
		background.drawCentered(880, Display.getHeight()/2);
		g.setColor(Color.white);
		magigity.drawCentered(880, magigity.getHeight()/2+10);
		//start.render(g);
		name.render(gc, g);
		pw.render(gc, g);
		submit.render(g);
		quit.render(g);
		g.setBackground(new Color(230,0,0));
		if(isText > 0) {
			g.setColor(Color.white);
			g.drawString("Wrong Combination, Dude!",Display.getWidth()/2-100 , Display.getHeight()/2+200);
			
		}
		int wheelDetect = Mouse.getDWheel();

		if(wheelDetect!=0) {
			
			y += wheelDetect/2;
			if(y < 100) {
				
				y = 100;
				
			}
			if(y > 620) {
				
				y = 620;
				
			}
			
				
			
			text.drawCentered(100, y);
			
		}
		else {
			
			text.drawCentered(100, y);
			
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return States.menu;
	}

}
