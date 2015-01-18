package com.teampik.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class InGameUI{

	Skin skin;
	Stage stage;

	Table table;
	Table rootTable;
	Label lblPlayer;
	Label lblInventory;
	Label lblPowerUp;
	TextButton btnEndTurn;
	InventoryTrainButton btnTrain0;
	InventoryTrainButton btnTrain1;
	InventoryTrainButton btnTrain2;
	TextButton btnPowerup1;

	ArrayList<InventoryTrainButton> inventoryItems;
	ArrayList<Label> goalList;
 
	public InGameUI(){
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();

		inventoryItems = new ArrayList<InventoryTrainButton>();
		goalList = new ArrayList<Label>();
		
		lblPlayer = new Label("", skin);
		//lblPlayer.setPosition(Gdx.graphics.getWidth() - 300f, Gdx.graphics.getHeight() - 20f);
		//lblPlayer.setWidth(200f);
		//lblPlayer.setHeight(20f);

		lblInventory = new Label("Inventory", skin);
		lblPowerUp = new Label("Power-ups", skin);
		Label lblGoals = new Label("Goals",skin);
		Label lblTrains = new Label("Trains",skin);
		//lblInventory.setPosition(Gdx.graphics.getWidth() - lblInventory.getPrefWidth() , Gdx.graphics.getHeight() - 100f);

		btnEndTurn = new TextButton("End Turn", skin, "default");      
		btnEndTurn.setWidth(200f);
		btnEndTurn.setHeight(20f);
		btnEndTurn.setPosition(Gdx.graphics.getWidth() - 200f, 20f);
		
		//Pixmap with one pixel
	    Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
	    pm1.setColor(Color.GRAY);
	    pm1.fill();
		
	    TextButton tmp = new TextButton("End Turn", skin, "default"); 
	    btnTrain0 = new InventoryTrainButton("               ", skin, 0, null);  
		btnTrain1 = new InventoryTrainButton("               ", skin, 1, null); 
		btnTrain2 = new InventoryTrainButton("               ", skin, 2, null); 
	    TextButton btnPowerup1 = new TextButton("p1", skin, "default");  
	    TextButton btnPowerup2 = new TextButton("p2", skin, "default");  
	    TextButton btnPowerup3 = new TextButton("p3", skin, "default");  
	    TextButton btnPowerup4 = new TextButton("p4", skin, "default");  
	    Label lblGoal1 = new Label("g1",skin);
	    Label lblGoal2 = new Label("g2",skin);
	    Label lblGoal3 = new Label("g3",skin);
	    
	    Table table = new Table();
	    table.row().height(40);
	    table.add(lblPlayer).height(60);
	    table.row();
	    table.add(lblGoals);
	    table.row();    
	    table.add(lblGoal1);    
	    table.row();
	    table.add(lblGoal2);    
	    table.row();
	    table.add(lblGoal3);    
	    table.row();
	    table.add(lblInventory);
	    table.row();            
	    table.add(lblTrains);
	    table.row();   
	    table.add(btnTrain0);    
	    table.row();
	    table.add(btnTrain1);
	    table.row();
	    table.add(btnTrain2); 
	    table.row();                      
	    table.add(lblPowerUp);   
	    table.row();        
	    table.add(btnPowerup1); 
	    table.row();
	    table.add(btnPowerup2); 
	    table.row();
	    table.add(btnPowerup3); 
	    table.row();
	    table.add(btnPowerup4); 
	    table.row();
	    table.add(btnEndTurn);
	    table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
	    
		table.setColor(35, 200, 250, 50);
	    table.center().top();
	    Table rootTable = new Table();
	    rootTable.add(table).width(200);
	    rootTable.right().top();
	    rootTable.setFillParent(true);
	    stage.addActor(rootTable);
	    table.setDebug(true);
	    rootTable.setDebug(true);
	    
//		stage.addActor(btnEndTurn);
		//stage.addActor(lblPlayer);
		//stage.addActor(lblInventory);
	}

	public void addToInventory(Player currentPlayer){
		
		if (currentPlayer.inventory.trains.size() > 0){
			btnTrain0 = new InventoryTrainButton(currentPlayer.inventory.trains.get(0).type.toString(), skin, 0, currentPlayer.inventory);
			//rootTable.add(btnTrain0);
			System.out.println(currentPlayer.inventory.trains.get(0).type.toString());
			System.out.println(btnTrain0.getText());
		}
		if (currentPlayer.inventory.trains.size() > 1){
			btnTrain1 = new InventoryTrainButton(currentPlayer.inventory.trains.get(1).type.toString(), skin, 1, currentPlayer.inventory);
			System.out.println("inventory refreshed > 1");
			System.out.println(currentPlayer.inventory.trains.get(1).type.toString());
		}
		if (currentPlayer.inventory.trains.size() > 2){
			btnTrain2 = new InventoryTrainButton(currentPlayer.inventory.trains.get(2).type.toString(), skin, 2, currentPlayer.inventory);
			System.out.println("inventory refreshed > 2");
			System.out.println(currentPlayer.inventory.trains.get(2).type.toString());
		}
		/*
		InventoryTrainButton btn = new InventoryTrainButton(t.type.toString(), skin, inventoryItems.size(), p.inventory);
		btn.setPosition(Gdx.graphics.getWidth() - lblInventory.getPrefWidth(), Gdx.graphics.getHeight() - 150f - (inventoryItems.size() * 40f));
		inventoryItems.add(btn);

		stage.addActor(btn);*/
	}

	public void clearInventory(){
		for (InventoryTrainButton l : inventoryItems){
			l.remove();
		}
		inventoryItems.clear();
	}
	
	public void addToGoals(Player p, Goal g){
		Label label = new Label(g.toString(), skin);
		label.setPosition(0, Gdx.graphics.getHeight() - 20f - (goalList.size()*40f));
		goalList.add(label);
		stage.addActor(label);
		
	}
	
	public void clearGoal(){
		for (Label l : goalList){
			l.remove();
		}
		goalList.clear();
	}


}
