package com.jb.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jb.main.Game;

public class DesktopLauncher {
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	public static String title = "Starship Fighters";
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = title;
		config.width = WIDTH * SCALE;
		config.height = HEIGHT * SCALE;
		new LwjglApplication(new Game(), config);
	}
}
