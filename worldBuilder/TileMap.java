import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class TileMap {
	Rectangle rect;
	public ArrayList<Image> textures = new ArrayList<Image>();
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public int tileWidth;
	public int tileHeight;
	public TileMap(int tileWidth, int tileHeight) {
		super();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		rect = new Rectangle(0,0,tileWidth,tileHeight);
	}
	public void updateGradients(){
		updateNeighbours();
		for(Tile tile: tiles){
			tile.updateGradients();
		}
	}
	public void addTile(int posX, int posY, Image texture, boolean gradient){
		int textureID = textures.indexOf(texture);
		
		if(textureID == -1){
			for(int i = 0; i < textures.size(); i++){
				if(textures.get(i).equals(texture)){
					textureID = i;
				}
			}
			if(textureID == -1){
				textureID = textures.size();
				textures.add(texture);
			}
		}
		
		tiles.add(new Tile(this, textureID, posX, posY, gradient));
	}
	public Tile getTile(int posX, int posY){
		for(Tile tile: tiles){
			if(posX == tile.positionX && posY == tile.positionY){
				return tile;
			}
		}
		return null;
	}
	private void updateNeighbours(){
		for(Tile tile : tiles){
			tile.neighbours[0] = getTile(tile.positionX-1, tile.positionY-1);
			tile.neighbours[1] = getTile(tile.positionX, tile.positionY-1);
			tile.neighbours[2] = getTile(tile.positionX+1, tile.positionY-1);
			tile.neighbours[3] = getTile(tile.positionX-1, tile.positionY);
			tile.neighbours[4] = getTile(tile.positionX+1, tile.positionY);
			tile.neighbours[5] = getTile(tile.positionX-1, tile.positionY+1);
			tile.neighbours[6] = getTile(tile.positionX, tile.positionY+1);
			tile.neighbours[7] = getTile(tile.positionX+1, tile.positionY+1);
		}
	}
	public void render(Graphics g){
		for(Tile tile : tiles)
			tile.render(g);
	}
	
	
}
