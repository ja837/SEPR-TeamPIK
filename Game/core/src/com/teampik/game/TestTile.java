package com.teampik.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class TestTile extends StaticTiledMapTile {
	
	

	public TestTile(TextureRegion textureRegion) {
		super(textureRegion);
		// TODO Auto-generated constructor stub
		
	}
	
	
	public static Vector2 getCoordsFromPoint(int x, int y, int radius){
		
		
		final int height = (int) (((float) radius) * Math.sqrt(3));
		final int width = radius * 2;
		final int side = radius * 3 / 2;
		
		Vector2 coords = Vector2.Zero;
		
		int ci = (int)Math.floor((float)x/(float)side);
        int cx = x - side*ci;

        int ty = y - (ci % 2) * height / 2;
        int cj = (int)Math.floor((float)ty/(float)height);
        int cy = ty - height*cj;

        if (cx > Math.abs(radius / 2 - radius * cy / height)) {
            coords.x = ci;
            coords.y = cj;
        } else {
        	coords.x = ci - 1;
        	coords.y = cj + (ci % 2) - ((cy < height / 2) ? 1 : 0);
            
        }
        
        return coords;
		
	}

}
