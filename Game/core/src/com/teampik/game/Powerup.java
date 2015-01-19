package com.teampik.game;

import com.badlogic.gdx.math.Vector2;

//This class is for the actual power-up 
public class Powerup{
	
	Vector2 coordinates;
	int type;
	
	public Powerup(Vector2 coords, int type){
		
		this.coordinates = coords;
		this.type = type;
	}
}

