package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class InstructionUI {
	Skin skin;
	Stage stage;
	
	Label lblBack;
	TextButton btnBack;
	

	public InstructionUI(){
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();
		

		lblBack = new Label("Back", skin);
		lblBack.setPosition(40f, Gdx.graphics.getHeight()-40);
		
		btnBack = new TextButton("Back", skin);
		btnBack.setPosition(40f, Gdx.graphics.getHeight()-40);
		
	
		stage.addActor(btnBack);
	
	}
}
