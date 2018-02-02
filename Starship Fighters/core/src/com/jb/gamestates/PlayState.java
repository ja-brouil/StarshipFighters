package com.jb.gamestates;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.HUD.HUD;
import com.jb.HUD.HealthBar;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.CollisionHandling;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.enemies.SamusShipBoss;
import com.jb.gameobjects.items.Item;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.gamestates.levels.Level1;
import com.jb.input.GameKeys;
import com.jb.main.Game;

public class PlayState extends GameState {

	// Game Objects
	private Player player;
	private Array<PlayerBullets> shipBullets;
	private Array<GameObjects> basicAliens;
	private Array<EnemyBullets> enemyBulletList;
	private Array<Explosion> explosionList;
	private SamusShipBoss samusShipBoss;
	private Array<Item> itemList;

	// GamePlay
	private boolean inputAllowed = true;
	private boolean gameOver = false;
	private CollisionHandling collisionHandling;
	
	// Audio
	private MusicManager musicManager;
	private SoundManager soundManager;

	// Level Objects
	private Level1 level1;

	// HUD Elements
	private HUD[] allHUDElements;

	// Timer
	private long deathTimer;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();

	}

	@Override
	public void init() {

		// Start Game Objects
		shipBullets = new Array<PlayerBullets>();
		enemyBulletList = new Array<EnemyBullets>();
		player = new Player(300, 150, 0, 0, shipBullets, this);
		basicAliens = new Array<GameObjects>();
		explosionList = new Array<Explosion>();
		itemList = new Array<Item>();
		collisionHandling = new CollisionHandling(this);

		// Start Level
		//level1 = new Level1("data/levels/testmap.tmx", this);

		// Start the HUD
		// 0 = Health Bar
		allHUDElements = new HUD[1];
		allHUDElements[0] = new HealthBar(10, 760, 200, 25, true);

		// Timers
		deathTimer = TimeUtils.millis();
		
		// Audio
		soundManager = getGSM().getGame().getSoundManager();
		musicManager = getGSM().getGame().getMusicManager();
		
		// DEBUG SECTION
		basicAliens.add(new BasicAlien(200, 850, 0, -5f, 1000, 3, enemyBulletList, 5, this));

	}

	@Override
	public void handleInput() {

		// Escape to quit to exit faster. Remember to remove this later!
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			dispose();
			Game.closeGame = true;
		}

		// Set Game Keys
		if (inputAllowed) {
			player.setLeft(GameKeys.isDown(GameKeys.LEFT));
			player.setRight(GameKeys.isDown(GameKeys.RIGHT));
			player.setUp(GameKeys.isDown(GameKeys.UP));
			player.setDown(GameKeys.isDown(GameKeys.DOWN));
			player.setShoot(GameKeys.isDown(GameKeys.SPACE));
			player.setMissile(GameKeys.isPressed(GameKeys.SHIFT));
		}

		// Update Keys
		GameKeys.update();
	}

	@Override
	public void update(float dt) {

		// If Game over, return/draw death animation
		/*if (gameOver) {
			// Add Player Explosions
			if (!player.getDeathStatus()) {
				for (int i = 0; i < 10; i++) {
					explosionList.add(new Explosion(player.getX() + (MathUtils.random(0, 32)),
							player.getY() + MathUtils.random(0, 32), 0, 0));
				}
				player.setDeathStatus(true);
			}

			return;
		}*/
		
		// Update Level
		//level1.update(dt);
		
		// Game Over
		if (((HealthBar) allHUDElements[0]).getHealthLeft() <= 0) {
			musicManager.disposeAllMusic();
			dispose();
			gsm.setState(GameStateManager.GAMEOVER);
		}

		// Check Input
		handleInput();

		// Update Player | Bullets | Missiles
		player.update(dt);

		for (int i = 0; i < shipBullets.size; i++) {
			shipBullets.get(i).update(dt);
			// Remove Bullets
			if (shipBullets.get(i).getRemovalStatus()) {
				shipBullets.get(i).dispose();
				shipBullets.removeIndex(i);
				i--;
			}
		}

		// Update Enemies
		for (int i = 0; i < basicAliens.size; i++) {
			basicAliens.get(i).update(dt, true, false);
			if (basicAliens.get(i).getHP() <= 0) {
				explosionList.add(new Explosion(basicAliens.get(i).getX(), basicAliens.get(i).getY(), 0, 0, this));
				((BasicAlien) basicAliens.get(i)).dispose();
				basicAliens.removeIndex(i);
				i--;
			}
		}

		// Update Boss | section needs to be rewritten with an array of bosses. Change
		// the basic aliens back
		if (samusShipBoss != null) {
			samusShipBoss.update(dt);
		}

		// Remove Explosions
		for (int i = 0; i < explosionList.size; i++) {
			explosionList.get(i).update(dt);
			// Remove Explosion
			if (explosionList.get(i).getExplosionStatus()) {
				explosionList.removeIndex(i);
				i--;
			}
		}

		// Enemy Bullets
		for (int i = 0; i < enemyBulletList.size; i++) {
			enemyBulletList.get(i).update(dt);
			// Remove Bullets
			if (enemyBulletList.get(i).getRemovalStatus()) {
				enemyBulletList.removeIndex(i);
				i--;
			}
		}

		// Update Items
		for (int i = 0; i < itemList.size; i++) {
			itemList.get(i).update(dt);
			if (itemList.get(i).getRemovalStatus()) {
				itemList.removeIndex(i);
				i--;
			}
		}
		
		// Collision/Physics Check
		collisionHandling.update(dt);

		// Update HUD || This should be updated last as the collisions will reflect the change in HP/Ammo
		for (int i = 0; i < allHUDElements.length; i++) {
			allHUDElements[i].update(dt);
		}
		
		// Close Game After Everything
		if (Game.closeGame) {
			Gdx.app.exit();
		}

	}


	@Override
	public void render() {
		// NOTICE: ORDER HERE IS IMPORTANT. THINK OF THIS AS LAYERS

		// Clear screen to Black Background
		// To use different color scales: modify the openGL state with c++
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		
		// Draw Level Elements (BackGround)
		//level1.render(spriteBatch);
		spriteBatch.end();

		// Draw HUD
		// NOTICE: THIS SHOULD NORMALLY BE UNDER THE SPRITEBATCH BEGIN BUT I DONT HAVE
		// CUSTOM HUD ELEMENTS YET
		spriteBatch.begin();
		for (int i = 0; i < allHUDElements.length; i++) {
			allHUDElements[i].draw(spriteBatch);
		}
		spriteBatch.end();
		spriteBatch.begin();
		
		
		
		// Player Render
		player.draw(spriteBatch);

		// Bullet Render
		for (int i = 0; i < shipBullets.size; i++) {
			shipBullets.get(i).draw(spriteBatch);
		}
		
		// Render Items
		for (int i = 0; i < itemList.size; i++) {
			itemList.get(i).draw(spriteBatch);
		}

		// Enemy Bullets
		for (int i = 0; i < enemyBulletList.size; i++) {
			enemyBulletList.get(i).draw(spriteBatch);
		}

		// Enemy Aliens
		for (int i = 0; i < basicAliens.size; i++) {
			basicAliens.get(i).draw(spriteBatch);
		}

		// Boss Units
		if (samusShipBoss != null) {
			samusShipBoss.draw(spriteBatch);
		}

		// Explosions | Hit Animations
		for (int i = 0; i < explosionList.size; i++) {
			explosionList.get(i).draw(spriteBatch);
		}

		// Close SpriteBatch and Shape Renderer
		spriteBatch.end();

	}

	@Override
	public void dispose() {
		
		// Dispose Level
		//level1.dispose();
		
		// Dispose Player
		player.dispose();
		
		// Dispose Bullets
		PlayerBullets tmPlayerBullets = new PlayerBullets(-1, -1, 0, 0);
		tmPlayerBullets.dispose();
		
		// Dispose Aliens
		BasicAlien tmpBasicAlien = new BasicAlien(0, 0, 0, 0, 0, 0, enemyBulletList, 0, this);
		tmpBasicAlien.dispose();
		
		// Dispose Enemy Bullets
		EnemyBullets tmpEnemyBullets = new EnemyBullets(0, 0, 0, 0, 0, this);
		tmpEnemyBullets.dispose();
		
		// Dispose Audio
		musicManager.disposeAllMusic();
		soundManager.disposeAllSound();	
	
	}


	public boolean getInputAllowed() {
		return inputAllowed;
	}

	public void setInputALlowed(boolean inputAllowed) {
		this.inputAllowed = inputAllowed;
	}

	// This should be array of bosses
	public SamusShipBoss getBoss() {
		return samusShipBoss;
	}

	public void setNewBoss(SamusShipBoss samusShipBoss) {
		this.samusShipBoss = samusShipBoss;
	}

	public GameStateManager getGSM() {
		return gsm;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Array<PlayerBullets> getShipBullets() {
		return shipBullets;
	}

	public Array<GameObjects> getBasicAliens() {
		return basicAliens;
	}

	public Array<EnemyBullets> getEnemyBulletList() {
		return enemyBulletList;
	}

	public Array<Explosion> getExplosionList() {
		return explosionList;
	}

	public Player getPlayer() {
		return player;
	}

	// Specific Array
	public HUD getHUD(int index) {
		return allHUDElements[index];
	}

	// Entire Array
	public HUD[] getArrayHUD() {
		return allHUDElements;
	}

	public Array<Item> getItemList() {
		return itemList;
	}
}
