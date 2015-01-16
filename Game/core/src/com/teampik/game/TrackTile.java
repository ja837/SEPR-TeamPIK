package com.teampik.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class TrackTile extends MapTile {
	
	Vector2 coords;
	
	public TrackTile(TextureRegion textureRegion, Vector2 coords) {
		
		super(textureRegion);
		
		this.coords = coords;
		
		//Need to do orientation maths here.
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Track Tile \n" + super.toString();
	}

	
	



}
