package com.jb.gameobjects.enemies.level1enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;

public class SamusShipBossHit extends GameObjects {
	
	// Graphic Variables
		private Animator bossHitAnimation;
		private String textureFilePath = "data/hit_and_explosions/impactHit.png";
		private boolean bossHitAnimationDone;
		private float animationTimer;
	
	public SamusShipBossHit(float x, float y, float dx, float dy, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);
		
		// Animation Variables
		bossHitAnimationDone = false;
		bossHitAnimation = new Animator(3, 1, textureFilePath, 3, 1, 1 / 40f, assetManager);
	}

	@Override
	public void update(float dt) {
		if (bossHitAnimation.getAnimationFrames().isAnimationFinished(animationTimer)) {
			bossHitAnimationDone = true;
		}
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		animationTimer += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(bossHitAnimation.getAnimationFrames().getKeyFrame(animationTimer, false), x, y);
	}
	
	public boolean isBossHitAnimationDone() {
		return bossHitAnimationDone;
	}
	

}
