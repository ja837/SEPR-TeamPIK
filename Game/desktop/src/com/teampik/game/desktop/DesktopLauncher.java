package com.teampik.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.teampik.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Team PIK";
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
