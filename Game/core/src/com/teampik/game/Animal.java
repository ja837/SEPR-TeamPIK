package com.teampik.game;

public class Animal {
	
	public enum Animals{
		PYHTON, BEAR, MONKEY, GIRAFFE, PENGUIN, HORSE, FROG, COCKROACH, ZEBRA, LION, TOUCAN, PANDA
	}
	
	public Animal(){	
	}

	public static String getAnimalName(int x){
		return Animals.values()[x].toString();
	}	
}
