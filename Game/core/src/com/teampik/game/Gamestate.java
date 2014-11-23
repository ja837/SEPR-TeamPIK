package com.teampik.game;

public class Gamestate {

	final static int MAIN_MENU = 1000;
	final static int IN_GAME = 2000;
	final static int NULL = -1;
	
	private static int gameState = -1;
	
	public static int GetGamestate(){
		if (gameState == NULL){
			return MAIN_MENU;
		}
		else{
			return gameState;
		}
	}
	
	public static void MoveToGamestate(int newGameState){
		gameState = newGameState;
	}
}
