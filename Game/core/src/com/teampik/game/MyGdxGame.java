package com.teampik.game;


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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;


public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	Texture img2;
	Texture hexagon;
	
	TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    TextureRegion tr;
    
    MainMenuScreen mainMenuScreen;
    InGameScreen inGameScreen;
    BitmapFont font;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tempSplashscreen.png");
		img2 = new Texture("tempInGame.png");
		hexagon = new Texture("perfectHexagon.png");
		font = new BitmapFont();
        font.setColor(Color.RED);
		InputChecker inputProcessor = new InputChecker(this);
		Gdx.input.setInputProcessor(inputProcessor);
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
        tiledMap = new TmxMapLoader().load("tempMap.tmx");
        
        
        tr = new TextureRegion(hexagon);
        tiledMapRenderer = new HexagonalTiledMapRenderer(createMap());
        
        
        mainMenuScreen = new MainMenuScreen(this);
        inGameScreen = new InGameScreen(this);
        setScreen(mainMenuScreen);
        
        
	}


	private TiledMap createMap() {
		
		TiledMap map = new TiledMap();
		MapLayers layers = map.getLayers();

		TiledMapTileLayer layer1 = new TiledMapTileLayer(20, 20, 64, 56); //Number of tiles in x direction, Number of tiles in y direction, pixel width of tile, pixel height of tile
		
		for (int i = 0; i < 20; i++){
			for (int j = 0; j < 20; j++){
				Cell cell = new Cell();
				
				TestTile tile = new TestTile(tr); //radius of 32 pixels
				tile.setId(CoordinateGenerator.GenerateTileID(i, j));
				
				cell.setTile(tile);
				layer1.setCell(i, 20 - j, cell);
				
			}
		}
		

		layers.add(layer1);
		
		return map;
		
		
	}

	
}
