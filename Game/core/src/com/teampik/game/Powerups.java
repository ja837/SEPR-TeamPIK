package com.teampik.game;

import java.util.Random;
import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

public class Powerups{
	static ArrayList<Powerup> powerlist = new ArrayList<Powerup>();	//List of available power-ups

	public static void createPowerup(int type, int owner, MapLayout m) {	
				
		//Type: Random = 0; Speed = 1; Banana = 2; Bomb = 3; Forcefield = 4; Border Bribery = 5; Pet = 6				
		//Owner: 0 = No-one (not collected yet), 1 = P1, 2 = P2 
		Random rand = new Random();		
		if (type == 0){
			type = rand.nextInt(6) + 1;	
		}		
	
		int location = rand.nextInt(m.trackCoords.size());	//picks a random piece of track to spawn a powerup on
		Vector2 n = (m.trackCoords.get(location));	
		
		powerlist.add(new Powerup(n,type,0));		
						
		/*  	//render the power-up
		switch (type){	
			case 1:
			draw speed boost
		}*/
	}
	public static void removePowerup(int num){	//Num is the index of the powerup to remove; e.g. 0 will remove first powerup
		powerlist.remove(num);	
	}
		
}