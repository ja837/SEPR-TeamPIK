package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;

public class MainMenuUI{
	
	Skin skin;
	Stage stage;
	
	TextField tfPlayer1Name;
	TextField tfPlayer2Name;
	
	String player1Name;
	String player2Name;
	
	Label lblPlayer1Name;
	Label lblPlayer2Name;
	Label lblNameError;
	
	TextButton btnStartGame;
	
	public MainMenuUI(){
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();

		lblPlayer1Name = new Label("Player 1 name", skin);
		lblPlayer2Name = new Label("Player 2 name", skin);
		lblNameError = new Label("Please enter a name for both players", skin);
		tfPlayer1Name = new TextField("", skin);
		tfPlayer2Name = new TextField("", skin);
		
		lblPlayer1Name.setPosition((Gdx.graphics.getWidth() / 2) - lblPlayer1Name.getPrefWidth() - 20, Gdx.graphics.getHeight() / 4 + 20);		
		lblPlayer2Name.setPosition((Gdx.graphics.getWidth() / 2) - lblPlayer2Name.getPrefWidth() - 20, Gdx.graphics.getHeight() / 4 - 20);
		lblNameError.setPosition((Gdx.graphics.getWidth() / 2) - lblNameError.getPrefWidth() + 100, Gdx.graphics.getHeight() / 4 + 80);
		lblNameError.setVisible(false);
		tfPlayer1Name.setPosition((Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight() / 4 + 20);
		tfPlayer1Name.setWidth(200f);
		tfPlayer1Name.setHeight(20f);
		
		tfPlayer2Name.setPosition((Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight() / 4 - 20);
		tfPlayer2Name.setWidth(200f);
		tfPlayer2Name.setHeight(20f);

		tfPlayer1Name.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {
				player1Name= textField.getText();
			}
		});
		
		tfPlayer2Name.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {
				player2Name= textField.getText();
			}
		});
		
		btnStartGame = new TextButton("Start Game", skin);
		btnStartGame.setPosition((Gdx.graphics.getWidth() / 2) - btnStartGame.getPrefWidth(), Gdx.graphics.getHeight() / 4 - 60);
		
		
		

		stage.addActor(lblPlayer1Name);
		stage.addActor(lblPlayer2Name);
		stage.addActor(lblNameError);
		stage.addActor(tfPlayer1Name);
		stage.addActor(tfPlayer2Name);
		stage.addActor(btnStartGame);
	
	}

}
