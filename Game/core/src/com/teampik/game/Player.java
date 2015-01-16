package com.teampik.game;

import java.util.ArrayList;

public class Player {
	
	public String playerName = "default";
    
	Inventory inventory;
	
    ArrayList<Goal> goals = new ArrayList<Goal>();
	
	
	public void addGoal(Goal goal){
		//0 for basic; 1 for easy; 2 for med; 3 for hard
		if (goals.size() < 3){
			goals.add(new Goal(1));
			
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
