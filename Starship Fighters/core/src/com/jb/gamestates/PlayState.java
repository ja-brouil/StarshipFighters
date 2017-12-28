package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.HUD.HUD;
import com.jb.HUD.HealthBar;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.enemies.SamusShipBoss;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gameobjects.items.Item;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.gamestates.levels.Level1;
import com.jb.gamestates.levels.MasterLevel;
import com.jb.input.GameKeys;

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

	// Level Objects
	private MasterLevel[] levelList;
	private int levelNumber;

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

		// Start Level
		levelList = new MasterLevel[5];
		levelList[0] = new Level1(basicAliens, 1, this);
		levelNumber = 0;

		// Start the HUD
		// 0 = Health Bar
		allHUDElements = new HUD[1];
		allHUDElements[0] = new HealthBar(10, 760, 200, 25, true);

		// Timers
		deathTimer = TimeUtils.millis();

	}

	@Override
	public void handleInput() {

		// Escape to quit to exit faster. Remember to remove this later!
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
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
		if (gameOver) {
			// Add Player Explosions
			if (!player.getDeathStatus()) {
				for (int i = 0; i < 10; i++) {
					explosionList.add(new Explosion(player.getX() + (MathUtils.random(0, 32)),
							player.getY() + MathUtils.random(0, 32), 0, 0));
				}
				player.setDeathStatus(true);
			}

			return;
		}

		// Check Input
		handleInput();

		// Update Level
		levelList[levelNumber].update(dt);

		// Update Player | Bullets | Missiles
		player.update(dt);

		for (int i = 0; i < shipBullets.size; i++) {
			shipBullets.get(i).update(dt);
			// Remove Bullets
			if (shipBullets.get(i).getRemovalStatus()) {
				shipBullets.removeIndex(i);
				i--;
			}
		}

		// Update Enemies
		for (int i = 0; i < basicAliens.size; i++) {
			basicAliens.get(i).update(dt, true, false);
			if (basicAliens.get(i).getHP() <= 0) {
				explosionList.add(new Explosion(basicAliens.get(i).getX(), basicAliens.get(i).getY(), 0, 0));
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
		
		// Check for Collisions | Check for collisions after everything has been updated
		checkCollisions();

		// Update HUD || This should be updated last as the collisions will reflect the change in HP/Ammo
		for (int i = 0; i < allHUDElements.length; i++) {
			allHUDElements[i].update(dt);
		}

	}

	// Collision Check
	private void checkCollisions() {
		
		// Check if bullet hit an enemy 
		// For the best responsiveness, have a check for both contains method and overlap
		for (int i = 0; i < shipBullets.size; i++) {
			for (int j = 0; j < basicAliens.size; j++) {
				if (shipBullets.get(i).getBoundingBox().contains(basicAliens.get(j).getBoundingBox())
						|| basicAliens.get(j).getBoundingBox().contains(shipBullets.get(i).getBoundingBox()) || shipBullets.get(i).getBoundingBox().overlaps(basicAliens.get(j).getBoundingBox())
						|| basicAliens.get(j).getBoundingBox().overlaps(shipBullets.get(i).getBoundingBox())) {
					basicAliens.get(j).setHP(shipBullets.get(i).getDamageValue(), false);
					shipBullets.get(i).removeBullets();
				}
			}
		}
		
		// Check if player hits Enemies on Screen
		for (int i = 0; i < basicAliens.size; i++ ) {
			if (basicAliens.get(i).getBoundingBox().contains(player.getBoundingBox()) || basicAliens.get(i).getBoundingBox().overlaps(player.getBoundingBox())){
				((BasicAlien) basicAliens.get(i)).setDrop(1);
				basicAliens.get(i).setHP(-1, true);
				HealthBar tempHP = (HealthBar) getHUD(0);
				tempHP.setHealthLeft(-tempHP.getTotalHealth() * 0.25f);
			}
		}
		

		// Check Collision for enemies hitting the player
		for (int i = 0; i < enemyBulletList.size; i++) {
			if (enemyBulletList.get(i).getBoundingBox().contains(player.getBoundingBox()) || player.getBoundingBox().contains(enemyBulletList.get(i).getBoundingBox())) {
				// Play Explosion on Player
				explosionList.add(new Explosion((player.getX() + MathUtils.random(0, 32)),
						player.getY() + MathUtils.random(0, 32), 0, 0, "data/hit_and_explosions/impactHit.png",
						"data/audio/sound/Bomb Explosion.wav", "Player Hit", 3, 1, 3, 1, 1f / 40f));

				// Reduce Player Health
				HealthBar tempHP = (HealthBar) getHUD(0);
				tempHP.setHealthLeft(enemyBulletList.get(i).getDamageValue());

				// Remove Bullet
				enemyBulletList.get(i).removeBullets();

			}
		}

		// Check for Items colliding with player
		for (int i = 0; i < itemList.size; i++) {
			if (itemList.get(i).getBoundingBox().overlaps(player.getBoundingBox()) || player.getBoundingBox().overlaps(itemList.get(i).getBoundingBox())) {
				EnergyTank tmpEnergyTank = (EnergyTank) itemList.get(i);
				HealthBar tempBar = (HealthBar) getHUD(0);
				tempBar.setHealthLeft(tmpEnergyTank.getHealthRegenValue());
				itemList.removeIndex(i);
				i--;
			}
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

		// Draw Background
		levelList[levelNumber].draw(spriteBatch);
		spriteBatch.end();

		// Draw HUD
		// NOTICE: THIS SHOULD NORMALLY BE UNDER THE SPRITEBATCH BEGIN BUT I DONT HAVE
		// CUSTOM HUD ELEMENTS YET
		for (int i = 0; i < allHUDElements.length; i++) {
			allHUDElements[i].draw(spriteBatch);
		}

		// Player Render
		spriteBatch.begin();
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
			if (player.getDeathStatus()) {
				if (TimeUtils.timeSinceMillis(deathTimer) > 200) {
					explosionList.get(i).draw(spriteBatch);
					deathTimer = TimeUtils.millis();
				}
			} else {
				explosionList.get(i).draw(spriteBatch);
			}
		}

		// Close SpriteBatch and Shape Renderer
		spriteBatch.end();

	}

	@Override
	public void dispose() {
		SoundManager.disposeAllSound();
		MusicManager.disposeAllMusic();
	}

	public void setLevel(int levelNumber) {
		this.levelNumber = levelNumber;
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
