package com.jb.HUD;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class HUD {
	
	// Location of the HUD element
	protected float x,y; 
	// Draw HUD element
	protected boolean showHUDElement;

	public HUD(float x, float y, boolean showHUDElement) {
		
	}
	
	// Update and Render for the HUD
	public abstract void update(float dt);
	public abstract void draw(SpriteBatch spriteBatch);

}
