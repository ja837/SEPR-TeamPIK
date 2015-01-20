package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen{

	MyGdxGame game;
	
	MainMenuUI UI = new MainMenuUI();

	public MainMenuScreen(final MyGdxGame game){

		this.game = game;
		
		UI.btnStartGame.addListener(new ClickListener(){
			@Override 
			public void clicked(InputEvent event, float x, float y){
				
				if (game.mainMenuScreen.UI.tfPlayer1Name.getText().trim().isEmpty() || game.mainMenuScreen.UI.tfPlayer2Name.getText().trim().isEmpty()) {
					game.mainMenuScreen.UI.lblNameError.setVisible(true);
				}else{
				game.inGameScreen.SwitchToInGameScreen();
				}
			}
		});

		UI.btnInstruction.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				
				if(game.mainMenuScreen.UI.btnInstruction.isPressed());{
					game.instructionScreen.SwitchToInstructionScreen();
				}
			}
		});	
		
		
		


	}

	public void SwitchToMainMenuScreen(){
		Gamestate.MoveToGamestate(Gamestate.MAIN_MENU);
		game.setScreen(game.mainMenuScreen);
		game.inputMultiplexer.addProcessor(UI.stage);
		game.inputMultiplexer.removeProcessor(game.inGameScreen.UI.stage);
		game.inputMultiplexer.removeProcessor(game.instructionScreen.UI.stage);
	}
	
	


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		
		game.batch.begin();
		game.batch.draw(game.imgMainMenu, 0, 0);
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
