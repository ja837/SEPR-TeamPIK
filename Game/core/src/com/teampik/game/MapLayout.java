package com.teampik.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class MapLayout {
	
	
	
	public final static int WATER = 1000;
	public final static int LAND = 2000;
	public final static int SNOW = 3000;
	public final static int MOUNTAIN = 4000;
	public final static int FOREST = 5000;
	public final static int DESERT = 6000;
	public final static int TRACK = 7000;
	public final static int ZOO  = 8000;
	
	
	ArrayList<Vector2>[] borders;
	ArrayList<Vector2> trackCoords;
	ArrayList<Vector2> zooCoords;
	int[][] baseTileLayout;
	
	MapTile[][] tiles;
	
	int tilesX;
	int tilesY;
	
	//This constructor takes a layout and border list and converts them into tiles for use when creating the map.
	public MapLayout(MyGdxGame game, int[][] layout, ArrayList<Vector2>[] listOfCoordsWithBorders, ArrayList<Vector2> trackCoords, ArrayList<Vector2> zooCoords, int tilesX, int tilesY)
	{
		this.borders = listOfCoordsWithBorders;
		this.trackCoords = trackCoords;
		this.zooCoords = zooCoords;
		this.baseTileLayout = layout;
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		
		
		//Have to rotate layout because arrays are indexed differently to libgdx's maps.
		layout = rotateCW(layout);
		
		
		//2d array of maptiles
		tiles = new MapTile[layout.length][];
		
		//initialise maptile array
		for (int i =0;i < layout.length; i++){
			tiles[i] = new MapTile[layout[i].length];
		}
		
		//for each value in the layout array, put a tile in the tile array.
		for (int i = 0; i < layout.length;i++){
			for (int j = 0; j < layout[i].length; j++)
			{
				
				
				switch (layout[i][j]){												
				case WATER:
					tiles[i][j] = new MapTile(game.trWater);
					break;
				case LAND:
					tiles[i][j] = new MapTile(game.trLand);
					break;
				case SNOW:
					tiles[i][j] = new MapTile(game.trSnow);
					break;
				case MOUNTAIN:
					tiles[i][j] = new MapTile(game.trMountain);
					break;	
				case DESERT:
					tiles[i][j] = new MapTile(game.trDesert);
					break;	
				case FOREST:
					tiles[i][j] = new MapTile(game.trForest);
					break;	
				default:
					tiles[i][j] = new MapTile(game.trDefault);
					break;
				}
				
				
			}
		}
		
		
		//This is obviously inefficient (looping through twice). Will sort out later.
		for (int i = 0; i < layout.length;i++){
			for (int j = 0; j < layout[i].length; j++)
			{
				//Add borders to MapTile instance. Tell the tiles that they have borders.
				for (int direction = Direction.NORTH; direction <= Direction.NORTH_WEST; direction++){
					for (Vector2 v : listOfCoordsWithBorders[direction]){
						if ((int)v.x == i && (int)v.y == j){
							tiles[(int)v.x][(int)v.y].borders[direction] = true;
						}
					}
				}
			}
		}
		
		
	}
	
	private static int[][] rotateCW(int[][] mat) {
	    final int M = mat.length;
	    final int N = mat[0].length;
	    int[][] ret = new int[N][M];
	    for (int r = 0; r < M; r++) {
	        for (int c = 0; c < N; c++) {
	            ret[c][M-1-r] = mat[r][c];
	        }
	    }
	    return ret;
	}
	

}
