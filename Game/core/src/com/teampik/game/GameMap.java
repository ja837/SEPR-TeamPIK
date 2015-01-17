package com.teampik.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameMap extends TiledMap{
	
	final static int baseLayerIndex = 0;
	final static int borderLayerIndex[] = {1,2,3,4,5,6};
	final static int trackLayerIndex = 7;
	final static int zooLayerIndex = 8;
	final static int itemLayerIndex = 9;
	final static int trainLayerIndex = 10;
	final static int selectedTileLayerIndex = 11;
	
	
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
		
		TiledMapTileLayer trackLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		TiledMapTileLayer zooLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		
		//Base Tiles (Land, water etc.)
		for (int i = 0; i < mapLayout.tiles.length; i++){
			for (int j = 0; j < mapLayout.tiles[i].length; j++){
				Cell cell = new Cell();
				cell.setTile(mapLayout.tiles[i][j]);
			
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
		
		for (Vector2 coordinate : mapLayout.trackCoords){
			Cell cell = new Cell();
			cell.setTile(new TrackTile(game.trTrack, coordinate));
			trackLayer.setCell((int) coordinate.x,  (int) coordinate.y, cell);
		}
		
		for (ZooParams params : mapLayout.zooParams){
			Cell cell = new Cell();
			cell.setTile(new ZooTile(game.trZoo, params));
			zooLayer.setCell((int) params.coordinates.x,  (int) params.coordinates.y, cell);
		}
			
			
		TiledMapTileLayer itemLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		TiledMapTileLayer trainLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		TiledMapTileLayer selectedTileLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		
		//Add all the layers.
		layers.add(baseLayer);		
		for (int i = 0; i < borderLayers.length;i++){
			layers.add(borderLayers[i]);
		}		
		layers.add(trackLayer);
		layers.add(zooLayer);
		
		layers.add(itemLayer);
		layers.add(trainLayer);
		layers.add(selectedTileLayer);
		
		
		return map;
		
		
	}
	
	/**
	 * 
	 * @param mouseX Position of mouse in x direction
	 * @param mouseY Position of mouse in x direction
	 * @param camera camera object, used for calculating offsets
	 * @return returns coordinates of hexagon from any point on the screen. Use map.getTile() to gte the tile.
	 */
	public static Vector2 getCoordsFromPoint(int mouseX, int mouseY, OrthographicCamera camera){
		
		Vector3 camPos = camera.position;
		float zoom = camera.zoom;
		
		Vector2 coords = Vector2.Zero; // This is our final coordinate.
		
		int halfScreenWidth = Gdx.graphics.getWidth() / 2;		//Find middle of the screen. This is the default camera position.
		int halfScreenHeight = Gdx.graphics.getHeight() / 2;
		
		int tileSideAdjusted = (int) ( tileSide / zoom);
		int tileHeightAdjusted = (int) (tileHeight / zoom);		//Adjust the tile geometry values according to the zoom
		int tileRadiusAdjusted = (int) (tileRadius / zoom);
		
		
		//When we zoom, our effective 0,0 coordinate is moved from the bottom left corner of the screen. This is used to help with the recalculation.
		double hexSidesToXEdge = (double) halfScreenWidth / tileSide;
		double hexHeightsToYEdge = (double) halfScreenHeight / tileHeight;
		
		double zeroCoordinateX = halfScreenWidth - (hexSidesToXEdge * tileSideAdjusted); 
		double zeroCoordinateY = halfScreenHeight - (hexHeightsToYEdge * tileHeightAdjusted);


		//Adjust for camera position (there may be tiles off screen).
		int extraI = (int) ((camPos.x -  halfScreenWidth) / tileSideAdjusted);
		int extraJ = (int) ((camPos.y -  halfScreenHeight) / tileHeightAdjusted);

		//Adjust for uneven camera position (doesn't line up directly with hexagon edges).
		int extraI2 = (int) (camPos.x - halfScreenWidth) % tileSideAdjusted;	
		int extraJ2 = (int) (camPos.y - halfScreenHeight) % tileHeightAdjusted;	
		
		int adjustedMouseX = mouseX + extraI2;
		int adjustedMouseY = Gdx.graphics.getHeight() - mouseY + extraJ2;
		
		
		//x coordinate of Square box that the user clicked on in relation to screen.
		int coordI = (int)Math.floor(((float)adjustedMouseX - zeroCoordinateX)/(float)tileSideAdjusted);

	
		//x coordinate of Square box that user clicked on in relation to the world.
		int coordIAdjusted = coordI + extraI;

		//Where inside the square box user clicked in x direction
		int insideTileX = adjustedMouseX - tileSideAdjusted*coordI;

		//Use mouse position and x coordinates to work our which 
		int tempJ = adjustedMouseY - (((coordIAdjusted + 1) % 2) * tileHeightAdjusted / 2);
		int coordJ = (int)Math.floor(((float)tempJ - zeroCoordinateY)/(float)tileHeightAdjusted);
		int coordJAdjusted = coordJ + extraJ;
		int insideTileY = tempJ - tileHeightAdjusted*coordJ;

		if (insideTileX > Math.abs(tileRadiusAdjusted / 2 - tileRadiusAdjusted * insideTileY / tileHeightAdjusted)) {
			coords.x = coordI;
			coords.y = coordJ;
		} else {
			coords.x = coordI - 1;
			coords.y = (coordJ + ((coordIAdjusted + 1) % 2) - ((insideTileY < tileHeightAdjusted / 2) ? 1 : 0));

		}


		coords.x += extraI;
		coords.y += extraJ;

		return coords;

	}
	
	public MapTile getTile(int x, int y, int layerIndex){
		MapTile tile = null;
		TiledMapTileLayer t = (TiledMapTileLayer) game.map.getLayers().get(layerIndex);
		Cell c = t.getCell(x, y);
		if (c != null){
			tile = (MapTile) c.getTile();	
		}
		
		return tile;
	}
	
	public void deployTraintoTile (Vector2 coords, Train t){
		
		MapTile tile = null;
		TiledMapTileLayer trainLayer = (TiledMapTileLayer) game.map.getLayers().get(trainLayerIndex);
		Cell c = trainLayer.getCell((int) coords.x, (int) coords.y);
		
		c.setTile(tile);
		
		if (c != null){
			tile = (MapTile) c.getTile();	
		}
		
	}
	
	

}
