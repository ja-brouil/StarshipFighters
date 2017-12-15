package com.jb.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jb.main.Game;

public class DesktopLauncher {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 800;
	public static String title = "Starship Fighters";
	
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = title;
		config.width = WIDTH;
		config.height = HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}
