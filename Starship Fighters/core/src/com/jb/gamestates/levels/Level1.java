package com.jb.gamestates.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.HUD.HealthBar;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.SamusShipBoss;
import com.jb.gamestates.PlayState;
import com.jb.images.Background;
import com.jb.main.Game;

public class Level1 extends MasterLevel {
	
	// Might want to rewrite this using JSON

	// Background
	private Background level1Background, level1background2;

	// Time
	private long timer = 3000;
	private long cooldownBetweenWaves = 2000;
	private long waveTimer;
	private boolean updateTimerEnabled = true;
	private long bossTimer = 5000;
	private boolean startTimerForBoss = true;

	// Music and Sounds
	private MusicManager musicManager;
	private String level1MusicPathName = "data/audio/music/level1.mp3";
	private String level1Music = "Level 1 Music";
	private String victoryMusicPathName = "data/audio/music/victorytheme.mp3";
	private String victoryMusicName = "Victory";
	

	// GamePlay
	// Boolean Switches
	private boolean[] gameplaySwitch;
	private int switchCounter;
	private int enemyWavesCounter = 0;
	private boolean bossSpawnEnabled = true;
	private SamusShipBoss samusShipBoss;

	public Level1(Array<GameObjects> enemyList, int levelNumber, PlayState playState) {
		super(enemyList, playState.getExplosionList(), playState.getEnemyBulletList(), levelNumber, playState);

		// Get lists
		this.enemyList = enemyList;
		this.levelNumber = levelNumber;
		this.playState = playState;

		// Start Background
		initializeBackground();

		// Update Timer
		timeSinceLevelBegan = TimeUtils.millis();
		waveTimer = TimeUtils.millis();

		// Initialize the switches
		gameplaySwitch = new boolean[20];
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
		
		musicManager = playState.getGSM().getGame().getMusicManager();
		musicManager.addMusic(level1MusicPathName, level1Music);
		musicManager.loopMusic(level1Music);
		musicManager.addMusic(victoryMusicPathName, victoryMusicName);
	}
	
	// Stop Music
	public void stopMusic() {
		musicManager.stopMusic(level1Music);
		musicManager.stopMusic(victoryMusicName);
	}

	@Override
	public void update(float dt) {

		// stop Music if game is over
		if (((HealthBar) playState.getHUD(0)).getHealthLeft() <= 0) {
			musicManager.stopMusic(level1Music);
			musicManager.stopMusic(victoryMusicName);
		}
		
		// Update the background
		level1Background.update(dt);
		level1Background.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		level1background2.update(dt);
		level1background2.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		
		
		// GamePlay Checks
		// Start Level 1 after 3 seconds have passed or we have reached the 5 fifth wave
		if (TimeUtils.timeSinceMillis(timeSinceLevelBegan) < timer) {
			return;
		}

		
		// Spawn Enemies
		if (enemyWavesCounter < 20) {
			// Spawn Enemies
			if (gameplaySwitch[switchCounter]) {
				if (switchCounter == 0) {
					addInitalEnemies();
				} else if (switchCounter == 1 && TimeUtils.timeSinceMillis(waveTimer) > cooldownBetweenWaves) {
					addSecondEnemies();
				} else if (switchCounter == 2 && TimeUtils.timeSinceMillis(waveTimer) > cooldownBetweenWaves) {
					addThirdEnemies();
				} else if (switchCounter == 3 && TimeUtils.timeSinceMillis(waveTimer) > cooldownBetweenWaves) {
					addFourthEnemies();
				} else if (switchCounter == 4 && TimeUtils.timeSinceMillis(waveTimer) > cooldownBetweenWaves) {
					addFifthEnemies();
				} else if (enemyList.size == 0 && TimeUtils.timeSinceMillis(waveTimer) > cooldownBetweenWaves) {
					randomSpawn();
				}
				
				gameplaySwitch[switchCounter] = false;
			}
		}

		// Check if enemies are dead | For first 5 spawns
		if (enemyList.size == 0) {
			// Update cooldown
			if (updateTimerEnabled) {
				waveTimer = TimeUtils.millis();
				updateTimerEnabled = false;
			}
			// Switch Level
			if (!gameplaySwitch[switchCounter]) {
				gameplaySwitch[switchCounter] = true;
			}
		}

		
		// Check if Boss needs to be spawned
		if (enemyWavesCounter == 20 && bossSpawnEnabled && enemyList.size == 0) {
			if (startTimerForBoss) {
				bossTimer = TimeUtils.millis();
				startTimerForBoss = false;
				musicManager.stopMusic(level1Music);
			}
			
			if (TimeUtils.timeSinceMillis(bossTimer) > 3000) {
				bossSpawnEnabled = false;
				spawnBoss();
			}	
		}
		
		// If Boss is dead, victory!
		if (getPlayState().getBoss() != null) {
			if (getPlayState().getBoss().getDeathStatus()) {
				musicManager.stopMusic("Boss Battle");
				musicManager.playMusic(victoryMusicName);
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

	// Enemy Spawns
	// Add Initial Enemies
	private void addInitalEnemies() {
		playState.getBasicAliens().add(new BasicAlien(568, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(468, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(140, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(40, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		switchCounter++;
		enemyWavesCounter++;
		updateTimerEnabled = true;
	}

	private void addSecondEnemies() {
		playState.getBasicAliens().add(new BasicAlien(100, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(250, 950, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(350, 850, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(400, 800, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		switchCounter++;
		enemyWavesCounter++;
		updateTimerEnabled = true;
	}

	private void addThirdEnemies() {
		playState.getBasicAliens().add(new BasicAlien(500, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(550, 950, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(450, 850, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(400, 800, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		switchCounter++;
		enemyWavesCounter++;
		updateTimerEnabled = true;
	}

	private void addFourthEnemies() {
		playState.getBasicAliens().add(new BasicAlien(500, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(550, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(450, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(300, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(200, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(100, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		switchCounter++;
		enemyWavesCounter++;
		updateTimerEnabled = true;
	}

	private void addFifthEnemies() {
		playState.getBasicAliens().add(new BasicAlien(600, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(500, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(400, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(300, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(200, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		playState.getBasicAliens().add(new BasicAlien(100, 900, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
		switchCounter++;
		enemyWavesCounter++;
		updateTimerEnabled = true;
	}

	private void randomSpawn() {
		for (int i = 0; i < MathUtils.random(5, 7); i++) {
			playState.getBasicAliens().add(new BasicAlien(75 + (i * 75), 925 - (MathUtils.random(0, 2) * 50), 0, -5, 1000L, -15,
					playState.getEnemyBulletList(), -20, playState));
		}
		enemyWavesCounter++;
		updateTimerEnabled = true;
	}
	
	// Boss 
	private void spawnBoss() {
		samusShipBoss = new SamusShipBoss(100, 975, 2, -2, playState.getEnemyBulletList(), playState.getExplosionList(), playState.getBasicAliens(), playState);
		getPlayState().setNewBoss(samusShipBoss); 
	}
	
	// Dispose of all resources
	@Override
	public void dispose() {
		level1Background.dispose();
		level1background2.dispose();
		musicManager.disposeAllMusic();
	}
}
