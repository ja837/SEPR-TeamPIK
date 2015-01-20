package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InstructionScreen implements Screen{

	MyGdxGame game;
	InstructionUI UI = new InstructionUI();
	
	public InstructionScreen(final MyGdxGame game){
		this.game = game;
		
		
		UI.btnBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//if(game.instructionScreen.UI.btnBack.isPressed());{
					game.mainMenuScreen.SwitchToMainMenuScreen();
				//}
			}		
		});
		
			
	}

	
	public void SwitchToInstructionScreen(){
		Gamestate.MoveToGamestate(Gamestate.INSTRUCTIONS);
		game.setScreen(game.instructionScreen);
		game.inputMultiplexer.addProcessor(UI.stage);	
		game.inputMultiplexer.removeProcessor(game.mainMenuScreen.UI.stage);
		game.inputMultiplexer.removeProcessor(game.inGameScreen.UI.stage);
	}
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(game.imgInstructions, 0, 0);
		game.batch.end();
		
		game.batch.begin();
		UI.stage.draw();
		game.batch.end();
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
