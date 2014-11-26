package com.teampik.game;


import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
	Texture img;
	Texture img2;
	
	TextureRegion trDefault;
	TextureRegion trWater;
	TextureRegion trLand;
	TextureRegion trTrack;
	TextureRegion trZoo;
	TextureRegion[] trBorders = new TextureRegion[6];
	
	TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    
    LoadingScreen loadingScreen;
    MainMenuScreen mainMenuScreen;
    InGameScreen inGameScreen;
    BitmapFont font;
    
    GameMap map;
    Vector3 cameraInitPos;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		imgLoading = new Texture("tempSplashscreenLoading.png");
		img = new Texture("tempSplashscreen.png");
		img2 = new Texture("tempInGame.png");
		
		
		loadingScreen = new LoadingScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
        inGameScreen = new InGameScreen(this);
        
        Gamestate.MoveToGamestate(Gamestate.LOADING);
        setScreen(loadingScreen);
		
		
		font = new BitmapFont();
        font.setColor(Color.RED);
		InputChecker inputProcessor = new InputChecker(this);
		Gdx.input.setInputProcessor(inputProcessor);
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        cameraInitPos = camera.position;
        //camera.zoom += 2;
        
        
        camera.update();
        
        
        
        trDefault = new TextureRegion(new Texture("perfectHexagon.png"));
        trWater = new TextureRegion(new Texture("perfectHexagonBlue.png"));
        trLand = new TextureRegion(new Texture("perfectHexagonGreen.png"));
        trTrack = new TextureRegion(new Texture("perfectHexagonGrey.png"));
        trZoo = new TextureRegion(new Texture("perfectHexagonYellow.png"));
        trBorders[Direction.NORTH] = new TextureRegion(new Texture("Borders/borderNorth.png"));
        trBorders[Direction.NORTH_EAST] = new TextureRegion(new Texture("Borders/borderNorthEast.png"));
        trBorders[Direction.SOUTH_EAST] = new TextureRegion(new Texture("Borders/borderSouthEast.png"));
        trBorders[Direction.SOUTH] = new TextureRegion(new Texture("Borders/borderSouth.png"));
        trBorders[Direction.SOUTH_WEST] = new TextureRegion(new Texture("Borders/borderSouthWest.png"));
        trBorders[Direction.NORTH_WEST] = new TextureRegion(new Texture("Borders/borderNorthWest.png"));
        
        
                
        
        MapLayout m = new MapLayout(this, MapLayout.testLayout, GetTestBorderList(), 45, 30);
        
        map = GameMap.createMap(this, m);
        tiledMapRenderer = new HexagonalTiledMapRenderer(map);

        
        //camera.translate(0,427);
        
        Gamestate.MoveToGamestate(Gamestate.MAIN_MENU);
        setScreen(mainMenuScreen);
       
        
        
        
	}
	
	private ArrayList<Vector2>[] GetTestBorderList(){
		ArrayList<Vector2>[] listOfCoordsWithBorders = (ArrayList<Vector2>[]) new ArrayList[6];
		
		for (int i = Direction.NORTH; i <= Direction.NORTH_WEST; i++){
        	listOfCoordsWithBorders[i] = new ArrayList<Vector2>();
        }
		
		//Always add the opposite border, so if 0,0 has a border to the north, 0,1 must have a border to the south.
		
		listOfCoordsWithBorders[Direction.NORTH].add(new Vector2(0,0));
		listOfCoordsWithBorders[Direction.SOUTH].add(new Vector2(0,1));
		
		listOfCoordsWithBorders[Direction.NORTH_EAST].add(new Vector2(0,0));
		listOfCoordsWithBorders[Direction.SOUTH_WEST].add(new Vector2(1,1));
		
		listOfCoordsWithBorders[Direction.NORTH].add(new Vector2(9,8));
		listOfCoordsWithBorders[Direction.SOUTH].add(new Vector2(9,9));
		
		listOfCoordsWithBorders[Direction.NORTH_WEST].add(new Vector2(9,9));
		listOfCoordsWithBorders[Direction.SOUTH_EAST].add(new Vector2(8,9));
		
		listOfCoordsWithBorders[Direction.NORTH_EAST].add(new Vector2(9,8));
		listOfCoordsWithBorders[Direction.SOUTH_WEST].add(new Vector2(10,8));
		
		
        
		
		return listOfCoordsWithBorders;
		
	}


	

	
}
