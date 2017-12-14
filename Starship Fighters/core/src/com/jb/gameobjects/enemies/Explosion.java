package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;

public class Explosion extends GameObjects {
	
	// Graphics
	private String pathName;
	private TextureRegion[] explosionTexture;
	private Animator explosionAnimation;
	private Texture allTexture;

	public Explosion(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		
		// Pathname for graphics
		pathName = "data/hit_and_explosions/";
		
		// Start Explosion
		init();
		
	}
	
	// Initialize
	private void init() {
		allTexture = new Texture(Gdx.files.internal(pathName));
		TextureRegion tmp[][] = TextureRegion.split(allTexture, allTexture.getWidth() / 4, allTexture.getHeight() / 2);
		//for (int i = 0; i < )
	}
	
	// Draw Explosion
	public void draw(SpriteBatch spriteBatch) {
		
	}

}
