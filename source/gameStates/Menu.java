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

import connections.ConnectionHandler;
import connections.mysqlconn;
import gui.Button;
import info.Information;
import tools.Loader;
import tools.States;

public class Menu extends BasicGameState{
	Input input;
	Button start;
	Image background;
	Image magigity;
	TextField name;
	TextField pw;
	Button submit;
	int isText = 0;
	Image text;
	int y = 100;
	Button quit;
	ConnectionHandler connectionHandler;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		States.menuState = this;
		
		
		connectionHandler = new ConnectionHandler("localhost", 25643, 25646);
		
		Button.defaultPressSound = Loader.loadSound("clap");
		Button.defaultReleaseSound = Loader.loadSound("knock");

		input = new Input(Input.ANY_CONTROLLER);
		start = new Button("Start", Loader.loadImage("btnstart"),
							Display.getWidth()/2-100,Display.getHeight()/2-50,200,100);
		magigity = Loader.loadImage("Magigity","png",358,105);
		background = Loader.loadImage("MenuBackground");
		
        name = new TextField(gc, gc.getDefaultFont(), 880-50, Display.getHeight()/2-40, 100, 30);
        pw = new TextField(gc, gc.getDefaultFont(), 880-50, Display.getHeight()/2, 100, 30);
        name.setText("Username");
        pw.setText("Password");
        pw.setMaskEnabled(true);
        submit = new Button(Loader.loadImage("btnstart"),
        					880-100,Display.getHeight()/2+50,200,50);
        String path = "/resources/images/magigityIcon16x16.png"; 
        gc.setIcon(path);
        
        text = Loader.loadImage("Magigity", "png", 358, 105);
        	
        quit = new Button(Loader.loadImage("btnquit"),780,480,200,50);
        
	}
	
	boolean pwHadFocus;
	boolean nameHadFocus;
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {	
		if(!pwHadFocus && pw.hasFocus()){
			pwHadFocus = true;
			pw.setText("");
			Button.defaultReleaseSound.play();
		}else if(!nameHadFocus && name.hasFocus()){
			nameHadFocus = true;
			name.setText("");
			Button.defaultReleaseSound.play();
		}else if(nameHadFocus && !name.hasFocus()&&name.getText().equals("")){
			nameHadFocus = false;
			name.setText("Username");
		}else if(pwHadFocus && !pw.hasFocus()&&pw.getText().equals("")){
			pwHadFocus = false;
			pw.setText("Password");
		}
		quit.update(input);
		submit.update(input);
		if(submit.isPressedAndReleased()) {
			
			connectionHandler.tryToLogin(name.getText(),pw.getText());
			int count = 0;
			while(true){
				System.out.println(connectionHandler.networkListener.answered);
				if(connectionHandler.networkListener.answered){
					break;
				}
				System.out.println(count);
				if(count++ > 1000000){
					System.err.println("SERVER IS OFFLINE");
					break;
				}
			}
			connectionHandler.networkListener.answered = false;
			if(Information.loggedIn) {
				
				States.runningState.customInit();
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
