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

		game.inputMultiplexer.addProcessor(UI.stage);

	}
	
	public void SwitchToInGameScreen(){ //Called whenever this state is entered. Effectively a reset.
		Gamestate.MoveToGamestate(Gamestate.IN_GAME);
		game.setScreen(game.inGameScreen);
		turnCount = 1;
		
		currentPlayer = game.player1;
		currentState = endOfTurnProcessing;
		
		game.player1.changeName(game.mainMenuScreen.UI.tfPlayer1Name.getText()); //Assigns Names from menu text boxes to Players 
		game.player2.changeName(game.mainMenuScreen.UI.tfPlayer2Name.getText());
		
		game.player1.inventory.trains.clear();
		game.player2.inventory.trains.clear();
		game.player1.goals.clear();
		game.player2.goals.clear();
		
		//Clear all trains off map
		for (Train t : game.map.deployedTrains){
			TiledMapTileLayer trainLayer = (TiledMapTileLayer) game.map.getLayers().get(GameMap.trainLayerIndex);

			//Visibly select the tile.
			Cell toBeRemoved = trainLayer.getCell((int)t.location.x, (int) t.location.y);

			if (toBeRemoved != null)
			{
				toBeRemoved.setTile(null);
			}
		}
		
		game.map.deployedTrains.clear();
		
		
		RefreshInventory();
		RefreshGoals();
		
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
				ProcessEndOfTurn(game.player2);
				currentState = player2Turn;
			}
			else{
				ProcessEndOfTurn(game.player1);
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
		//UI.clearInventory();

		UI.clearGoal();
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
		int randomTrainInt = rdm.nextInt(5);
		
		player.inventory.addTrain(new Train(game.trTrains[randomTrainInt][player.playerNumber], Train.trainType.values()[randomTrainInt], player));

		RefreshInventory();
		/*
		for (Train t : player.inventory.trains){
			UI.addToInventory(player, t);
		}*/
		
		int ranNumber = rdm.nextInt(4);
		Goal g = new Goal(ranNumber, game.map, player);
		player.addGoal(g);
		
		for (Goal goal : player.goals){
			UI.addToGoals(player, goal);
			System.out.println(goal.toString());
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


		Train train = (Train) game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.trainLayerIndex);
		MapTile tile = game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.baseLayerIndex);
		ZooTile ztile = (ZooTile) game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.zooLayerIndex);
		TrackTile ttile = (TrackTile) game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.trackLayerIndex[Direction.MIDDLE]);

		//Debug, outputs tile info to console.
		System.out.println("\n" + tileCoords.toString());
		System.out.println("Mouse position : " + Integer.toString(x) + ", " + Integer.toString(y));
		System.out.println("Camera position : " + cameraPosition.toString());		

		
		//Since there is lots of things on the same tile, checking what has been clicked on is cascaded down. Train then zoo then track then maptile.
		if (train != null){
			//Select train and move it when new tile is selected.
		}
		else if (ztile != null){
			System.out.println(ztile.toString());	
			
			//Deploy Train if it has been selected
			if (currentPlayer.inventory.selectedTrain != null){
				
				game.map.deployTraintoTile(tileCoords, currentPlayer.inventory.selectedTrain);
				currentPlayer.inventory.trains.remove(currentPlayer.inventory.selectedTrain);
				
				
				game.map.deployedTrains.add(currentPlayer.inventory.selectedTrain);
				currentPlayer.inventory.selectedTrain.setLocation(tileCoords);
				
				currentPlayer.inventory.selectedTrain = null;
				
				RefreshInventory();
				RefreshGoals();
				System.out.println("Train deployed");	
			}
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
	
	public void RefreshInventory(){
		UI.clearInventory();
		if (currentPlayer.inventory.trains.size() > 0){
			UI.btnTrain0 = new InventoryTrainButton(currentPlayer.inventory.trains.get(0).type.toString(), UI.skin, 0, currentPlayer.inventory);
			System.out.println("inventory refreshed > 0");
			System.out.println(currentPlayer.inventory.trains.get(0).type.toString());
		}
		if (currentPlayer.inventory.trains.size() > 1){
			UI.btnTrain1 = new InventoryTrainButton(currentPlayer.inventory.trains.get(1).type.toString(), UI.skin, 1, currentPlayer.inventory);
			System.out.println("inventory refreshed > 1");
			System.out.println(currentPlayer.inventory.trains.get(1).type.toString());
		}
		if (currentPlayer.inventory.trains.size() > 2){
			UI.btnTrain2 = new InventoryTrainButton(currentPlayer.inventory.trains.get(2).type.toString(), UI.skin, 2, currentPlayer.inventory);
			System.out.println("inventory refreshed > 2");
			System.out.println(currentPlayer.inventory.trains.get(2).type.toString());
		}
		
			/*
		for (Train t : currentPlayer.inventory.trains){
			UI.addToInventory(currentPlayer, t);
			UI.btnTrain1.setText(t.type.toString());
		}*/
	}
	
	public void RefreshGoals(){
		UI.clearGoal();
		for (Goal goal : currentPlayer.goals){
			UI.addToGoals(currentPlayer, goal);
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
