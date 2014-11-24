package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
		context.batch.end();
		
		
		
		context.camera.update();
		context.tiledMapRenderer.setView(context.camera);
		context.tiledMapRenderer.render();
		
		
		
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
