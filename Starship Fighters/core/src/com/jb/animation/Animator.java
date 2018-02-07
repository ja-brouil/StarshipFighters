package com.jb.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
	
	// Graphics
	private Texture spriteSheet;
	private int frameCols, frameRows, columnCutOff, rowCutOff;
	private int spriteHeight, spriteWidth;
	private String filePath;
	private TextureRegion[] animationSprites;
	
	// Animation Variables
	private Animation<TextureRegion> animationTextureRegion;
	private float frameLengthTime;
	
	// Asset Manager
	private AssetManager assetManager;
	
	public Animator(int frameCols, int frameRows, String filePath, int columnCutOff, int rowCutOff, float frameLengthTime, AssetManager assetManager) {
	
		this.frameCols = frameCols;
		this.frameRows = frameRows;
		this.filePath = filePath;
		this.columnCutOff = columnCutOff;
		this.rowCutOff = rowCutOff;
		this.frameLengthTime = frameLengthTime;
		this.assetManager = assetManager;
		this.filePath = filePath;
		initialize();
	}
	
	// Start Animation
	public void initialize() {

		// Load SpriteSheet
		if (!assetManager.isLoaded(filePath, Texture.class)) {
			assetManager.load(filePath, Texture.class);
			assetManager.finishLoading();
			spriteSheet = assetManager.get(filePath, Texture.class);
		} else {
			spriteSheet = assetManager.get(filePath, Texture.class);
		}
		
		
		// Extract Sprites
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / frameCols, spriteSheet.getHeight() / frameRows);
		
		// Place into 1D array
		animationSprites = new TextureRegion[columnCutOff * rowCutOff];
		int index = 0;
		for (int i = 0; i < rowCutOff; i++) {
			for (int j = 0; j < columnCutOff; j++) {
				animationSprites[index++] = tmp[i][j];
			}
		}
		
		// Load animation frames
		animationTextureRegion = new Animation<TextureRegion>(frameLengthTime, animationSprites);	
	}
	
	// Setters + Getters
	public Texture getSpriteSheet() {
		return spriteSheet;
	}
	
	public TextureRegion getTextureRegion(int value) {
		return animationSprites[value];
	}

	public Animation<TextureRegion> getAnimationFrames(){
		return animationTextureRegion;
	}
	
	public float getFrameLengthTime() {
		return frameLengthTime;
	}

	public int getFrameCols() {
		return frameCols;
	}


	public int getFrameRows() {
		return frameRows;
	}


	public int getSpriteHeight() {
		return spriteHeight;
	}


	public int getSpriteWidth() {
		return spriteWidth;
	}
	
	public String filePath() {
		return filePath;
	}

}
