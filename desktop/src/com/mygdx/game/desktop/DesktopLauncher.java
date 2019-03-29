package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
		config.width = 880;
		config.height = 640;
		config.vSyncEnabled = true; // Setting to false disables vertical sync
		config.title = MyGdxGame.TITLE + " v" + MyGdxGame.VERSION;
		new LwjglApplication(new MyGdxGame(), config);
		System.out.println("hello");
		System.out.println("COMMIT_vol2");
		System.out.println("Hej jesper2");
	}
}
