package com.teampik.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Train extends MapTile {
	
	public enum trainType {
		HOVER, BULLET, ELECTRIC, DIESEL, STEAM
	}
	
	public final static int HOVER = 0;
	public final static int BULLET = 1;
	public final static int ELECTRIC = 2;
	public final static int DIESEL = 3;
	public final static int STEAM = 4;
	
	public final static int RED = 1;
	public final static int BLUE = 2;
	
	//public Tile currentTile;
	public trainType type;
	public int speed;
	Vector2 location;

	Player playerTrainBelongsTo;

	ArrayList<Vector2> locationHistory;

	// Train constructor
	public Train(TextureRegion textureRegion, trainType tType, Player p){
		super(textureRegion);
		
		
		this.playerTrainBelongsTo = p;
		this.type = tType;
		
		
		switch (tType) {
		    case STEAM:
		        speed = 4;
		    case DIESEL:
		        speed = 5;
		    case ELECTRIC:
		        speed = 6;
		    case BULLET:
		    	speed = 7;
		    case HOVER:
		    	speed = 8;
		}
	}
	
	
	//change train speed, will add duration (no of turns to change for) later
	public void setSpeed(int tspeed){
		speed = tspeed;
	}
	
	public Vector2 getLocation(){
	 	return this.location;
	 }
	
	public void setLocation(Vector2 v){
	 	location = v;
	 }
	 
	public static trainType getRandomTrain(){
		Random ran = new Random();
		int rann = ran.nextInt(5);
		trainType t = Train.trainType.values()[rann];

		return t;
	}

	 
	
}

