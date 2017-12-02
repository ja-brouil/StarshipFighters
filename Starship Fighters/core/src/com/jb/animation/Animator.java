package com.jb.animation;

import javax.xml.soap.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
	
	private Texture spriteSheet;
	private int frameCols, frameRows;
	private int spriteHeight, spriteWidth;
	private String filePath;
	private TextureRegion[] animationSprites;
	private Animation<TextureRegion> animationTextureRegion;

	
	public Animator(int frameCols, int frameRows, String filePath) {
	
		this.frameCols = frameCols;
		this.frameRows = frameRows;
		this.filePath = filePath;
		
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
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j++) {
				animationSprites[index++] = tmp[i][j];
			}
		}
		
		// Load animation frames
		animationTextureRegion = new Animation<TextureRegion>(10f, animationSprites);

		
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
