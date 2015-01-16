package com.teampik.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class InGameUI{
	
	Skin skin;
	Stage stage;
	
	Label lblPlayer;
	TextButton btnEndTurn;
	
	public InGameUI(){
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage();
        
        
        lblPlayer = new Label("", skin);
        lblPlayer.setPosition(Gdx.graphics.getWidth() - 300f, Gdx.graphics.getHeight() - 20f);
        lblPlayer.setWidth(200f);
        lblPlayer.setHeight(20f);
        
        btnEndTurn = new TextButton("End Turn", skin, "default");      
        btnEndTurn.setWidth(200f);
        btnEndTurn.setHeight(20f);
        btnEndTurn.setPosition(Gdx.graphics.getWidth() - 200f, 20f);
        
        stage.addActor(btnEndTurn);
        stage.addActor(lblPlayer);
	}
	


}
