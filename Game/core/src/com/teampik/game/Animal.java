package com.teampik.game;

import java.util.ArrayList;

public class Animal {
	
	ArrayList<String> animals = new ArrayList<String>();
	
	public Animal(){
		animals.add("Python Snake");
		animals.add("African Oliphont");
		animals.add("Panda Bear");
		animals.add("Squirrel Monkey");
		animals.add("Rothschild Giraffe");
		animals.add("Emperor Penguin");
		animals.add("Arabian Horse");
		animals.add("Mountain Chicken Frog");
		
	}
	
	
	public String getAnimalName(int x){
		return animals.get(x);
	}
}
