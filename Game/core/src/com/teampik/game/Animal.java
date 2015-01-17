package com.teampik.game;

//import java.util.ArrayList;

public class Animal {
	
	public enum Animals{
		PYHTON, OLIPHONT, BEAR, MONKEY, GIRAFFE, PENGUIN, HORSE, FROG
	}
	
	public Animal(){	
	}

	public static String getAnimalName(int x){
		return Animals.values()[x].toString();
	}	
}
