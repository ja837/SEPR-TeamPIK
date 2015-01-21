package com.teampik.game;

import java.util.Random;
import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InGameScreen implements Screen{
	
	Player currentPlayer;

	final static int endOfTurnProcessing = 0;
	final static int player1Turn = 1;
	final static int player2Turn = 2;

	public int currentState = player1Turn;
	public int turnCount = 0;
	public int turnLimit = 20;	
	
	//Location of the player's trains
	public ArrayList<Vector2> p1Trains = new ArrayList<Vector2>();
	public ArrayList<Vector2> p2Trains = new ArrayList<Vector2>();	
	
	//Store the actual train objects used
	public ArrayList<Train> p1TrainsObjects = new ArrayList<Train>();
	public ArrayList<Train> p2TrainsObjects = new ArrayList<Train>();
	public ArrayList<Train> allTrains = new ArrayList<Train>();
	
	//maximum distance which can be moved each turn
	public ArrayList<Integer> p1TrainsMaxMovement = new ArrayList<Integer>();
	public ArrayList<Integer> p2TrainsMaxMovement = new ArrayList<Integer>();
	
	//distance which can be moved this turn; resets at the end of each go
	public ArrayList<Integer> p1TrainsMovement = new ArrayList<Integer>();
	public ArrayList<Integer> p2TrainsMovement = new ArrayList<Integer>();
	
	public ArrayList<Integer> p1Powerups = new ArrayList<Integer>();
	public ArrayList<Integer> p2Powerups = new ArrayList<Integer>(); 	
	
	public Vector2 playerTrain = new Vector2();
	public Vector2 selectTrain = new Vector2();
	public Vector2 searchTrain = new Vector2();
	
	public int mode = 0;
	public int whichTrain;
	
	boolean found = false;

	InGameUI UI = new InGameUI();	
	
	MyGdxGame game;

	public InGameScreen(MyGdxGame game){
		this.game = game;
		currentPlayer = game.player1;
		currentState = endOfTurnProcessing;
		currentState = player1Turn;
		
		UI = new InGameUI();
		
		//Button listener added here so we take change state easily.
		UI.btnEndTurn.addListener(new ClickListener(){
			@Override 
			public void clicked(InputEvent event, float x, float y){
				currentState = endOfTurnProcessing;
			}
		});
	}
	
	public void SwitchToInGameScreen(){ //Called whenever this state is entered. Effectively a reset.		
		
		turnCount = 1;		
		currentPlayer = game.player1;
		currentState = player1Turn;
		
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
		ProcessEndOfTurn(currentPlayer);		
		
		RefreshInventory();
		RefreshGoals();		
		
		Gamestate.MoveToGamestate(Gamestate.IN_GAME);
		game.setScreen(game.inGameScreen);
		game.inputMultiplexer.addProcessor(UI.stage);
		game.inputMultiplexer.removeProcessor(game.mainMenuScreen.UI.stage);
		game.inputMultiplexer.removeProcessor(game.instructionScreen.UI.stage);		
	}

	@Override
	public void render(float delta) {
		game.batch.begin();
		game.batch.draw(game.imgInGame, 0, 0);
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
				currentState = player2Turn;
				currentPlayer = game.player2;
				ProcessEndOfTurn(game.player2);				
			}
			else{
				currentState = player1Turn;
				currentPlayer = game.player1;
				ProcessEndOfTurn(game.player1);				
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

	//Update the UI
	public void RefreshUI(){		

		UI.lblPlayer.setText(currentPlayer.playerName + "'s (Player " + currentState + "'s) turn");
		UI.lblPlayer.setText("Player " + currentState + "\n" + currentPlayer.playerName + "'s turn");		
	}

	public void ProcessEndOfTurn(Player player){ 		
		UI.clearGoal();
		System.out.println("Turn " + (turnCount) + " just ended. Turn " + (turnCount+1) + " is now starting.");
		
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
		refreshMovement();
		
		int ranNumber = rdm.nextInt(4);
		Goal g = new Goal(ranNumber, game.map, player);
		player.addGoal(g);
		
		for (Goal goal : player.goals){ 
			UI.addToGoals(player, goal);
			System.out.println(goal.toString()); 
		}		
		RefreshInventory();
	}
	
	public ArrayList<Vector2> findNeighbours(Vector2 tile){
		//Gives the neighbouring cells of the input cell which have track on them
		ArrayList<Vector2> targets = new ArrayList<Vector2>();
		ArrayList<Vector2> track = new ArrayList<Vector2>();
		if (tile.x % 2 == 0){	//Find neighbours of a particular tile on the hexagonal grid
			targets.add(new Vector2(tile.x, tile.y+1));
			targets.add(new Vector2(tile.x, tile.y-1));
			targets.add(new Vector2(tile.x+1, tile.y+1));
			targets.add(new Vector2(tile.x-1, tile.y+1));
			targets.add(new Vector2(tile.x+1, tile.y));
			targets.add(new Vector2(tile.x-1, tile.y));
		}
		else{
			targets.add(new Vector2(tile.x, tile.y+1));
			targets.add(new Vector2(tile.x, tile.y-1));
			targets.add(new Vector2(tile.x+1, tile.y-1));
			targets.add(new Vector2(tile.x-1, tile.y-1));
			targets.add(new Vector2(tile.x+1, tile.y));
			targets.add(new Vector2(tile.x-1, tile.y));
		}
		track = game.getTrackList();
		for (int i=0; i<targets.size();i++){
			if (track.contains(targets.get(i))==false){
				targets.remove(i);
			}
		}
		return targets;
	}
	
	public void findSelected(Vector2 tile){
		//Looks if the tile selected has a train on it
		//Returns id of train in array and the player who owns it
		found = false;
		int player = 0;
		int which = 0;
		if (currentPlayer == game.player1){	//This stops players from controlling each other's trains
			for (int i=0;i<p1Trains.size();i++){
				if (tile.x == p1Trains.get(i).x && tile.y == p1Trains.get(i).y){
					which = i;				
					player = 1;
					found = true;
					searchTrain = p1Trains.get(i);
				}
			}
		}
		else{
			for (int i=0;i<p2Trains.size();i++){
				if (tile.x == p2Trains.get(i).x && tile.y == p2Trains.get(i).y){
					which = i;
					player = 2;
					found = true;
					searchTrain = p2Trains.get(i);
				}
			}
		}
		if (found == true){
			playerTrain = new Vector2(which,player);
		}
	}
	
	public void doMovement(ArrayList<Vector2> trainList, ArrayList<Train> trainObjects, ArrayList<Integer> movement, Vector2 tile){
		// This method moves the train and renders it on the map
		int whichTrain = 0;
		ArrayList<Vector2> targets = new ArrayList<Vector2>();
		
		if (trainList.size() > 0){	
			for (int i=0; i<trainObjects.size(); i++){
				if (searchTrain.x == trainList.get(i).x && searchTrain.y == trainList.get(i).y){
					whichTrain = i;
				}
			}
			targets = findNeighbours(searchTrain);	
			
			for (int j=0; j<targets.size();j++){					
				if (tile.x == targets.get(j).x && tile.y == targets.get(j).y){
					if (movement.get(whichTrain) > 0){							
						game.map.removeTrainTile(searchTrain);
						trainList.set(whichTrain, new Vector2(tile));	//Move train in the array							
						game.map.deployTraintoTile(new Vector2(tile),trainObjects.get(whichTrain));	//Move train on the map
						movement.set(whichTrain, movement.get(whichTrain)-1);	//Decrease the train's movement allowance this turn
					}
					else{
						System.out.println("Train out of movement");
					}													
				}
			}					
		}
	}
	
	public void refreshMovement(){
		//Gives trains movement distance again at the end of the turn
		p1TrainsMovement = new ArrayList<Integer>(p1TrainsMaxMovement);		//Since we're using Vector2s, we can't just use the equality operator
		p2TrainsMovement = new ArrayList<Integer>(p2TrainsMaxMovement);
	}
	
	public void moveTrain(Vector2 tile){
		//This method handles the movement of the trains		
		System.out.println(allTrains.size());	
		System.out.println("tile is" + tile);		

		findSelected(tile);		//which = x, player = y
			
		if (currentPlayer == game.player1){	//Player 1's turn
			doMovement(p1Trains,p1TrainsObjects,p1TrainsMovement,tile);
		}
		else{
			System.out.println("player 2 has trains??");
			doMovement(p2Trains,p2TrainsObjects,p2TrainsMovement,tile);
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
		
		//Sets up the various layers for rendering
		MapTile tile = game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.baseLayerIndex);
		ZooTile ztile = (ZooTile) game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.zooLayerIndex);
		TrackTile ttile = (TrackTile) game.map.getTile((int)tileCoords.x, (int)tileCoords.y, GameMap.trackLayerIndex[Direction.MIDDLE]);

		//Debug, outputs tile info to console.
		
		moveTrain(tileCoords);
				
		System.out.println("\n" + tileCoords.toString());
		System.out.println("Mouse position : " + Integer.toString(x) + ", " + Integer.toString(y));
		System.out.println("Camera position : " + cameraPosition.toString());		
		
		//Since there is lots of things on the same tile, checking what has been clicked on is cascaded down. Zoo then track then maptile.
		//Train is rendered elsewhere, in the doMovement function
		
		if (ztile != null){
			System.out.println(ztile.toString());	
			
			//Deploy Train if it has been selected
			if (currentPlayer.inventory.selectedTrain != null){
				int distance = 0;
				
				switch(currentPlayer.inventory.selectedTrain.type){	//Set movement distances
					case HOVER:
						distance = 10;						
						break;
					case BULLET:
						distance = 8;
						break;
					case ELECTRIC:
						distance = 6;
						break;
					case DIESEL:
						distance = 4;
						break;
					case STEAM:
						distance = 2;
						break;
				}
				
				game.map.deployTraintoTile(tileCoords, currentPlayer.inventory.selectedTrain);
				if (currentPlayer == game.player1){
					p1TrainsObjects.add(currentPlayer.inventory.selectedTrain);
				}
				else{
					p2TrainsObjects.add(currentPlayer.inventory.selectedTrain);
				}
				
				//allTrains.add(currentPlayer.inventory.selectedTrain);
				//currentPlayer.inventory.trains.remove(currentPlayer.inventory.selectedTrain);				
				game.map.deployedTrains.add(currentPlayer.inventory.selectedTrain);
				currentPlayer.inventory.selectedTrain.setLocation(tileCoords);
				currentPlayer.inventory.selectedTrain = null;
							
				if (currentPlayer == game.player1){
					p1Trains.add(new Vector2(tileCoords));
					p1TrainsMovement.add(new Integer(distance));
					p1TrainsMaxMovement.add(new Integer(distance));
				}
				else{
					p2Trains.add(new Vector2(tileCoords));
					p2TrainsMovement.add(new Integer(distance));
					p2TrainsMaxMovement.add(new Integer(distance));
				}
				
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
		UI.addToInventory(currentPlayer);
		System.out.println(currentPlayer.playerNumber+" player number after inventory refresh");
	}
	public void RefreshGoals(){
		UI.clearGoal();
		for (Goal goal : currentPlayer.goals){
			UI.addToGoals(currentPlayer,goal);
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
