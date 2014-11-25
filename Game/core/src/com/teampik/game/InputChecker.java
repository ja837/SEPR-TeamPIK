package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;




public class InputChecker implements InputProcessor
{
	
	private MyGdxGame context;
	
	public InputChecker(MyGdxGame game){
		
		context = game;
	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		
		
		switch (Gamestate.GetGamestate())
		{
		case Gamestate.MAIN_MENU:
			
			
			break;
			
		case Gamestate.IN_GAME:
			if (keycode == Input.Keys.W){
				context.camera.translate(0, TestTile.height);
			}
			if (keycode == Input.Keys.A){
				context.camera.translate(-TestTile.side, 0);
			}
			if (keycode == Input.Keys.S){
				context.camera.translate(0, -TestTile.height);
			}
			if (keycode == Input.Keys.D){
				context.camera.translate(TestTile.side, 0);
			}
			
			if (keycode == Input.Keys.Q){
				context.camera.translate(0, 1);
			}
			if (keycode == Input.Keys.E){
				context.camera.translate(0, -1);
			}
			
			
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (Gamestate.GetGamestate())
		{
		case Gamestate.MAIN_MENU:
			if (keycode == Input.Keys.ENTER){
				Gamestate.MoveToGamestate(Gamestate.IN_GAME);
				context.setScreen(context.inGameScreen);
				
			}
			
			
			break;
			
		case Gamestate.IN_GAME:
			if (keycode == Input.Keys.ESCAPE){
				Gamestate.MoveToGamestate(Gamestate.MAIN_MENU);
				context.setScreen(context.mainMenuScreen);
			}
			
			
			break;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
		switch (Gamestate.GetGamestate())
		{
		case Gamestate.MAIN_MENU:
			break;
		case Gamestate.IN_GAME:
			if (button == Buttons.LEFT){
				context.inGameScreen.selectTile(screenX, screenY);
			}
			
			
			
			
			break;
		}
		
		
		
		
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}
