package com.jb.gamestates.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gamestates.PlayState;

public class Level1 extends MasterLevel{
	
	// Time
	private long timer = 3000;
	
	// GamePlay
	// Boolean Switches
	private boolean[] gameplaySwitch;
	private int switchCounter;

	public Level1(Array<GameObjects> enemyList, Array<Explosion> explosionList, Array<EnemyBullets> enemyBulletList, int levelNumber, PlayState playState) {
		super(enemyList, explosionList, enemyBulletList, levelNumber, playState);
		
		// Get lists
		this.enemyList = enemyList;
		this.explosionList = explosionList;
		this.enemyBulletList = enemyBulletList;
		this.levelNumber = levelNumber;
		this.playState = playState;
		
		// Update Timer
		timeSinceLevelBegan = TimeUtils.millis();
		
		// Initialize the switches
		gameplaySwitch = new boolean[10];
		for (int i = 0; i < gameplaySwitch.length; i++) {
			gameplaySwitch[i] = false;
		}
		gameplaySwitch[0] = true;
		switchCounter = 0;
	}
	

	@Override
	public void update(float dt) {
		
		// Start Level 1 after 3 seconds have passed
		// 0
		if (gameplaySwitch[switchCounter]) {
			if (TimeUtils.timeSinceMillis(timeSinceLevelBegan) > timer) {
				addInitalEnemies();
				gameplaySwitch[switchCounter] = false;
			}
			
			//1
		} else if (gameplaySwitch[switchCounter]) {
			addSecondEnemies();
			gameplaySwitch[switchCounter] = false;
			
			//2
		} else if (gameplaySwitch[switchCounter]) {
			addThirdEnemies();
			gameplaySwitch[switchCounter] = false;
			
			//3
		} else if (gameplaySwitch[switchCounter]) {
			addFourthEnemies();
			gameplaySwitch[switchCounter] = false;
			
			//4
		} else if (gameplaySwitch[switchCounter]) {
			addInitalEnemies();
			gameplaySwitch[switchCounter] = false;
		}
		
		// Check if enemies are dead
		if (enemyList.size == 0 && !gameplaySwitch[switchCounter]) {
			switchCounter++;
			gameplaySwitch[switchCounter] = true;
		}

	}
	


	@Override
	public void draw(SpriteBatch spriteBatch) {}

	
	// Add Initial Enemies
	private void addInitalEnemies() {
		enemyList.add(new BasicAlien(500, 700, 3, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(550, 750, 3, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(450, 650, 3, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(400, 600, 3, 0, 1000L, -15, enemyBulletList));
	}
	
	private void addSecondEnemies() {
		enemyList.add(new BasicAlien(500, 700, 4, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(550, 750, 4, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(450, 650, 4, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(400, 600, 4, 0, 1000L, -15, enemyBulletList));
	}
	
	private void addThirdEnemies() {
		enemyList.add(new BasicAlien(500, 700, 2, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(550, 750, 5, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(450, 650, 4, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(400, 600, 3, 0, 1000L, -15, enemyBulletList));
	}
	
	private void addFourthEnemies() {
		enemyList.add(new BasicAlien(500, 700, -1, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(550, 750, 2, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(450, 650, -3, 0, 1000L, -15, enemyBulletList));
		enemyList.add(new BasicAlien(400, 600, 4, 0, 1000L, -15, enemyBulletList));
	}

}
