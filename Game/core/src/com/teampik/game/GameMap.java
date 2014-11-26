package com.teampik.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameMap extends TiledMap{
	
	MyGdxGame game;
	MapLayout mapLayout;
	
	public static int tileRadius = 32;
	public static int tileHeight  = 56; 						/*= (int) (((float) radius) * Math.sqrt(3)) This is correct mathematically, where a radius of 32 would get a height of just under 56. Since we are working with a texture, we can just take the hieght of that instead.*/
	public static int tileWidth  = tileRadius * 2;
	public static int tileSide= tileRadius * 3 / 2;
	
	
	public GameMap(MyGdxGame g){		
		game = g;
		
	}
	
	public static GameMap createMap(MyGdxGame game, MapLayout mapLayout) {
		
		
		GameMap map = new GameMap(game);
		MapLayers layers = map.getLayers();

		
		TiledMapTileLayer baseLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight); //Number of tiles in x direction, Number of tiles in y direction, pixel width of tile, pixel height of tile
		TiledMapTileLayer[] borderLayers = new TiledMapTileLayer[6]; // One for each direction
		
		for (int i = 0; i < borderLayers.length;i++){
			borderLayers[i] = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		}
		
		
		//Base Tiles (Land, water etc.)
		for (int i = 0; i < mapLayout.tiles.length; i++){
			for (int j = 0; j < mapLayout.tiles[i].length; j++){
				Cell cell = new Cell();
				
						
				cell.setTile(mapLayout.tiles[i][j]);
				
				
				//Have to swap i and j and then invert i to compensate for differences in how coordinates and array are indexed 
				//baseLayer.setCell(j, mapLayout.tiles.length - i - 1, cell);
				baseLayer.setCell(i, j, cell);
				
			}
		}
		
		for (int i = Direction.NORTH; i <= Direction.NORTH_WEST; i++){
			for (Vector2 coordinate : mapLayout.borders[i])
			{
				Cell cell = new Cell();
				cell.setTile(new MapTile(game.trBorders[i]));
				borderLayers[i].setCell((int) coordinate.x,  (int) coordinate.y, cell);
			}
		}
			
			
		
		

		layers.add(baseLayer);
		
		for (int i = 0; i < borderLayers.length;i++){
			layers.add(borderLayers[i]);
		}
		
		
		return map;
		
		
	}

	public static Vector2 getCoordsFromPoint(int mouseX, int mouseY, Vector3 camPos){

		int adjustedMouseY = Gdx.graphics.getHeight() - mouseY;

		int halfScreenWidth = Gdx.graphics.getWidth() / 2;
		int halfSscreenHeight = Gdx.graphics.getHeight() / 2;


		Vector2 coords = Vector2.Zero;

		//Adjust for camera position.
		int extraI2 = (int) (camPos.x - halfScreenWidth) % tileSide;	//These will be used for adjusting for camera positions not exactly lining up with hexagons
		int extraJ2 = (int) (camPos.y - halfSscreenHeight) % tileHeight;	//Not used yet.

		int coordI = (int)Math.floor(((float)mouseX)/(float)tileSide);

		//Adjust for camera position.
		int extraI = (int) ((camPos.x - /*camInitPos.x*/ halfScreenWidth) / tileSide);
		int extraJ = (int) ((camPos.y - /*camInitPos.y*/ halfSscreenHeight) / tileHeight);



		int coordIAdjusted = coordI + extraI;


		int insideTileX = mouseX - tileSide*coordI;

		int tempJ = adjustedMouseY - (((coordIAdjusted + 1) % 2) * tileHeight / 2);
		int coordJ = (int)Math.floor((float)tempJ/(float)tileHeight);
		int coordJAdjusted = coordJ + extraJ;
		int insideTileY = tempJ - tileHeight*coordJ;

		if (insideTileX > Math.abs(tileRadius / 2 - tileRadius * insideTileY / tileHeight)) {
			coords.x = coordI;
			coords.y = coordJ;
		} else {
			coords.x = coordI - 1;
			coords.y = (coordJ + ((coordIAdjusted/* + extraJ*/) % 2) - ((insideTileY < tileHeight / 2) ? 1 : 0));

		}


		coords.x += extraI;
		coords.y += extraJ;

		return coords;

	}

}
