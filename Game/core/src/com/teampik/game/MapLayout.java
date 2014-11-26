package com.teampik.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class MapLayout {
	
	
	
	public final static int WATER = 1000;
	public final static int LAND = 2000;
	public final static int TRACK = 3000;
	public final static int ZOO  = 4000;
	
	
	ArrayList<Vector2>[] borders;
	int[][] baseTileLayout;
	
	MapTile[][] tiles;
	
	int tilesX;
	int tilesY;
	
	//This constructor takes a layout and border list and converts them into tiles for use when creating the map.
	public MapLayout(MyGdxGame game, int[][] layout, ArrayList<Vector2>[] listOfCoordsWithBorders, int tilesX, int tilesY)
	{
		this.borders = listOfCoordsWithBorders;
		this.baseTileLayout = layout;
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		
		tiles = new MapTile[layout.length][];
		
		for (int i =0;i < layout.length; i++){
			tiles[i] = new MapTile[layout[i].length];
		}
		
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
				case TRACK:
					tiles[i][j] = new MapTile(game.trTrack);
					break;
				case ZOO:
					tiles[i][j] = new MapTile(game.trZoo);
					break;
				default:
					tiles[i][j] = new MapTile(game.trDefault);
					break;
				}
				
				
			}
		}
		
		for (int i = 0; i < layout.length;i++){
			for (int j = 0; j < layout[i].length; j++)
			{
				//Add borders to MapTile instance
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
	

}
