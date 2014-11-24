package com.teampik.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;


public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	Texture img2;
	
	TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    
    MainMenuScreen mainMenuScreen;
    InGameScreen inGameScreen;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tempSplashscreen.png");
		img2 = new Texture("tempInGame.png");
		InputChecker inputProcessor = new InputChecker(this);
		Gdx.input.setInputProcessor(inputProcessor);
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
        tiledMap = new TmxMapLoader().load("tempMap.tmx");
        tiledMapRenderer = new HexagonalTiledMapRenderer(tiledMap);
        
        mainMenuScreen = new MainMenuScreen(this);
        inGameScreen = new InGameScreen(this);
        setScreen(mainMenuScreen);
        
        createMap();
	}


	private void createMap() {
		
		//TODO
	}

	
}
