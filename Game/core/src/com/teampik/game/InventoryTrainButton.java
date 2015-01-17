package com.teampik.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InventoryButton extends TextButton{
	int index;
	Inventory inventory;

	public InventoryButton(String text, Skin skin, int index, Inventory inventory) {
		super(text, skin);
		this.index = index;
		this.inventory = inventory;
		
		addListener(new ClickListener(){
			@Override 
			public void clicked(InputEvent event, float x, float y){
				onClick();
			}
		});
	}
	
	public void onClick(){
		
		inventory.selectedTrain = inventory.trains.get(index);
		
		System.out.println(index + " selected in inventory");
	}

}
