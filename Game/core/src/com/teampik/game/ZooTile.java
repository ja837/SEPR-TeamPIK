package com.teampik.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class ZooTile extends MapTile {
	
	Vector2 coords;
	
	public ZooTile(TextureRegion textureRegion, Vector2 coords) {
		
		super(textureRegion);
		
		this.coords = coords;
		

		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ZooTile \n" + super.toString();
	}

	
	



}
