import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tests.GradientImageTest;

import tools.Loader;

public class WorldBuilder extends BasicGame{

	TileMap map;
	Image grass;
	Image stone;
	Image currentImage;

	public WorldBuilder(String title) {
		super(title);
	}
	@Override
	public void init(GameContainer container) throws SlickException {
		grass = Loader.loadImage("terrain/grass2");
		stone = Loader.loadImage("terrain/stone");
		currentImage = grass;
		map = new TileMap(100, 100);
//		for(int i = 0; i < 16; i++){
//			map.addTile(i%4, i/4, grass, true);
//		}
//		for(int i = 16; i < 32; i++){
//			map.addTile(i%4, i/4, stone, true);
//		}
//		for(int i = 0; i < 16; i++){
//			map.addTile(i%4+4, i/4, stone, true);
//		}
//		for(int i = 16; i < 32; i++){
//			map.addTile(i%4+4, i/4, grass, true);
//		}
//		map.addTile(0, 0, grass, true);
//		map.addTile(1, 0, stone, true);
		map.updateGradients();
	}@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
	}
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(g);
	}
	@Override
	public void mousePressed(int button, int x, int y) {
		if(button == Input.MOUSE_RIGHT_BUTTON){
			if(currentImage == grass)
				currentImage = stone;
			else
				currentImage = grass;
		}else{
			map.addTile(Math.round(x/map.tileWidth),
						Math.round(y/map.tileHeight),
						currentImage, true);
			map.updateGradients();
		}
		super.mousePressed(button, x, y);
	}
	@Override
	public void keyPressed(int key, char c) {
		int h = 10;
		if(c == 'j'){
			Tile.f-=h;
		}
		if(c == 'k'){
			Tile.f+=h;
		}
		if(c == 'u'){
			Tile.d+=h;
		}
		if(c == 'i'){
			Tile.d-=h;
		}
		System.out.println(Tile.f+" "+Tile.d);
		map.updateGradients();
		super.keyPressed(key, c);
	}
	

	public static void main(String[] args){
		try {
			AppGameContainer container = new AppGameContainer(new WorldBuilder("WorldBuilder"));
			container.setDisplayMode(1080,720,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
}
