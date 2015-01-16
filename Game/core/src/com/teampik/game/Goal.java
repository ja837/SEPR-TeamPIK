package com.teampik.game;

import java.util.ArrayList;
import java.util.Random;

/*TODO 
	allocating to players
	players discarding
	progress updater
*/

public class Goal {
	
	private final static int basic = 0;
	private final static int easy = 1;
	private final static int medium = 2;
	private final static int hard = 3;
	
	private final static int via = 0;
	private final static int turn = 1;
	private final static int train = 2;
	
	private int turnLimit;
	
	protected static Boolean boolTrain;
	protected static Boolean boolVia;
	
	Random ranInt = new Random(); 
	
	private int diff;
	protected int goalTurnCount;
	ArrayList<Restriction> restrictions = new ArrayList<Restriction>();

	
	public Goal(int diff){
		//generateOriginTile() //randomly select a city
		//generateDestinationTile() //randomly select a city
		//name = originTile.name + " to " + destinationTile.name;
		boolTrain = false;
		boolVia = false;
		this.goalTurnCount = 0;
		
		this.diff = diff;
		this.addRestrictions(diff);
	}
	
	/*
	ZooTile originTile;
	ZooTile viaTile;   -- if on ZooTile -> train.getTile() = viaReq 
	ZooTile destinationTile;
	Train trainType;
	Train train;
	
	*/
	
	public void addRestrictions(int diff){
		int r1 = ranInt.nextInt(train+1);
		int r2 = ranInt.nextInt(train+1);
		while (r1 == r2){
			r2 = ranInt.nextInt(train+1);
		}
		
		switch (diff) {
			/*TODO
			 * pass random origin, dest and via ZooTiles to cases
			 * Train Type
			 * Animal type
			 */
			case basic:
				
				break;
			case easy:
				restrictions.add(new Restriction(r1));
				break;
			case medium:
				restrictions.add(new Restriction(r1));
				restrictions.add(new Restriction(r2));
				break;
			case hard:
				restrictions.add(new Restriction(via));
				restrictions.add(new Restriction(turn));
				restrictions.add(new Restriction(train));
				break;
				
			}
	}
	
	
	
//--------------------------------------------------
	//checking if all restrictions are complete, plus train + cargo on right tile
	/*
	protected void checkGoalCompleted(Goal goal){
		// if (train.getTile() = destination {
			//if (train.cargo = aniReq) {
				if (subGoalsComplete()){
					//Scoring.allocatePoints();
					
				}
				
			}
		}	
		else {
			return false;
		}
			
		}
	}
    */
//--------------------------------------------------
	//checking individual restrictions - will say incomplete all time
	/*
	private Boolean hasViaBeenFullfilled() {
		if (train.getTile == viaReq){
			boolVia = true;
		}
		return boolVia;
	} 
		
	
	private Boolean isRightTrain(){
		if (player.train.getType() == Train() ) {
			boolTrain = true;
		}
		return boolTrain;
	}*/
//----------------------------------------------------
	//checking train and via restrictions
	/*
	private Boolean subGoalsComplete(){
		if (boolTrain && boolVia){
			return true;
		}
		else{
			return false;
		}
	}
	*/
//-----------------------------------------------------	
	/*
	@Override
	public String toString() {
		if (viaTile = true){
			String goalName = "Send train from " + goal.originTile + " to " + goal.destinationTile + " via " + goal.viaTile;
		}
		String goalName = "Send train from " + goal.originTile + " to " + goal.destinationTile;
		return goalName;
	}
	*/

//------------------------------------------------------
	//randomly select a restriction for E,M,H goals
	protected static class Restriction{
		protected Restriction(int randomInt){
			
			switch (randomInt){
			case turn:
				//depends on tiles from zoo->zoo + amount train can move per turn + via, return int
				//# hexs from A->B(->C / lowest train movement in player.inv = limit
				break;
			case via:
				//need ZooTile somewhere - turn limit needs to take this into account, return zootile
				break;
			case train:
				//5 types ; hover bullet diesel electric steam
				//must be in player's inventory, return train type
				break;
			}
		}
		
	}



}