package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;




public class InputChecker implements InputProcessor
{
	
	private MyGdxGame game;
	
	private final Vector3 current = new Vector3();
	private final Vector3 mouse = new Vector3();
	private final Vector3 delta = new Vector3();
	private final Vector3 last = new Vector3(-1, -1, -1);
	
	public InputChecker(MyGdxGame game){
		
		this.game = game;
	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		
		
		switch (Gamestate.GetGamestate())
		{
		case Gamestate.MAIN_MENU:
			
			
			break;
			
		case Gamestate.IN_GAME:
			if (keycode == Input.Keys.W){
				game.camera.translate(0, 10); //GameMap.tileHeight
			}
			if (keycode == Input.Keys.A){
				game.camera.translate(-10, 0); //-GameMap.tileSide
			}
			if (keycode == Input.Keys.S){
				game.camera.translate(0, -10); //-GameMap.tileHeight
			}
			if (keycode == Input.Keys.D){
				game.camera.translate(10, 0); //GameMap.tileSide
			}
			
			if (keycode == Input.Keys.Q){
				game.camera.translate(0, 1);
			}
			if (keycode == Input.Keys.E){
				game.camera.translate(0, -1);
			}
			if (keycode == Input.Keys.SPACE){
				switch (game.inGameScreen.currentState){
				case (InGameScreen.player1Turn):
					game.inGameScreen.currentState = game.inGameScreen.endOfTurnProcessing;
					break;
				
				case (InGameScreen.player2Turn):
					game.inGameScreen.currentState = game.inGameScreen.endOfTurnProcessing;
					break;
				}
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
				game.setScreen(game.inGameScreen);
				
			}
			
			
			break;
			
		case Gamestate.IN_GAME:
			if (keycode == Input.Keys.ESCAPE){
				Gamestate.MoveToGamestate(Gamestate.MAIN_MENU);
				game.setScreen(game.mainMenuScreen);
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
			if (button == Buttons.LEFT){
				Gamestate.MoveToGamestate(Gamestate.IN_GAME);
				game.setScreen(game.inGameScreen);
			}
			break;
		case Gamestate.IN_GAME:
			if (button == Buttons.LEFT){
				game.inGameScreen.selectTile(screenX, screenY);
			}
			
						
			
			break;
		}
		
		
		
		
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch (Gamestate.GetGamestate())
		{
		case Gamestate.MAIN_MENU:
			break;
		case Gamestate.IN_GAME:
			if (button == Buttons.LEFT){
				last.set(-1, -1, -1);
			}
			
						
			
			break;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		
		switch (Gamestate.GetGamestate())
		{
		case Gamestate.MAIN_MENU:
			break;
		case Gamestate.IN_GAME:
			
			game.camera.unproject(current.set(screenX, screenY, 0));
			
			if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			      game.camera.unproject(delta.set(last.x, last.y, 0));
			      delta.sub(current);
			      game.camera.position.add(delta.x, delta.y, 0);
			    }
			    last.set(screenX, screenY, 0);
						
			
			break;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		switch (Gamestate.GetGamestate())
		{
		case Gamestate.MAIN_MENU:
			break;
		case Gamestate.IN_GAME:
			
			if (amount > 0 && game.camera.zoom < 1.5) {
				game.camera.zoom += 0.1f;
			}

	        //Zoom in
			if (amount < 0 && game.camera.zoom > 0.5) {
				game.camera.zoom -= 0.1f;
			}
			
						
			
			break;
		}

		
		return false;
	}



	
	

	
}
