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
    
    
    MainMenuScreen mainMenuScreen;
    InGameScreen inGameScreen;
    BitmapFont font;
    
    GameMap map;
    Vector3 cameraInitPos;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tempSplashscreen.png");
		img2 = new Texture("tempInGame.png");
		
		
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
        
        ArrayList<Vector2>[] listOfCoordsWithBorders = (ArrayList<Vector2>[]) new ArrayList[6];
        for (int i = Direction.NORTH; i < Direction.NORTH_WEST; i++){
        	listOfCoordsWithBorders[i] = new ArrayList<Vector2>();
        }
        
        listOfCoordsWithBorders[Direction.NORTH].add(new Vector2(0,0));
        listOfCoordsWithBorders[Direction.NORTH_EAST].add(new Vector2(0,0));
        
        
        MapLayout m = new MapLayout(this, MapLayout.testLayout, listOfCoordsWithBorders, 45, 30);
        
        map = GameMap.createMap(this, m);
        tiledMapRenderer = new HexagonalTiledMapRenderer(map);

        
        //camera.translate(0,427);
        
        
        mainMenuScreen = new MainMenuScreen(this);
        inGameScreen = new InGameScreen(this);
        setScreen(mainMenuScreen);
        
        
	}


	

	
}
