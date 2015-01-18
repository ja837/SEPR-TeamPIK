package com.teampik.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class ZooTile extends TrackTile {
	
	Vector2 coords;
	String name;
	
	public ZooTile(TextureRegion textureRegion, ZooParam params) {
		
		super(textureRegion, params.coordinates);
		
		this.coords = params.coordinates;
		this.name = params.name;
		

		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + "\n" + super.toString();
	}
	
	

	
	



}
