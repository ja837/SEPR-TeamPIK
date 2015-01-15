package com.teampik.game;

public class Train {
	
	public enum trainType {
		HOVER, BULLET, ELECTRIC, DIESEL, STEAM
	}
	
	public trainType type;
	public int speed;
	
	// Train constructor
	public Train(trainType tType){
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
	
	//change train speed, will add duration (no of turns to change for) later
	public void setSpeed(int tspeed){
		speed = tspeed;
	}
}

