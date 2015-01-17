package com.teampik.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InGameUI{

	Skin skin;
	Stage stage;

	Label lblPlayer;
	Label lblInventory;
	TextButton btnEndTurn;

	ArrayList<InventoryTrainButton> inventoryItems;


	public InGameUI(){
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();

		inventoryItems = new ArrayList<InventoryTrainButton>();


		lblPlayer = new Label("", skin);
		lblPlayer.setPosition(Gdx.graphics.getWidth() - 300f, Gdx.graphics.getHeight() - 20f);
		lblPlayer.setWidth(200f);
		lblPlayer.setHeight(20f);

		lblInventory = new Label("Inventory", skin);
		lblInventory.setPosition(Gdx.graphics.getWidth() - lblInventory.getPrefWidth() , Gdx.graphics.getHeight() - 100f);

		btnEndTurn = new TextButton("End Turn", skin, "default");      
		btnEndTurn.setWidth(200f);
		btnEndTurn.setHeight(20f);
		btnEndTurn.setPosition(Gdx.graphics.getWidth() - 200f, 20f);

		stage.addActor(btnEndTurn);
		stage.addActor(lblPlayer);
		stage.addActor(lblInventory);
	}

	public void addToInventory(Player p, Train t){
		InventoryTrainButton btn = new InventoryTrainButton(t.type.toString(), skin, inventoryItems.size(), p.inventory);
		btn.setPosition(Gdx.graphics.getWidth() - lblInventory.getPrefWidth(), Gdx.graphics.getHeight() - 150f - (inventoryItems.size() * 40f));
		inventoryItems.add(btn);
		
		

		

		stage.addActor(btn);
	}

	public void clearInventory(){
		for (InventoryTrainButton l : inventoryItems){
			l.remove();
		}
		inventoryItems.clear();
	}



}
