import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Vector2f;

public class Tile {
	int positionX;
	int positionY;
	int textureID;
	Tile[] neighbours = new Tile[8];
	boolean gradient;
	boolean[] gradients = new boolean[8];
	GradientFill[] fills = new GradientFill[8];
	
	TileMap map;
	public static int f = 40;
	public static float d = 0.0000001f;
	public Tile(TileMap map, int textureID,int positionX, int positionY, boolean gradient) {
		super();
		this.map = map;
		this.positionX = positionX;
		this.positionY = positionY;
		this.gradient = gradient;
		this.textureID = textureID;
	}
	public void updateGradients(){
		for(int i = 0; i < neighbours.length; i++){
			if(neighbours[i] != null){
				if(i == 4 || i==6 || i==7)
				if(neighbours[i].textureID==textureID){
					gradients[i] = false;
				}else{
					gradients[i] = true;
					fills[i] = generateFill(neighbours[i]);
				}
			}
		}
	}
	private GradientFill generateFill(Tile tile){
//		return new GradientFill(positionX*map.tileWidth, positionY*map.tileHeight, new Color(1,1,1,1),
//								tile.positionX*map.tileWidth, tile.positionY*map.tileHeight, new Color(0,0,0,0));
		int xDif = (int) Math.signum(positionX -tile.positionX);
		int yDif = (int) Math.signum(positionY -tile.positionY);
//		return new GradientFill(-xDif,-yDif,new Color(1,1,1,1f),xDif,yDif,new Color(0,0,0,0));
		if(xDif != 0 && yDif != 0)
			return new GradientFill(f*xDif,f*yDif,new Color(1,1,1,1f),f+d*xDif,f+d*yDif,new Color(0,0,0,0));
		return new GradientFill(40*xDif,40*yDif,new Color(1,1,1,1f),41*xDif,41*yDif,new Color(0,0,0,0));
	}
	public void render(Graphics g){
		map.rect.setX(positionX*map.tileWidth);
		map.rect.setY(positionY*map.tileHeight);
		g.texture(map.rect, map.textures.get(textureID));
		for(int i = 0; i < neighbours.length; i++){
			if(gradients[i]){
				g.texture(map.rect, map.textures.get(neighbours[i].textureID), fills[i]);
			}
		}
	}
}
