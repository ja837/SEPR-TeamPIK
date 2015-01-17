package com.teampik.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Train extends MapTile {
	
	public enum trainType {
		HOVER, BULLET, ELECTRIC, DIESEL, STEAM
	}
	
	//public Tile currentTile;
	public trainType type;
	public int speed;
	Vector2 location;
	Player playerTrainBelongsTo;
	
	// Train constructor
	public Train(TextureRegion textureRegion, trainType tType){
		super(textureRegion);
		
		this.type = tType;
		switch (tType) {
		    case STEAM:
		        speed = 2;
		    case DIESEL:
		        speed = 4;
		    case ELECTRIC:
		        speed = 5;
		    case BULLET:
		    	speed = 6;
		    case HOVER:
		    	speed = 8;
		}
	}
	
	public void setPlayer(Player p){
		playerTrainBelongsTo = p;
	}
	
	//change train speed, will add duration (no of turns to change for) later
	public void setSpeed(int tspeed){
		speed = tspeed;
	}
	
	
	public Vector2 getLocation(){
	 	return this.location;
	 }
	 
	
	/*
	 
	 */
}

