package com.teampik.game;

import java.util.ArrayList;

public class Inventory {
	//Trains
	ArrayList<Train> trains = new ArrayList<Train>();
	Train selectedTrain;
	
	public void selectTrain(int index){
		selectedTrain = trains.get(index);
	}

	public int addTrain(Train train){
		
		if (trains.size() < 4) { //If there is less than 3 Trains in the inventory add the train
			trains.add(train);
			return 1;
			}
		
		else{
			return 0;	//otherwise return 0
		}
		
	}
	
	public void deployTrain(Train train, MapTile zoo){
		if (true){//INCOMPLETE CHANGE TO CHECK TILE IS ZOOTILE
			
		}
	}
	//Powerups
}
