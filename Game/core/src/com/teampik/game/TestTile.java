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
	
	
	public static Vector2 getCoordsFromPoint(int mouseX, int mouseY, Vector3 camPos){
		
		int adjustedMouseY = Gdx.graphics.getHeight() - mouseY;
		
		
		
		
		Vector2 coords = Vector2.Zero;
		
		//Adjust for camera position.
		int extraI2 = (int) (camPos.x - 640f) % side;	//These will be used for adjusting for camera postions not exactly lining up with hexagons
		int extraJ2 = (int) (camPos.y - 360f) % height;
		
		int coordI = (int)Math.floor(((float)mouseX)/(float)side);
		
		//Adjust for camera position.
		int extraI = (int) ((camPos.x - 640f) / side);
		int extraJ = (int) ((camPos.y - 360f) / height);
		
		
		
		int coordIAdjusted = coordI + extraI;
		
		
        int insideTileX = mouseX - side*coordI;

        int tempJ = adjustedMouseY - (((coordIAdjusted + 1) % 2) * height / 2);
        int coordJ = (int)Math.floor((float)tempJ/(float)height);
        int coordJAdjusted = coordJ + extraJ;
        int insideTileY = tempJ - height*coordJ;

        if (insideTileX > Math.abs(radius / 2 - radius * insideTileY / height)) {
            coords.x = coordI;
            coords.y = coordJ;
        } else {
        	coords.x = coordI - 1;
        	coords.y = (coordJ + ((coordIAdjusted/* + extraJ*/) % 2) - ((insideTileY < height / 2) ? 1 : 0));
            
        }
                
        
        coords.x += extraI;
        coords.y += extraJ;
        
        return coords;
		
	}
	


}
