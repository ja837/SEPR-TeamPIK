package com.teampik.game;

import java.util.ArrayList;

public class Player {
	
	ArrayList<Goal> goals = new ArrayList<Goal>();
	
	
	public void addGoal(Goal goal){
		goals.add(goal);
	}
	
	public ArrayList<Goal> getAllGoals(){
		return goals;
	}
	
	public Goal getGoal(int index){
		return goals.get(index);
	}

}
