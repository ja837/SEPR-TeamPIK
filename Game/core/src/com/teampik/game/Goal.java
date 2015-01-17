package com.teampik.game;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

import com.badlogic.gdx.math.Vector2;
import com.teampik.game.Animal.Animals;

/*TODO 
	progress updater
 */

public class Goal {

	GameMap map;
	private final static int basic = 0;
	private final static int easy = 1;
	private final static int medium = 2;
	private final static int hard = 3;

	private final static int via = 0;
	private final static int turn = 1;
	private final static int train = 2;

	/*private int turnLimit;

	protected static Boolean boolTrain;
	protected static Boolean boolVia;*/

	Random ranInt = new Random(); 

	int diff;
	int goalTurnCount;
	ArrayList<Restriction> restrictions = new ArrayList<Restriction>();

	ZooTile originTile;
	ZooTile viaTile;  
	ZooTile destinationTile;
	Train.trainType trainType;
	Train trainForGoal;
	Player p;
	String animal;


	public Goal(int diff, GameMap m, Player p){
		this.p = p;
		this.map = m;
		this.goalTurnCount = 0;
		this.diff = diff;
		

		originTile = generateOriginTile();
		destinationTile = generateDestinationTile();
		
		while( originTile == destinationTile){
			destinationTile = generateDestinationTile();
		}
		
		this.addRestrictions(diff);
		Random ranInt2 = new Random();
		int rannInt = ranInt2.nextInt(Animals.values().length);
		animal = Animal.getAnimalName(rannInt);
		System.out.println(this.toString());
	}

	public void addRestrictions(int diff){
		int r1 = ranInt.nextInt(train+1);
		int r2 = ranInt.nextInt(train+1);
		while (r1 == r2){
			r2 = ranInt.nextInt(train+1);
		}
		switch (diff) {
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

	private ZooTile generateOriginTile(){
		int r = ranInt.nextInt(map.zooList.size());
		ZooTile origin = map.zooList.get(r);
		return origin;
	}
	private ZooTile generateDestinationTile(){
		int r = ranInt.nextInt(map.zooList.size());
		ZooTile destination = map.zooList.get(r);
		return destination;
	}
	private ZooTile generateViaTile(){
		int r = ranInt.nextInt(map.zooList.size());
		viaTile = map.zooList.get(r);
		return viaTile;
	}

	//using restriction bools for completion
	public Boolean allRestrictionsComplete(){
		Boolean allComplete = false;
		for (Restriction r : restrictions){
			
			Boolean r1 = false;
			Boolean r2 = false;
			Boolean r3 = false;

			if (r.trainTypeRestriction != null){
				//train
				//train being used must be of type
				if (trainForGoal.type == r.trainTypeRestriction){
					r1 = true;
				}
			}
			else{
				r1 = true;
			}

			if (r.turnLimit != -1){
				//goalTurnCount < turnLimit
				if (goalTurnCount < r.turnLimit){
					r2 = true;
				}
			}
			else{
				r2 = true;
			}

			if (r.viaRestriciton != null){
				//via
				//use array of where train has been
				for (Vector2 v : trainForGoal.locationHistory){
					if (v == viaTile.coords){
						r3= true;
					}
					else{
						r3 = true;
					}


					if (r1 && r2 && r3){
						allComplete = true;
					}
				}
			}
		}
		return allComplete;
	}



		@Override
		public String toString() {
			String goalName = "Deliver a " + animal + " to " + destinationTile.name + " from " + originTile.name;
			
			for (Restriction r : restrictions){
				if (r.viaRestriciton != null){
					goalName += " via " + r.viaRestriciton.name;
				}
				if (r.trainTypeRestriction != null){
					goalName += " with train " + r.trainTypeRestriction.toString();
				}		
			
				//turn limit
				if (r.turnLimit != -1){
					goalName += " within " + r.turnLimit + " turns";
				}
			}
			return goalName;
			
		}

		//randomly select a restriction for E,M,H goals
		protected class Restriction{

			Train.trainType trainTypeRestriction = null;
			int turnLimit = -1;
			ZooTile viaRestriciton = null;

			Boolean trainTypeCompleted = false;
			Boolean turnLimitCompleted = false;
			Boolean viaCompleted = false;

			protected Restriction(int randomInt){
				switch (randomInt){
				case turn:
					float xDistance, yDistance, cDistance, x1Distance, y1Distance;
					int tempSpeed = 10;
					//Vector2 distance;

					//depends on tiles from zoo->zoo + amount train can move per turn + via, return int
					//# hexs from A->B(->C / lowest train movement in player.inv = limit

					//find distance between o+d / o+v+d
					if (viaRestriciton != null){
						//iteration 1 of finding best way to do turn limit
						//xDistance
						x1Distance = Math.abs(originTile.coords.x - viaTile.coords.x);
						xDistance = Math.abs(x1Distance + destinationTile.coords.x);
						//yDistance;
						y1Distance = Math.abs(originTile.coords.y - viaTile.coords.y);
						yDistance = Math.abs(y1Distance + destinationTile.coords.y);
					}
					else{
						xDistance = Math.abs(originTile.coords.x - destinationTile.coords.x);
						yDistance = Math.abs(originTile.coords.y - destinationTile.coords.y);
					}				
					cDistance = (float) Math.sqrt((Math.pow(xDistance, 2)) + (Math.pow(yDistance, 2))) + 15f;

					//finding lowest moving train in player inv
					//tempSpeed holds lowest value
					if (trainTypeRestriction != null){
						tempSpeed = trainForGoal.speed;
					}
					else {
						for (Train t : p.inventory.trains){
							if (t.speed < tempSpeed){
								tempSpeed = t.speed;
							}
						}
					}
					//do distance / t.speed = limit
					turnLimit = ((int) cDistance / tempSpeed) + 2;
					break;

				case via:
					viaRestriciton = generateViaTile();
					while (viaRestriciton == originTile){
						while (viaRestriciton == destinationTile){
							viaRestriciton = generateViaTile();
						}
					}
					break;

				case train:
					//5 types ; hover bullet diesel electric steam
					//must be in player's inventory, return train type
					Boolean hasTrain = false;
					while (!hasTrain){
						trainType = Train.getRandomTrain();
						for (Train t : p.inventory.trains){
							if (t.type == trainType){
								hasTrain = true;
								trainForGoal = t;
							}
						}
					}

					trainTypeRestriction = trainType;
					break;
				}
			}
		}



	}