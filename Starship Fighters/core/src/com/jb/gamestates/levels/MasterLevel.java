package com.jb.gamestates.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gamestates.PlayState;

public abstract class MasterLevel {
	
	// Level Game Objects
	protected Array<GameObjects> enemyList;
	protected Array<Explosion> explosionList;
	protected Array<EnemyBullets> enemyBulletList;
	protected PlayState playState;
	protected int levelNumber;
	protected long timeSinceLevelBegan;

	public MasterLevel(Array<GameObjects> enemyList, Array<Explosion> explosionList, Array<EnemyBullets> enemyBulletList, int levelNumber, PlayState playState) {
		this.playState = playState;
	}
	
	public abstract void update(float dt);
	public abstract void draw(SpriteBatch spriteBatch);
	public abstract void dispose();
	
	// Setters + Getters
	
	public Array<GameObjects> getEnemylist(){
		return enemyList;
	}
	
	public Array<Explosion> getExplosionList(){
		return explosionList;
	}
	
	public int getLevelNumber() {
		return levelNumber;
	}
	
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public PlayState getPlayState() {
		return playState;
	}

}
