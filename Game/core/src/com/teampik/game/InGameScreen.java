package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InGameScreen implements Screen{
	
	
	
	
	
	MyGdxGame context;
	
	public InGameScreen(MyGdxGame game){
        this.context = game;
        
}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		context.batch.begin();
		context.batch.draw(context.img2, 0, 0);
		//context.font.draw(context.batch, coords.toString(), 200, 200);
		context.batch.end();
		
		
		
		context.camera.update();
		context.tiledMapRenderer.setView(context.camera);
		context.tiledMapRenderer.render();
		
		TestTile t = (TestTile) context.tiledMap.getTileSets().getTile(1000);
		
		TiledMapTileLayer layer = (TiledMapTileLayer) context.tiledMap.getLayers().get(0);
		TiledMapTile t2 = layer.getCell(10, 0).getTile();
		
//		Vector2 coords = CoordinateGenerator.GetCoord(t.getId());
		
		
		
		
		
	}
	
	
	public void selectTile(int x, int y){
		
		Vector3 cameraPosition = context.camera.position;
		
		Vector2 tileCoords = TestTile.getCoordsFromPoint(x, y, cameraPosition);
		System.out.println(tileCoords.toString());
		System.out.println("Mouse position : " + Integer.toString(x) + ", " + Integer.toString(y));
		System.out.println("Camera position : " + cameraPosition.toString());
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
