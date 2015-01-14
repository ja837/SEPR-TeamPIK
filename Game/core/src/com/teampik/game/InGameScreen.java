package com.teampik.game;

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

public class InGameScreen implements Screen{
	
	
	MyGdxGame game;
	
	public InGameScreen(MyGdxGame game){
        this.game = game;
        
}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		game.batch.begin();
		game.batch.draw(game.img2, 0, 0);
		//context.font.draw(context.batch, coords.toString(), 200, 200);
		game.batch.end();
			
		game.camera.update();
		game.tiledMapRenderer.setView(game.camera);
		game.tiledMapRenderer.render();
		
		
	}
	
	
	public void selectTile(int x, int y){
		
		Vector3 cameraPosition = game.camera.position;
		
		
		Vector2 tileCoords = GameMap.getCoordsFromPoint(x, y, game.camera);
		
		MapTile tile = game.map.getTile((int)tileCoords.x, (int)tileCoords.y, 0);
		
		//Debug, outputs tile info to console.
		System.out.println("\n" + tileCoords.toString());
		System.out.println("Mouse position : " + Integer.toString(x) + ", " + Integer.toString(y));
		System.out.println("Camera position : " + cameraPosition.toString());		
				
		if (tile != null){
			if (tile.borders[0] || tile.borders[1] || tile.borders[2] || tile.borders[3] || tile.borders[4] || tile.borders[5]){
				System.out.println(tile.toString());
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
