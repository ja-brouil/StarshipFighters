package com.jb.assetmanagers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ImageManager {
	
	private HashMap<String, Texture> textureDatabase;

	public ImageManager() {
		textureDatabase = new HashMap<String, Texture>();
	}
	
	public void loadTexture(String pathname, String key) {
		Texture image = new Texture(Gdx.files.internal(pathname));
		textureDatabase.put(key, image);
	}
	
	public Texture getTexture(String key) {
		return textureDatabase.get(key);
	}
	
	public void disposeTexture(String key) {
		Texture texture = textureDatabase.get(key);
		if (texture != null) {
			texture.dispose();
		}
	}
	
	public void removeTexture(String key) {
		Texture texture = textureDatabase.get(key);
		if (texture != null) {
			textureDatabase.remove(key);
		}
	}
	
	 

}
