package com.jb.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jb.main.GameMain;

public class DesktopLauncher {
	
	public static final int SCALE = 2;
	public static String title = "Starship Fighters";
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = title;
		config.width = 320 * SCALE;
		config.height = 240 * SCALE;
		new LwjglApplication(new GameMain(), config);
	}
}
