package com.teampik.game;


import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture imgLoading;
	Texture imgMainMenu;
	Texture imgInGame;
	
	Texture labelBackgroundRed;
	Texture labelBackgroundBlue;
	
	
	TextureRegion trDefault;
	TextureRegion trWater;
	TextureRegion trLand;
	TextureRegion trSnow;
	TextureRegion trForest;
	TextureRegion trMountain;
	TextureRegion trDesert;
	TextureRegion trTrack;
	TextureRegion trZoo;
	TextureRegion trSelected;
	TextureRegion[] trBorders = new TextureRegion[6];
	Vector2 currentlySelectedTile = new Vector2(-1,-1);
	
	TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    
    LoadingScreen loadingScreen;
    MainMenuScreen mainMenuScreen;
    InGameScreen inGameScreen;
    BitmapFont font;
    
    GameMap map;
    Vector3 cameraInitPos;
    
    Player player1;
    Player player2;
 
    
    InputMultiplexer inputMultiplexer = new InputMultiplexer();
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		imgLoading = new Texture("tempSplashscreenLoading.png");
		imgMainMenu = new Texture("GUI/MainMenu/background.png");
		imgInGame = new Texture("tempInGame.png");
		
		InputChecker inputProcessor = new InputChecker(this);		
        inputMultiplexer.addProcessor(inputProcessor);
		
		loadingScreen = new LoadingScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
        inGameScreen = new InGameScreen(this);
        
        Gamestate.MoveToGamestate(Gamestate.LOADING);
        setScreen(loadingScreen);
		
		
		LoadAssets();

       
        
        Gamestate.MoveToGamestate(Gamestate.MAIN_MENU);
        setScreen(mainMenuScreen);
       
            
	}

	private void LoadAssets() {
						
		font = new BitmapFont();
        font.setColor(Color.RED);
        
		
        
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        cameraInitPos = camera.position;
        
        
        
        camera.update();
         
        
        trDefault = new TextureRegion(new Texture("Tiles/lava1.png"));
        trWater = new TextureRegion(new Texture("Tiles/water1.png"));
        trLand = new TextureRegion(new Texture("Tiles/land.png"));
        trSnow = new TextureRegion(new Texture("Tiles/snow1.png"));
        trDesert = new TextureRegion(new Texture("Tiles/desert.png"));
        trMountain = new TextureRegion(new Texture("Tiles/mountain.png"));
        trForest = new TextureRegion(new Texture("Tiles/forest.png"));
        trTrack = new TextureRegion(new Texture("track.png"));
        trZoo = new TextureRegion(new Texture("zoo.png"));
        trSelected = new TextureRegion(new Texture("perfectHexagonSelected.png"));
        trBorders[Direction.NORTH] = new TextureRegion(new Texture("Borders/borderNorth.png"));
        trBorders[Direction.NORTH_EAST] = new TextureRegion(new Texture("Borders/borderNorthEast.png"));
        trBorders[Direction.SOUTH_EAST] = new TextureRegion(new Texture("Borders/borderSouthEast.png"));
        trBorders[Direction.SOUTH] = new TextureRegion(new Texture("Borders/borderSouth.png"));
        trBorders[Direction.SOUTH_WEST] = new TextureRegion(new Texture("Borders/borderSouthWest.png"));
        trBorders[Direction.NORTH_WEST] = new TextureRegion(new Texture("Borders/borderNorthWest.png"));
        
        labelBackgroundRed = new Texture("RED.png");
        labelBackgroundBlue = new Texture("BLUE.png");
        
                
        
        MapLayout m = new MapLayout(this, GetTestTileLayout(), GetTestBorderList(), GetTestTrackList(), GetTestZooList(), 45, 30);
        
        map = GameMap.createMap(this, m);
        tiledMapRenderer = new HexagonalTiledMapRenderer(map);
        
        player1 = new Player();
        player2 = new Player();
	}
	
	
	//Just an example list of borders
	private ArrayList<Vector2>[] GetTestBorderList(){
		ArrayList<Vector2>[] lstBorderCoords = (ArrayList<Vector2>[]) new ArrayList[6];
		
		for (int i = Direction.NORTH; i <= Direction.NORTH_WEST; i++){
        	lstBorderCoords[i] = new ArrayList<Vector2>();
        }
		
		// {x,y,nw,n,ne}
		int[][] coords = new int [][]{
				{1,4,1,1,0},{1,5,0,0,1},{1,6,0,0,1},
				{2,4,1,0,0},{2,5,1,0,0},{2,6,1,1,0},{2,7,0,0,1},{2,9,0,1,1},
				{3,7,1,0,0},{3,8,1,1,0},{3,9,0,1,1},
				{4,8,1,0,0},
				{8,9,0,0,1},{8,23,0,0,1},{8,24,0,0,1},
				{9,9,0,1,1},{9,24,1,0,0},
				{10,8,0,1,1},{10,19,0,1,0},
				{11,8,0,1,1},{11,20,1,1,0},{11,21,0,0,1},{11,22,0,0,1},
				{12,7,0,1,1},{12,20,1,0,0},{12,21,1,0,0},{12,23,0,1,0},
				{13,7,0,1,1},{13,24,1,1,0},
				{15,17,0,1,1},
				{16,16,0,1,1},{16,18,0,1,1},
				{17,9,0,0,1},{17,10,0,0,1},{17,11,0,0,1},{17,12,0,0,1},{17,16,0,1,1},{17,18,0,1,1},
				{18,9,1,0,0},{18,10,1,1,0},{18,11,1,0,0},{18,12,1,1,0},{18,13,0,0,1},{18,14,0,0,1},{18,15,0,1,1},{18,16,0,0,1},{18,17,0,1,1},
				{19,11,1,1,1},{19,13,1,1,0},{19,14,1,0,0},{19,15,1,0,0},{19,16,1,0,0},{19,17,1,0,0},{19,18,1,1,0},{19,19,0,0,1},{19,20,0,0,1},
				{20,10,0,1,0},{20,12,0,1,1},{20,18,1,0,0},{20,19,1,0,0},
				{21,11,1,1,0},{21,13,1,0,0},{21,12,0,1,1},{21,13,0,1,1},{22,11,1,1,0},{22,12,0,1,0},
				{23,12,1,1,1},{23,13,1,1,0},
				{24,11,0,1,1},{24,13,1,1,0},{24,14,0,1,0},{24,15,1,0,0},
				{25,11,1,0,0},{25,13,0,1,0},{25,15,0,1,0},{25,17,0,0,1},{25,18,0,0,1},{25,19,0,0,1},
				{26,16,0,1,0},{26,17,1,1,0},{26,18,1,0,0},{26,19,1,0,0},
				{27,17,0,1,0},
				{28,14,0,1,0},{28,16,0,1,0},
				{29,15,0,1,0},{29,16,0,1,0},
				};
		for (int i=0; i < coords.length; i++) {
			if (coords[i][4] == 1) {
				lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(coords[i][0],coords[i][1]));
			}
			if (coords[i][3] == 1) {
				lstBorderCoords[Direction.NORTH].add(new Vector2(coords[i][0],coords[i][1]));
			}
			if (coords[i][2] == 1) {
				lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(coords[i][0],coords[i][1]));
			}
		}
//			
//		// NORTH borders
//		lstBorderCoords[Direction.NORTH].add(new Vector2(1,4));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(2,6));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(2,9));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(3,8));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(3,9));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(9,9));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(10,8));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(10,19));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(11,8));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(11,20));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(12,7));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(12,23));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(13,7));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(13,24));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(15,17));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(16,16));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(16,18));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(17,16));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(17,18));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(18,15));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(18,17));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(18,10));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(18,12));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(19,11));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(19,13));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(19,18));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(20,10));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(20,12));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(21,11));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(21,12));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(21,13));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(22,11));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(22,12));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(23,13));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(23,12));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(24,11));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(24,13));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(24,14));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(25,13));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(25,15));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(26,16));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(26,17));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(27,17));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(28,14));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(28,16));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(29,15));
//		lstBorderCoords[Direction.NORTH].add(new Vector2(29,16));
//		
		// SOUTH borders
		for (Vector2 v : lstBorderCoords[Direction.NORTH]){
			if ((int)v.y != 29) {
				lstBorderCoords[Direction.SOUTH].add(new Vector2((int)v.x,(int)v.y+1));
			}
        }
//		
//		// NORTHWEST borders
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(1,4));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(2,4));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(2,5));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(2,6));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(3,7));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(3,8));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(4,8));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(9,24));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(11,20));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(12,20));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(12,21));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(13,24));
//		
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(18,9));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(18,10));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(18,11));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(18,12));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(19,11));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(19,13));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(19,14));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(19,15));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(19,16));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(19,17));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(19,18));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(20,18));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(20,19));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(21,11));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(21,13));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(22,11));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(23,12));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(23,13));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(24,13));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(24,15));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(25,11));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(26,17));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(26,18));
//		lstBorderCoords[Direction.NORTH_WEST].add(new Vector2(26,19));
		
		// SOUTHEAST borders
		for (Vector2 v : lstBorderCoords[Direction.SOUTH_EAST]){
			if ((int)v.x % 2 == 0) {
				lstBorderCoords[Direction.SOUTH_EAST].add(new Vector2((int)v.x-1,(int)v.y-1));
			} else {
				lstBorderCoords[Direction.SOUTH_EAST].add(new Vector2((int)v.x-1,(int)v.y+1));
			}
        }
		
		
//		// NORTHEAST borders
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(1,5));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(1,6));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(2,7));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(2,9));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(3,9));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(8,9));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(8,23));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(8,24));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(9,9));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(10,8));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(11,8));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(11,21));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(11,22));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(12,7));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(13,7));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(17,9));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(15,17));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(16,16));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(16,18));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(17,18));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(17,16));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(17,10));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(17,11));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(17,12));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(18,13));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(18,14));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(18,15));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(18,16));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(18,17));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(19,11));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(19,19));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(19,20));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(20,12));
//		
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(21,12));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(21,13));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(23,12));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(24,11));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(24,14));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(25,17));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(25,18));
//		lstBorderCoords[Direction.NORTH_EAST].add(new Vector2(25,19));
////		
		// SOUTHWEST borders
		for (Vector2 v : lstBorderCoords[Direction.NORTH_EAST]){
			if ((int)v.x % 2 == 0) {
				lstBorderCoords[Direction.SOUTH_WEST].add(new Vector2((int)v.x+1,(int)v.y+1));
			} else {
				lstBorderCoords[Direction.SOUTH_WEST].add(new Vector2((int)v.x+1,(int)v.y));
			}
		}
		
		
		
		
		
        
		
		return lstBorderCoords;
		
	}
	
	//Example map layout.
	private int[][] GetTestTileLayout(){
		
		int w = MapLayout.WATER;
		int l = MapLayout.LAND;
		int z = MapLayout.ZOO;
		int t = MapLayout.TRACK;
		int s = MapLayout.SNOW;
		int d = MapLayout.DESERT;
		int f = MapLayout.FOREST;
		int m = MapLayout.MOUNTAIN;
		
		return new int [] [] {
				{w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,l,l,l,w,w,w,l,l,l,w,l,l,s,s,s,s,s,s,s},
				{w,w,w,w,w,w,w,w,w,w,w,l,l,w,w,w,w,w,w,w,l,l,l,l,l,l,l,l,l,l,w,w,w,w,w,l,w,l,s,s,s,s,s,s,s},
				{w,w,w,w,w,w,w,w,w,w,w,m,m,m,w,w,w,w,w,w,l,l,l,w,l,l,l,l,l,w,w,w,w,l,l,l,l,s,s,s,s,s,s,s,s},
				{w,w,w,w,w,w,w,w,w,w,w,l,l,w,w,w,w,w,w,w,w,w,w,w,l,l,l,l,w,w,w,w,l,w,l,l,l,l,s,s,s,s,s,s,s},
				{w,w,w,w,w,w,w,w,w,l,w,l,l,l,w,w,w,w,w,w,w,w,w,w,w,l,l,l,w,w,w,w,w,w,l,l,l,l,s,s,s,s,s,l,l},
				{w,w,w,w,w,w,w,l,l,l,w,w,l,l,w,w,w,w,w,w,w,w,w,w,w,l,l,l,w,w,w,w,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,l,l,l,w,w,w,l,l,w,w,w,w,w,w,w,w,w,w,w,l,l,w,w,w,w,w,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,l,l,l,w,w,l,l,l,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,w,l,l,l,w,w,w,w,w,w,w,l,l,s,w,s,w,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,w,w,w,w,l,l,l,l,s,s,s,s,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,w,w,l,l,l,l,l,l,l,s,s,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,f,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,l,f,f,f,f,f,f,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,l,w,l,l,l,l,l,l,l,l,l,f,f,f,f,f,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,m,l,m,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,l,l,l,l,l,l,m,l,m,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,f,l,f,l,l,l,w,l,w,l},
				{w,w,l,l,w,l,w,w,w,w,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,f,f,f,f,l,l,l,w,w,w,l},
				{w,w,l,l,l,l,l,l,w,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,w,w,l,l,l,l,l,l,l,l,f,f,f,f,f,l,l,w,w,w,w},
				{w,w,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,l,w,w,l,l,l,w,w,l,l,l,l,l,l,l,l,l,f,f,f,f,l,w,w,w,w,w},
				{w,l,l,l,l,l,l,l,l,l,l,l,l,l,w,w,l,w,w,w,w,w,l,l,w,w,l,l,l,l,l,l,l,l,l,l,f,l,l,w,w,w,w,w,w},
				{w,l,l,l,l,l,l,l,l,l,l,l,l,l,w,w,w,w,w,w,w,w,l,l,l,w,w,w,l,l,l,l,l,l,l,l,l,l,l,w,w,w,w,w,w},
				{l,l,l,l,l,l,l,l,l,l,l,w,l,w,w,w,w,w,w,l,l,w,w,l,l,l,w,w,w,w,l,l,l,l,l,l,l,l,l,l,w,w,w,l,l},
				{l,l,l,l,d,d,d,d,l,l,w,w,w,w,w,w,w,w,w,w,w,w,w,w,l,l,l,w,w,w,w,l,l,l,l,l,l,w,l,l,l,l,l,l,l},
				{l,l,l,d,d,d,d,d,l,l,w,w,w,l,w,w,w,w,w,l,w,w,w,w,w,w,l,l,l,l,w,l,l,l,l,l,w,w,l,l,l,l,l,l,l},
				{w,w,l,l,d,l,l,l,l,w,w,w,w,w,w,w,w,w,w,l,w,w,w,w,w,w,l,l,w,w,w,l,l,l,w,w,w,w,l,l,l,l,l,l,l},
				{w,w,l,w,l,w,l,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,l,w,w,w,w,l,l,l,w,w,w,w,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,l,l,l,l,w,w,w,l,l,l,l,l,l},
				{w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,w,l,l,w,w,w,w,w,l,l,l,l,l}
		};
	}
		
	
	
	private ArrayList<Vector2> GetTestTrackList(){
		ArrayList<Vector2> trackCoords = new ArrayList<Vector2>();
		
		trackCoords.add(new Vector2(5,11));
		trackCoords.add(new Vector2(6,11));
		trackCoords.add(new Vector2(7,11));
		trackCoords.add(new Vector2(8,11));
		trackCoords.add(new Vector2(9,11));
		
		return trackCoords;
		
	}
	
	private ArrayList<Vector2> GetTestZooList(){
		ArrayList<Vector2> zooCoords = new ArrayList<Vector2>();
		
		zooCoords.add(new Vector2(10,10));
		zooCoords.add(new Vector2(4,11));
		zooCoords.add(new Vector2(1,1));
		
		return zooCoords;
		
	}
	

	
}
