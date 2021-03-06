package com.teampik.game;

import java.util.ArrayList;
import java.util.Random;

public class Player {
	
	GameMap map;
	public String playerName = "default";
	int playerNumber;
    
	Inventory inventory;
	
    ArrayList<Goal> goals = new ArrayList<Goal>();
    
    public Player(int number){
    	inventory  = new Inventory();
    	playerNumber  = number;
    }
	
	
	public void addGoal(Goal goal){
		//0 for basic; 1 for easy; 2 for med; 3 for hard
		Random ranInt = new Random();
		int r1 = ranInt.nextInt(4);
		if (goals.size() < 3){
			goals.add(goal);
			
		}
	}


	public ArrayList<Goal> getAllGoals(){
		return goals;
	}
	
	public Goal getGoal(int index){
		return goals.get(index);
	}

	public void changeName(String newName){
		playerName = newName;
	}
	
	public ArrayList<Goal> discardGoal(Goal g){
		goals.remove(g);
		return goals;
	}
}
