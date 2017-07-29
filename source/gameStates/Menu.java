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
import settings.KeySettings;
import tools.Loader;
import tools.States;

public class Menu extends BasicGameState{
	Input input;
	Button start;
	
	Button up;
	Button down;
	Button sprint;
	Button left;
	Button right;
	Button draw;
	
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
		
		KeySettings.loadKeySettings();
		States.menuState = this;
		
//		217.230.226.5 -> Marco IPv4
//		84.190.9.192 -> JohannesIPv4
		connectionHandler = new ConnectionHandler("84.190.9.192", 25643, 25646);
		
		Button.defaultPressSound = Loader.loadSound("clap");
		Button.defaultReleaseSound = Loader.loadSound("knock");

		up = new Button("Up", Loader.loadImage("BlackCircle"),
				Display.getWidth()/2,Display.getHeight()/2-150,100,50);
		down = new Button("Down", Loader.loadImage("BlackCircle"),
				Display.getWidth()/2,Display.getHeight()/2-100,100,50);
		right = new Button("Right", Loader.loadImage("BlackCircle"),
				Display.getWidth()/2,Display.getHeight()/2-50,100,50);
		left = new Button("Left", Loader.loadImage("BlackCircle"),
				Display.getWidth()/2,Display.getHeight()/2,100,50);
		sprint = new Button("Sprint", Loader.loadImage("BlackCircle"),
				Display.getWidth()/2,Display.getHeight()/2+50,100,50);
		draw = new Button("Draw", Loader.loadImage("BlackCircle"),
				Display.getWidth()/2,Display.getHeight()/2+100,100,50);
		
		input = new Input(Input.ANY_CONTROLLER);
		start = new Button("Start", Loader.loadImage("btnstart"),
							Display.getWidth()/2-100,Display.getHeight()/2-50,200,100);
		magigity = Loader.loadImage("Magigity","png",358,105);
		background = Loader.loadImage("MenuBackground");
		
        name = new TextField(gc, gc.getDefaultFont(), 880-50, Display.getHeight()/2-40, 100, 30);
        pw = new TextField(gc, gc.getDefaultFont(), 880-50, Display.getHeight()/2, 100, 30);
//        name.setText("Username");
//        pw.setText("Password");
        name.setText("Info");
        pw.setText("info");
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
		
		up.update(input);
		down.update(input);
		right.update(input);
		left.update(input);
		draw.update(input);
		sprint.update(input);
		
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
		if(up.isPressedAndReleased()){
			currentButton = up;
			passed = false;
		}else if(down.isPressedAndReleased()){
			currentButton = down;
			passed = false;
		}else if(left.isPressedAndReleased()){
			currentButton = left;
			passed = false;
		}else if(right.isPressedAndReleased()){
			currentButton = right;
			passed = false;
		}else if(sprint.isPressedAndReleased()){
			currentButton = sprint;
			passed = false;
		}else if(draw.isPressedAndReleased()){
			currentButton = draw;
			passed = false;
		}
		
		
		
		if(submit.isPressedAndReleased()) {
			
			connectionHandler.tryToLogin(name.getText(),pw.getText());
			int count = 0;
			while(true){
				if(connectionHandler.networkListener.getAnswered()){
					break;
				}
				if(count++ > 10000){
					System.err.println("SERVER IS OFFLINE");
					break;
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			connectionHandler.networkListener.answered = false;
			if(Information.loggedIn) {
				
				if(States.runningState.customInit());
				KeySettings.writeCurrent();
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
		background.drawCentered(Display.getWidth()/2, Display.getHeight()/2);
		g.setColor(Color.white);
		magigity.drawCentered(880, magigity.getHeight()/2+10);
		//start.render(g);
		name.render(gc, g);
		pw.render(gc, g);
		submit.render(g);
		quit.render(g);
		up.render(g);
		down.render(g);
		left.render(g);
		right.render(g);
		sprint.render(g);
		draw.render(g);
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
	boolean passed = false;
	Button currentButton;
	@Override
	public void keyPressed(int key, char c) {
		if(!passed){
			passed = true;
			if(currentButton == up){
				KeySettings.up = key;
			}else if(currentButton == down){
				KeySettings.down = key;
			}else if(currentButton == left){
				KeySettings.left = key;
			}else if(currentButton == right){
				KeySettings.right = key;
			}else if(currentButton == sprint){
				KeySettings.sprint = key;
			}else if(currentButton == draw){
				KeySettings.draw = key;
			}
		
		}
		super.keyPressed(key, c);
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return States.menu;
	}
}
