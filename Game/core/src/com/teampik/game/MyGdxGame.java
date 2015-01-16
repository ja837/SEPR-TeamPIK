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
				{19,11,1,1,1},{19,13,1,1,1},{19,14,1,0,0},{19,15,1,0,0},{19,16,1,0,0},{19,17,1,0,0},{19,18,1,1,0},{19,19,0,0,1},{19,20,0,0,1},
				{20,10,0,1,0},{20,12,0,1,1},{20,18,1,0,0},{20,19,1,0,0},
				{21,11,1,1,0},{21,13,1,0,0},{21,12,0,1,1},{21,13,0,1,1},{22,11,1,1,0},{22,12,0,1,0},
				{23,12,1,1,1},{23,13,1,1,0},
				{24,11,0,1,1},{24,13,1,1,0},{24,14,0,0,1},{24,15,0,0,1},{24,27,1,1,0},{24,28,0,0,1},
				{25,11,1,1,0},{25,14,1,1,0},{25,15,1,0,0},{25,16,1,1,0},{25,17,0,0,1},{25,18,0,0,1},{25,19,0,0,1},{25,20,0,0,1},{25,28,1,0,0},{25,29,1,0,0},
				{26,11,1,1,0},{26,14,1,1,1},{26,16,1,1,1},{26,17,1,0,0},{26,18,1,0,0},{26,19,1,0,0},{26,20,1,0,0},
				{27,8,0,0,1},{27,12,1,1,0},{27,13,0,0,1},{27,14,0,1,1},{27,16,0,1,1},
				{28,7,1,0,0},{28,8,1,1,0},{28,12,1,1,1},{28,13,1,0,0},{28,15,0,1,1},
				{29,9,1,1,1},{29,12,0,1,1},{29,15,0,1,1},
				{30,8,0,1,0},{30,11,0,1,1},{30,12,1,1,0},{30,14,0,1,0},{30,21,0,0,1},
				{31,6,1,1,1},{31,9,1,1,0},{31,10,0,0,1},{31,11,0,1,1},{31,13,1,1,0},{31,15,1,1,1},{31,21,0,1,1},
				{32,5,0,1,0},{32,9,1,1,1},{32,10,1,0,0},{32,11,1,1,0},{32,13,1,1,1},{32,14,0,1,1},{32,20,0,1,1},
				{33,6,1,1,1},{33,7,0,0,1},{33,9,0,1,1},{33,12,1,1,0},{33,13,0,1,1},{33,14,1,1,0},{33,15,0,0,1},{33,18,0,0,1},{33,19,0,0,1},{33,20,0,1,1},
				{34,5,0,1,1},{34,5,0,1,1},{34,7,1,1,0},{34,6,1,0,0},{34,8,0,1,1},{34,12,1,1,0},{34,14,1,0,0},{34,15,1,1,0},{34,16,0,0,1},{34,17,0,1,1},{34,18,1,0,0},{34,19,1,0,0},{34,20,1,1,0},
				{35,5,0,1,1},{35,8,1,1,1},{35,13,1,1,0},{35,16,1,0,0},{35,17,1,0,0},{35,18,1,1,0},{35,21,1,1,0},{35,23,0,0,1},{35,22,0,0,1},{35,26,0,0,1},{35,27,0,0,1},
				{36,7,0,1,0},{36,13,1,1,0},{36,18,1,1,1},{36,21,1,0,0},{36,22,1,0,0},{36,23,1,1,0},{36,24,0,0,1},{36,25,0,1,1},{36,26,1,0,0},{36,27,1,0,0},
				{37,8,1,1,0},{37,14,1,1,1},{37,18,0,1,1},{37,24,1,1,1},{37,25,1,0,0},
				{38,5,1,1,0},{38,8,1,1,1},{38,13,0,1,1},{38,17,0,1,0},{38,22,0,0,1},{38,23,0,1,1},
				{39,6,1,0,0},{39,13,0,1,1},{39,18,1,1,0},{39,22,0,1,1},{39,23,1,0,0},
				{40,12,0,1,1},{40,18,1,1,0},{40,19,0,0,1},{40,20,0,0,1},{40,21,0,1,1},
				{41,19,1,1,0}, {41,20,1,0,0},{41,21,1,0,0},
				{42,19,1,1,1},
				{43,18,0,0,1},{43,19,0,1,1},
				{44,17,0,1,0}, {44,18,1,0,0}
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

		// SOUTH borders
		for (Vector2 v : lstBorderCoords[Direction.NORTH]){
			if ((int)v.y != 29) {
				lstBorderCoords[Direction.SOUTH].add(new Vector2((int)v.x,(int)v.y+1));
			}
        }
	
		// SOUTHEAST borders
		for (Vector2 v : lstBorderCoords[Direction.SOUTH_EAST]){
			if ((int)v.x % 2 == 0) {
				lstBorderCoords[Direction.SOUTH_EAST].add(new Vector2((int)v.x-1,(int)v.y-1));
			} else {
				lstBorderCoords[Direction.SOUTH_EAST].add(new Vector2((int)v.x-1,(int)v.y+1));
			}
        }
		
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
		
		trackCoords.add(new Vector2(1,5));
		
		return trackCoords;
		
	}
	
	private ArrayList<ZooParams> GetTestZooList(){
		ArrayList<ZooParams> zooParmas = new ArrayList<ZooParams>();
				
		zooParmas.add(new ZooParams(new Vector2(0,5), "Lisbon"));
		zooParmas.add(new ZooParams(new Vector2(4,16), "Atlantis"));
		zooParmas.add(new ZooParams(new Vector2(6,6), "Madrid"));
		zooParmas.add(new ZooParams(new Vector2(8,23), "Cardiff"));
		zooParmas.add(new ZooParams(new Vector2(11,26), "Edinburgh"));
		return zooParmas;
		
	}
	

	
}
