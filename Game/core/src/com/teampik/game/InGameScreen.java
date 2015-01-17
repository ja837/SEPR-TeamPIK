package com.teampik.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class InGameScreen implements Screen{
	
	Player currentPlayer;

	final static int endOfTurnProcessing = 0;
	final static int player1Turn = 1;
	final static int player2Turn = 2;

	public int currentState = endOfTurnProcessing;
	public int turnCount = 1;

	public int turnLimit = 50;



	InGameUI UI = new InGameUI();
	
	Train trainSelectedFromInventory;
	



	MyGdxGame game;

	public InGameScreen(MyGdxGame game){
		this.game = game;
		currentPlayer = game.player1;
		

		UI = new InGameUI();
		
		
		//Button listener added here so we take change state easily.
		UI.btnEndTurn.addListener(new ClickListener(){
			@Override 
			public void clicked(InputEvent event, float x, float y){
				currentState = endOfTurnProcessing;
			}
		});
		
		/*
		for (final InventoryButton b : UI.inventoryItems){
			b.addListener(new ClickListener(){
				@Override 
				public void clicked(InputEvent event, float x, float y){
					currentPlayer.inventory.selectTrain(b.index);
				}
			});
		}*/

		


		game.inputMultiplexer.addProcessor(UI.stage);

	}
	
	public void SwitchToInGameScreen(){ //Called whenever this state is entered. Effectively a reset.
		Gamestate.MoveToGamestate(Gamestate.IN_GAME);
		game.setScreen(game.inGameScreen);
		turnCount = 1;
		
		currentPlayer = game.player1;
		
		game.player1.changeName(game.mainMenuScreen.UI.tfPlayer1Name.getText()); //Assigns Names from menu text boxes to Players 
		game.player2.changeName(game.mainMenuScreen.UI.tfPlayer2Name.getText());
		
		game.player1.inventory.trains.clear();
		game.player2.inventory.trains.clear();
		
		UI.clearInventory();
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		game.batch.begin();
		game.batch.draw(game.imgInGame, 0, 0);
		//context.font.draw(context.batch, coords.toString(), 200, 200);
		game.batch.end();

		game.camera.update();
		game.tiledMapRenderer.setView(game.camera);
		game.tiledMapRenderer.render();

		
		game.batch.begin();
		UI.stage.draw();
		switch (currentState){
		case endOfTurnProcessing:			
			//End of turn processing to be done here.
			

			if (turnCount % 2 == 0){
				ProcessEndOfTurn(game.player1);
				currentState = player2Turn;
			}
			else{
				ProcessEndOfTurn(game.player2);
				currentState = player1Turn;
			}
			//EndRegion
			break;
		case player1Turn:
			//game.batch.draw(game.labelBackgroundRed,Gdx.graphics.getWidth() - 260f, Gdx.graphics.getHeight() - 20f); //Player 1 is Red

			currentPlayer = game.player1;
			
			

			break;
		case player2Turn:
			currentPlayer = game.player2;
			
			

			break;
		}
		game.batch.end();

		RefreshUI();

	}

	//Refresh the UI
	public void RefreshUI(){
		
		UI.lblPlayer.setText(currentPlayer.playerName + "'s (Player " + currentState + "'s) turn");

	}

	public void ProcessEndOfTurn(Player player){ 
		UI.clearInventory();
		System.out.println("Turn " + (turnCount - 1) + " just ended. Turn " + turnCount + " is now starting.");
		
		if (turnCount == turnLimit){			
			game.mainMenuScreen.SwitchToMainMenuScreen();
		}
		else {
			turnCount++;
			
			for (Goal g : player.getAllGoals()){
				g.goalTurnCount++;
			}
			
		}
		
		//Create and give a goal to the next player.
		Random rdm = new Random();
		int ranNumber = rdm.nextInt(4);
		Goal g = new Goal(ranNumber);
		player.addGoal(g);
		
		player.inventory.addTrain(new Train(game.trDefault, Train.trainType.values()[rdm.nextInt(4)]));
		
		for (Train t : player.inventory.trains){
			UI.addToInventory(t);
		}
		
		
		

		
		
	}


	public void selectTile(int x, int y){

		Vector3 cameraPosition = game.camera.position;


		Vector2 tileCoords = GameMap.getCoordsFromPoint(x, y, game.camera);

		TiledMapTileLayer selectedLayer = (TiledMapTileLayer) game.map.getLayers().get(GameMap.selectedTileLayerIndex);


		//Visibly select the tile.
		Cell toBeRemoved = selectedLayer.getCell((int)game.currentlySelectedTile.x, (int) game.currentlySelectedTile.y);

		if (toBeRemoved != null)
		{
			toBeRemoved.setTile(null);
		}



		Cell cell = new Cell();
		cell.setTile(new MapTile(game.trSelected));		
		selectedLayer.setCell((int) tileCoords.x,  (int) tileCoords.y, cell);

		game.currentlySelectedTile = tileCoords.cpy();



		MapTile tile = game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.baseLayerIndex);
		ZooTile ztile = (ZooTile) game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.zooLayerIndex);
		TrackTile ttile = (TrackTile) game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.trackLayerIndex);

		//Debug, outputs tile info to console.
		System.out.println("\n" + tileCoords.toString());
		System.out.println("Mouse position : " + Integer.toString(x) + ", " + Integer.toString(y));
		System.out.println("Camera position : " + cameraPosition.toString());		

		
		
		if (ztile != null){
			System.out.println(ztile.toString());			
		}
		else if (ttile != null){
			System.out.println(ttile.toString());
		}
		else{		
			//If the tile has a border then display that info, otherwise don't
			if (tile != null)
			{
				if (tile.borders[0] || tile.borders[1] || tile.borders[2] || tile.borders[3] || tile.borders[4] || tile.borders[5]){
					System.out.println(tile.toString());
				}	
			}
		}



	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub


	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
