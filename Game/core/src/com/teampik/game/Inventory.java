package com.teampik.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Inventory {

	
	
	//Trains

	ArrayList<Train> trains = new ArrayList<Train>();
	Train selectedTrain = null;
	
	public void selectTrain(int index){
		selectedTrain = trains.get(index);
	}

	public int addTrain(Train train){
		
		if (trains.size() < 3) { //If there is less than 3 Trains in the inventory add the train
			trains.add(train);
			return 1;
			}
		
		else{
			return 0;	//otherwise return 0
		}

		
	}
	
	public void deployTrain(GameMap map, Vector2 coords){
		if (selectedTrain != null){
			map.deployTraintoTile(coords,selectedTrain);
			trains.remove(selectedTrain);
		}
	}
	
	
	
	//Powerups
	
	ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	
	Powerup selectedPowerup = null;
	
	public void selectPowerup(int index){
		selectedPowerup = powerups.get(index);
	}
	
	public int addPowerup(Powerup powerup){
		if (powerups.size() < 4) { //If there is less than 4 powerups in the inventory add the powerup
			powerups.add(powerup);
			return 1;
			}
		else {
			return 0;
			}
		}

	public void deployPowerup(){
		powerups.remove(selectedPowerup);
	}

}
