package com.teampik.game;

import com.badlogic.gdx.math.Vector2;

/**
 * This class makes handling zoos cleaner. It means we can still inherit from TrackTile but have extra stuff on top.
 * @author Jamie
 *
 */
public class ZooParams{
	
	Vector2 coordinates;
	String name;
	
	public ZooParams(Vector2 coords, String name){
		
		this.coordinates = coords;
		this.name = name;
	}
}
