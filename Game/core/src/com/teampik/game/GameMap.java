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
	final static int trackLayerIndex[] = {7,8,9,10,11,12,13};
	final static int zooLayerIndex = 14;
	final static int itemLayerIndex = 15;
	final static int trainLayerIndex = 16;
	final static int selectedTileLayerIndex = 17;
	
	
	MyGdxGame game;
	MapLayout mapLayout;
	
	ArrayList<ZooTile> zooList;
	ArrayList<Train> deployedTrains;
	
	public static int tileRadius = 32;
	public static int tileHeight  = 56; 						/*= (int) (((float) radius) * Math.sqrt(3)) This is correct mathematically, where a radius of 32 would get a height of just under 56. Since we are working with a texture, we can just take the hieght of that instead.*/
	public static int tileWidth  = tileRadius * 2;
	public static int tileSide= tileRadius * 3 / 2;
	
	
	public GameMap(MyGdxGame g, MapLayout mapLayout){		
		game = g;
		this.mapLayout = mapLayout;
		zooList = new ArrayList<ZooTile>();
		
		deployedTrains = new ArrayList<Train>();
	}
	
	public static GameMap createMap(MyGdxGame game, MapLayout mapLayout) {
		
		GameMap map = new GameMap(game, mapLayout);
		MapLayers layers = map.getLayers();

		TiledMapTileLayer itemLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		
		
		TiledMapTileLayer baseLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight); //Number of tiles in x direction, Number of tiles in y direction, pixel width of tile, pixel height of tile
		TiledMapTileLayer[] borderLayers = new TiledMapTileLayer[6]; // One for each direction			
		
		for (int i = 0; i < borderLayers.length;i++){
			borderLayers[i] = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		}
		
		TiledMapTileLayer[] trackLayers = new TiledMapTileLayer[7];
		
		for (int i = 0; i < trackLayers.length;i++){
			trackLayers[i] = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		}
		
		
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
			trackLayers[Direction.MIDDLE].setCell((int) coordinate.x,  (int) coordinate.y, cell);
			
		}

		for (ZooParam params : mapLayout.zooParams){
			Cell cell = new Cell();
			ZooTile z = new ZooTile(game.trZoo, params);
			cell.setTile(z);
			zooLayer.setCell((int) params.coordinates.x,  (int) params.coordinates.y, cell);

			map.zooList.add(z);
		}
		
		for (Vector2 coordinate: mapLayout.trackCoords){
			for (int i=0; i < mapLayout.trackCoords.size(); i++) {
				Vector2 next = mapLayout.trackCoords.get(i);
				if (next.x == coordinate.x){
					//if north tracktiles
					if (next.y == coordinate.y+1){
						Cell cell = new Cell();
						cell.setTile(new TrackTile(game.trTrackN, coordinate));
						trackLayers[Direction.NORTH].setCell((int) coordinate.x, (int) coordinate.y, cell);
					}
					//if south tracktiles
					if (next.y == coordinate.y-1){
						Cell cell = new Cell();
						cell.setTile(new TrackTile(game.trTrackS, coordinate));
						trackLayers[Direction.SOUTH].setCell((int) coordinate.x, (int) coordinate.y, cell);
					}
				}
				if (next.x == coordinate.x+1){
					// if northeast tracktiles
					if (((coordinate.x % 2 == 0) && (next.y == coordinate.y+1)) 
							|| ((coordinate.x % 2 != 0) && (next.y == coordinate.y))){
						Cell cell = new Cell();
						cell.setTile(new TrackTile(game.trTrackNE, coordinate));
						trackLayers[Direction.NORTH_EAST].setCell((int) coordinate.x, (int) coordinate.y, cell);
					}
					//if southeast tracktiles
					if (((coordinate.x % 2 == 0) && (next.y == coordinate.y)) 
							|| ((coordinate.x % 2 != 0) && (next.y == coordinate.y-1))){
						Cell cell = new Cell();
						cell.setTile(new TrackTile(game.trTrackSE, coordinate));
						trackLayers[Direction.SOUTH_EAST].setCell((int) coordinate.x, (int) coordinate.y, cell);
					}
				}
				if (next.x == coordinate.x-1){
					// if northwest tracktiles
					if (((coordinate.x % 2 == 0) && (next.y == coordinate.y+1)) 
							|| ((coordinate.x % 2 != 0) && (next.y == coordinate.y))){
						Cell cell = new Cell();
						cell.setTile(new TrackTile(game.trTrackNW, coordinate));
						trackLayers[Direction.NORTH_WEST].setCell((int) coordinate.x, (int) coordinate.y, cell);
					}
					//if southwest tracktiles
					if (((coordinate.x % 2 == 0) && (next.y == coordinate.y)) 
							|| ((coordinate.x % 2 != 0) && (next.y == coordinate.y-1))){
						Cell cell = new Cell();
						cell.setTile(new TrackTile(game.trTrackSW, coordinate));
						trackLayers[Direction.SOUTH_WEST].setCell((int) coordinate.x, (int) coordinate.y, cell);
					}
				}
			}
		}
		
		//remove single 1 hex width track pieces
		for (int i = 0; i < mapLayout.tiles.length; i++){
			for (int j = 0; j < mapLayout.tiles[i].length; j++){
				if((trackLayers[Direction.NORTH_EAST].getCell(i, j) != null) && (trackLayers[Direction.SOUTH_EAST].getCell(i, j) != null)){
					if (i%2 == 0 ){
						if ((trackLayers[Direction.NORTH].getCell(i+1, j) != null) && (trackLayers[Direction.SOUTH].getCell(i+1, j+1) != null)){
							if ((trackLayers[Direction.SOUTH].getCell(i+1, j) == null) && ( trackLayers[Direction.NORTH].getCell(i+1, j+1) == null)){
								trackLayers[Direction.NORTH].setCell(i+1, j, null);
								trackLayers[Direction.SOUTH].setCell(i+1, j+1, null);
							} else if ((trackLayers[Direction.SOUTH_WEST].getCell(i, j) == null) && ( trackLayers[Direction.NORTH_EAST].getCell(i+1, j+1) == null)){
								trackLayers[Direction.NORTH_EAST].setCell(i, j, null);
								trackLayers[Direction.SOUTH_WEST].setCell(i+1, j+1, null);
							} else if ((trackLayers[Direction.NORTH_WEST].getCell(i, j) == null) && ( trackLayers[Direction.SOUTH_EAST].getCell(i+1, j) == null)){
								trackLayers[Direction.SOUTH_EAST].setCell(i, j, null);
								trackLayers[Direction.NORTH_WEST].setCell(i+1, j, null);
							}
						}
					} else {
						if ((trackLayers[Direction.NORTH].getCell(i+1, j-1) != null) && (trackLayers[Direction.SOUTH].getCell(i+1, j) != null)){
							if ((trackLayers[Direction.SOUTH].getCell(i+1, j-1) == null) && ( trackLayers[Direction.NORTH].getCell(i+1, j) == null)){
								trackLayers[Direction.NORTH].setCell(i+1, j-1, null);
								trackLayers[Direction.SOUTH].setCell(i+1, j, null);
							} else if ((trackLayers[Direction.SOUTH_WEST].getCell(i, j) == null) && ( trackLayers[Direction.NORTH_EAST].getCell(i+1, j) == null)){
								trackLayers[Direction.NORTH_EAST].setCell(i, j, null);
								trackLayers[Direction.SOUTH_WEST].setCell(i+1, j, null);
							} else if ((trackLayers[Direction.NORTH_WEST].getCell(i, j-1) == null) && ( trackLayers[Direction.SOUTH_EAST].getCell(i+1, j-1) == null)){
								trackLayers[Direction.SOUTH_EAST].setCell(i, j, null);
								trackLayers[Direction.NORTH_WEST].setCell(i+1, j-1, null);
							}
						}
					}
				}
				if((trackLayers[Direction.NORTH_WEST].getCell(i, j) != null) && (trackLayers[Direction.SOUTH_WEST].getCell(i, j) != null)){
					if (i%2 == 0 ){
						if ((trackLayers[Direction.NORTH].getCell(i-1, j) != null) && (trackLayers[Direction.SOUTH].getCell(i-1, j+1) != null)){
							if ((trackLayers[Direction.SOUTH].getCell(i-1, j) == null) && ( trackLayers[Direction.NORTH].getCell(i-1, j+1) == null)){
								trackLayers[Direction.NORTH].setCell(i-1, j, null);
								trackLayers[Direction.SOUTH].setCell(i-1, j+1, null);
							} else if ((trackLayers[Direction.SOUTH_WEST].getCell(i-1, j) == null) && ( trackLayers[Direction.NORTH_EAST].getCell(i, j) == null)){
								trackLayers[Direction.NORTH_EAST].setCell(i-1, j, null);
								trackLayers[Direction.SOUTH_WEST].setCell(i, j, null);
							} else if ((trackLayers[Direction.NORTH_WEST].getCell(i-1, j+1) == null) && ( trackLayers[Direction.SOUTH_EAST].getCell(i, j) == null)){
								trackLayers[Direction.SOUTH_EAST].setCell(i-1, j+1, null);
								trackLayers[Direction.NORTH_WEST].setCell(i, j, null);
							}
						}
					} else {
						if ((trackLayers[Direction.NORTH].getCell(i-1, j-1) != null) && (trackLayers[Direction.SOUTH].getCell(i-1, j) != null)){
							if ((trackLayers[Direction.SOUTH].getCell(i-1, j-1) == null) && ( trackLayers[Direction.NORTH].getCell(i-1, j) == null)){
								trackLayers[Direction.NORTH].setCell(i-1, j-1, null);
								trackLayers[Direction.SOUTH].setCell(i-1, j, null);
							} else if ((trackLayers[Direction.SOUTH_WEST].getCell(i-1, j-1) == null) && ( trackLayers[Direction.NORTH_EAST].getCell(i, j) == null)){
								trackLayers[Direction.NORTH_EAST].setCell(i-1, j-1, null);
								trackLayers[Direction.SOUTH_WEST].setCell(i, j, null);
							} else if ((trackLayers[Direction.NORTH_WEST].getCell(i-1, j) == null) && ( trackLayers[Direction.SOUTH_EAST].getCell(i, j) == null)){
								trackLayers[Direction.SOUTH_EAST].setCell(i-1, j, null);
								trackLayers[Direction.NORTH_WEST].setCell(i, j, null);
							}
						}
					}
				}
			}	
		}
		
			
		for (Powerups params : mapLayout.powerups){
			Cell cell = new Cell();
			cell.setTile(new itemtile(game.trBomb, params));
			itemLayer.setCell((int) params.coordinates.x,  (int) params.coordinates.y, cell);
		}		
		
		TiledMapTileLayer trainLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		TiledMapTileLayer selectedTileLayer = new TiledMapTileLayer( mapLayout.tilesX, mapLayout.tilesY, tileWidth, tileHeight);
		
		//Add all the layers.
		layers.add(baseLayer);		
		for (int i = 0; i < borderLayers.length;i++){
			layers.add(borderLayers[i]);
		}	
		for (int i = 0; i < trackLayers.length;i++){
			layers.add(trackLayers[i]);
		}
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
		double hexSidesToXEdge = (double) camPos.x / tileSide;
		double hexHeightsToYEdge = (double) camPos.y / tileHeight;
		
		double zeroCoordinateX = camPos.x - (hexSidesToXEdge * tileSideAdjusted); 
		double zeroCoordinateY = camPos.y - (hexHeightsToYEdge * tileHeightAdjusted);


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
		
		
		TiledMapTileLayer trainLayer = (TiledMapTileLayer) game.map.getLayers().get(trainLayerIndex);
		Cell c = new Cell();
		
		c.setTile(t);
		
		trainLayer.setCell((int) coords.x, (int) coords.y, c);
		
	}
	
	public Train getTrainFromLocation(Vector2 v){
		Train t = (Train) getTile((int) v.x, (int) v.y, trainLayerIndex);
		return t;
	}

}

