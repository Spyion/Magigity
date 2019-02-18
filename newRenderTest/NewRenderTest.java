import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import components.DrawableObject;
import enitities.Camera;
import info.Information;
import tools.Loader;

public class NewRenderTest extends BasicGame{

	public NewRenderTest(String title) {
		super(title);
	}

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new NewRenderTest("Hello"), 1080, 720, false);
//			app.setAlwaysRender(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	DrawableObject test;
	DrawableObject testParent;
	Camera camera;
	@Override
		public void init(GameContainer container) throws SlickException {
		testParent = new DrawableObject(Loader.loadModel("terrain/grass"), null);
		test = new DrawableObject(Loader.loadModel("terrain/stone"), testParent);
		camera = new Camera();
		Information.currentCamera = camera;
		}
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		testParent.size.set(1, 1);
		test.size.set(1,1);
		test.position.set(1,1);
//		testParent.addToRotationDegrees(1);
//		test.position.add(0, 0);
//		testParent.addToRotationDegrees(delta/10f);
//		test.position.add(new Vector2f(delta/100f,delta/100f));
//		test.addToRotationDegrees(delta/10f);
//		testParent.addToRotationDegrees(delta/10f);
//		camera.addToRotationDegrees(delta/100f);
//		camera.position.add(0, delta/10f);
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		
		g.setBackground(Color.blue);
		testParent.render(g);
		test.render(g);
//		g.drawRect(500, 300, 50, 50);

	}

	

	

}
