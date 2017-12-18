package com.jb.gamestates.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gamestates.PlayState;
import com.jb.images.Background;
import com.jb.main.Game;

public class Level1 extends MasterLevel {

	// Background
	private Background level1Background, level1background2;

	// Time
	private long timer = 3000;

	// Music and Sounds
	private String level1MusicPathName = "data/audio/music/Level1Music.mp3";
	private String level1Music = "Level 1 Music";

	// GamePlay
	// Boolean Switches
	private boolean[] gameplaySwitch;
	private int switchCounter;

	public Level1(Array<GameObjects> enemyList, Array<Explosion> explosionList, Array<EnemyBullets> enemyBulletList,
			int levelNumber, PlayState playState) {
		super(enemyList, explosionList, enemyBulletList, levelNumber, playState);

		// Get lists
		this.enemyList = enemyList;
		this.explosionList = explosionList;
		this.enemyBulletList = enemyBulletList;
		this.levelNumber = levelNumber;
		this.playState = playState;

		// Start Background
		initializeBackground();

		// Update Timer
		timeSinceLevelBegan = TimeUtils.millis();

		// Initialize the switches
		gameplaySwitch = new boolean[10];
		for (int i = 0; i < gameplaySwitch.length; i++) {
			gameplaySwitch[i] = false;
		}
		gameplaySwitch[0] = true;
		switchCounter = 0;

		// Start Music
		startMusic();

	}

	// Background load
	private void initializeBackground() {

		level1Background = new Background(0, 0, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg");
		level1background2 = new Background(0, -800, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg");

	}

	// Start Music
	private void startMusic() {
		MusicManager.addMusic(level1MusicPathName, level1Music);
		MusicManager.loopMusic(level1Music);
	}

	@Override
	public void update(float dt) {

		// Update the background
		level1Background.update(dt);
		level1Background.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		level1background2.update(dt);
		level1background2.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		
		// Gameplay Checks
		// Start Level 1 after 3 seconds have passed or we have reached the 5 fifth wave
		if (TimeUtils.timeSinceMillis(timeSinceLevelBegan) < timer || switchCounter == 5) {
			return;
		}

		// 0
		if (gameplaySwitch[switchCounter]) {
			addInitalEnemies();
			gameplaySwitch[switchCounter] = false;
			switchCounter++;
		}

		// 1
		if (gameplaySwitch[switchCounter]) {
			addSecondEnemies();
			gameplaySwitch[switchCounter] = false;
			switchCounter++;

		}

		// 2
		if (gameplaySwitch[switchCounter]) {
			addThirdEnemies();
			gameplaySwitch[switchCounter] = false;
			switchCounter++;

		}

		// 3
		if (gameplaySwitch[switchCounter]) {
			addFourthEnemies();
			gameplaySwitch[switchCounter] = false;
			switchCounter++;

		}

		// 4
		if (gameplaySwitch[switchCounter]) {
			addInitalEnemies();
			gameplaySwitch[switchCounter] = false;
			switchCounter++;
		}

		// Check if enemies are dead
		if (enemyList.size == 0) {
			if (!gameplaySwitch[switchCounter]) {
				gameplaySwitch[switchCounter] = true;
			}
		}
	}

	// Draw Extra stuff if needed
	@Override
	public void draw(SpriteBatch spriteBatch) {
		// Draw Background
		level1Background.draw(spriteBatch);
		level1background2.drawInverted(spriteBatch, false, true);
	}

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
