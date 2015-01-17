package com.teampik.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class InventoryButton extends TextButton{
	int index;

	public InventoryButton(String text, Skin skin, int index) {
		super(text, skin);
		this.index = index;
	}

}
