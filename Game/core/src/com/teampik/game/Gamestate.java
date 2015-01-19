package com.teampik.game;

public class Gamestate {
	
	final static int LOADING = 500;
	final static int MAIN_MENU = 1000;
	final static int IN_GAME = 2000;
	final static int INSTRUCTIONS = 3000;
	final static int NULL = -1;
	
	private static int gameState = NULL;
	
	public static int GetGamestate(){
		if (gameState == NULL){
			return NULL;
		}
		else{
			return gameState;
		}
	}
	
	public static void MoveToGamestate(int newGameState){
		gameState = newGameState;
	}
}
