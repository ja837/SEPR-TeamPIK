package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TestTile extends StaticTiledMapTile {
	
	public static final int radius = 32;
	public static int height; 						/*= (int) (((float) radius) * Math.sqrt(3)) This is correct mathematically, where a radius of 32 would get a height of just under 56. Since we are working with a texture, we can just take the hieght of that instead.*/
	public static final int width = radius * 2;
	public static final int side = radius * 3 / 2;
	
	

	public TestTile(TextureRegion textureRegion) {
		super(textureRegion);
		// TODO Auto-generated constructor stub
		
		height = textureRegion.getRegionHeight();
		
	}
	
	
	
	


}
