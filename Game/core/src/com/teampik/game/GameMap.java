package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameMap extends TiledMap{
	
	MyGdxGame game;
	
	public static final int radius = 32;
	public static int height; 						/*= (int) (((float) radius) * Math.sqrt(3)) This is correct mathematically, where a radius of 32 would get a height of just under 56. Since we are working with a texture, we can just take the hieght of that instead.*/
	public static final int width = radius * 2;
	public static final int side = radius * 3 / 2;
	
	
	public GameMap(MyGdxGame g){		
		game = g;
		
	}
	
	public static GameMap createMap(MyGdxGame game, int[][] layout) {
		
		
		GameMap map = new GameMap(game);
		MapLayers layers = map.getLayers();

		
		TiledMapTileLayer baseLayer = new TiledMapTileLayer(MapLayout.width, MapLayout.height, TestTile.radius * 2, 56); //Number of tiles in x direction, Number of tiles in y direction, pixel width of tile, pixel height of tile
		
		
		for (int i = 0; i < layout.length; i++){
			for (int j = 0; j < layout[i].length; j++){
				Cell cell = new Cell();
				
				TestTile tile;
				switch (layout[i][j]){
				case MapLayout.WATER:
					tile = new TestTile(game.trWater); 
					break;
				case MapLayout.LAND:
					tile = new TestTile(game.trLand); 
					break;
				default:
					tile = new TestTile(game.trDefault); 
					break;
				}
				
				
				
				
				cell.setTile(tile);
				
				
				//Have to swap i and j and then invert i to compensate for differences in how coordinates and array are indexed 
				baseLayer.setCell(j, layout.length - i - 1, cell);
				
			}
		}
		

		layers.add(baseLayer);
		
		return map;
		
		
	}
	
public static Vector2 getCoordsFromPoint(int mouseX, int mouseY, Vector3 camPos){
		
		int adjustedMouseY = Gdx.graphics.getHeight() - mouseY;
		
		int halfScreenWidth = Gdx.graphics.getWidth() / 2;
		int halfSscreenHeight = Gdx.graphics.getHeight() / 2;
		
		
		Vector2 coords = Vector2.Zero;
		
		//Adjust for camera position.
		int extraI2 = (int) (camPos.x - halfScreenWidth) % side;	//These will be used for adjusting for camera positions not exactly lining up with hexagons
		int extraJ2 = (int) (camPos.y - halfSscreenHeight) % height;	//Not used yet.
		
		int coordI = (int)Math.floor(((float)mouseX)/(float)side);
		
		//Adjust for camera position.
		int extraI = (int) ((camPos.x - /*camInitPos.x*/ halfScreenWidth) / side);
		int extraJ = (int) ((camPos.y - /*camInitPos.y*/ halfSscreenHeight) / height);
		
		
		
		int coordIAdjusted = coordI + extraI;
		
		
        int insideTileX = mouseX - side*coordI;

        int tempJ = adjustedMouseY - (((coordIAdjusted + 1) % 2) * height / 2);
        int coordJ = (int)Math.floor((float)tempJ/(float)height);
        int coordJAdjusted = coordJ + extraJ;
        int insideTileY = tempJ - height*coordJ;

        if (insideTileX > Math.abs(radius / 2 - radius * insideTileY / height)) {
            coords.x = coordI;
            coords.y = coordJ;
        } else {
        	coords.x = coordI - 1;
        	coords.y = (coordJ + ((coordIAdjusted/* + extraJ*/) % 2) - ((insideTileY < height / 2) ? 1 : 0));
            
        }
                
        
        coords.x += extraI;
        coords.y += extraJ;
        
        return coords;
		
	}

}
