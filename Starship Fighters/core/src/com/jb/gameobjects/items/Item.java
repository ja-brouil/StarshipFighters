package com.jb.gameobjects.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;
import com.jb.gamestates.PlayState;

public abstract class Item extends GameObjects {
	
	// Gameplay + Graphics
	protected PlayState playState;
	protected Animator itemAnimation;
	protected String animationPathName;
	protected String soundPathName;
	protected String soundName;
	protected Texture itemTexture;
	protected boolean remove;
	
	public Item(float x, float y, float dx, float dy, PlayState playState, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);
		
	}

	@Override
	public void update(float dt) {}

	@Override
	public void draw(SpriteBatch spriteBatch) {}

	public boolean getRemovalStatus() {
		return remove;
	}
	
	public abstract void dispose();
	
}
