package com.teampik.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class MapTile extends StaticTiledMapTile {
	
	Boolean[] borders = new Boolean[]{false, false, false, false, false, false};
	

	public MapTile(TextureRegion textureRegion) {
		super(textureRegion);
		// TODO Auto-generated constructor stub
		

		
	}


	@Override
	public String toString() {
		
		return "MapTile with borders:"
				+ "\nNorth - " + borders[Direction.NORTH] 
				+ "\nNorth East - " + borders[Direction.NORTH_EAST] 
				+ "\nSouth East - " + borders[Direction.SOUTH_EAST] 
				+ "\nSouth - " + borders[Direction.SOUTH] 
				+ "\nSouth West - " + borders[Direction.SOUTH_WEST] 
				+ "\nNorth West - " + borders[Direction.NORTH_WEST]; 
						

	}
	
	
	
	//Does nothing different atm.


}
