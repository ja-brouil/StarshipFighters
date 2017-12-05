package com.jb.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
	
	private Texture spriteSheet;
	private int frameCols, frameRows, columnCutOff, rowCutOff;
	private int spriteHeight, spriteWidth;
	private String filePath;
	private TextureRegion[] animationSprites;
	private Animation<TextureRegion> animationTextureRegion;
	private float frameLengthTime;
	
	public Animator(int frameCols, int frameRows, String filePath, int columnCutOff, int rowCutOff, float frameLengthTime) {
	
		this.frameCols = frameCols;
		this.frameRows = frameRows;
		this.filePath = filePath;
		this.columnCutOff = columnCutOff;
		this.rowCutOff = rowCutOff;
		this.frameLengthTime = frameLengthTime;
		initialize();
	}
	
	
	public void initialize() {
		// Load SpriteSheet
		spriteSheet = new Texture(Gdx.files.internal(filePath));
		
		// Extract Sprites
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / frameCols, spriteSheet.getHeight() / frameRows);
		
		// Place into 1D array
		animationSprites = new TextureRegion[3];
		int index = 0;
		for (int i = 0; i < rowCutOff; i++) {
			for (int j = 0; j < columnCutOff; j++) {
				animationSprites[index++] = tmp[i][j];
			}
		}
		
		// Load animation frames
		animationTextureRegion = new Animation<TextureRegion>(frameLengthTime, animationSprites);
		
	}

	public Texture getSpriteSheet() {
		return spriteSheet;
	}

	public Animation<TextureRegion> getAnimationFrames(){
		return animationTextureRegion;
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
