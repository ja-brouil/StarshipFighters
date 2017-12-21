package com.jb.gamestates;

import java.sql.Time;

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
		
		// Start Level
		levelList = new MasterLevel[5];
		levelList[0] = new Level1(basicAliens, explosionList, enemyBulletList, 1, this);
		levelNumber = 0;

		// Start the HUD
		// 0 = Health Bar |
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
					explosionList.add(new Explosion(player.getX() + (MathUtils.random(0, 32)), player.getY() + MathUtils.random(0, 32), 0, 0));
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
			}
		}

		// Update Enemies
		for (int i = 0; i < basicAliens.size; i++) {
			((BasicAlien) basicAliens.get(i)).update(dt, true, false);
			if (basicAliens.get(i).getHP() == 0) {
				explosionList.add(new Explosion(basicAliens.get(i).getX(), basicAliens.get(i).getY(), 0, 0));
				basicAliens.removeIndex(i);
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
			}
		}

		// Enemy Bullets
		for (int i = 0; i < enemyBulletList.size; i++) {
			enemyBulletList.get(i).update(dt);
			// Remove Bullets
			if (enemyBulletList.get(i).getRemovalStatus()) {
				enemyBulletList.removeIndex(i);
			}
		}

		// Check Collisions || Collisions should probably be added per class instead of
		// having an overall? Look into this
		checkCollision();

		// Update HUD if needed
		for (int i = 0; i < allHUDElements.length; i++) {
			allHUDElements[i].update(dt);
		}

	}

	// Collision and Bullets
	private void checkCollision() {
		// Check Player Bullets
		for (int i = 0; i < shipBullets.size; i++) {
			// Check aliens
			for (int j = 0; j < basicAliens.size; j++) {
				if (shipBullets.get(i).getBoundingBox().overlaps(basicAliens.get(j).getBoundingBox())) {
					shipBullets.get(i).removeBullets();
					basicAliens.get(j).setHP(shipBullets.get(i).getDamageValue(), false);
				}
			}

			// Check Boss Hit | 0 = left, 1 = center, 2 = right
			if (samusShipBoss != null) {
				for (int h = 0; h < samusShipBoss.getHitBoxes().length; h++) {
					if (shipBullets.get(i).getBoundingBox().overlaps(samusShipBoss.getSpecificHitBox(h))) {
						if (h == 0) {
							if (!samusShipBoss.getLeftWingStatus()) {
								samusShipBoss.setLeftWingHealth(shipBullets.get(i).getDamageValue());
							}
							explosionList.add(new Explosion(shipBullets.get(i).getX(), shipBullets.get(i).getY(), 0, 0,
									"data/hit_and_explosions/impactHit.png", "data/audio/sound/Bomb Explosion.wav",
									"Boss Hit", 3, 1, 3, 1, 1f / 40f, false));
							shipBullets.get(i).removeBullets();
						}
						if (h == 1) {
							if (!samusShipBoss.getMiddleInvincibleStatus()) {
								samusShipBoss.setHP(shipBullets.get(i).getDamageValue(), false);
							}
							shipBullets.get(i).removeBullets();
							explosionList.add(new Explosion(shipBullets.get(i).getX(), shipBullets.get(i).getY(), 0, 0,
									"data/hit_and_explosions/impactHit.png", "data/audio/sound/Bomb Explosion.wav",
									"Boss Hit", 3, 1, 3, 1, 1f / 40f, false));
						}
						if (h == 2) {
							if(!samusShipBoss.getRightWingStatus()) {
								samusShipBoss.setRightWingHealth(shipBullets.get(i).getDamageValue());
							}
							explosionList.add(new Explosion(samusShipBoss.getSpecificHitBox(h).getX(),
									shipBullets.get(i).getY(), 0, 0, "data/hit_and_explosions/impactHit.png",
									"data/audio/sound/Bomb Explosion.wav", "Boss Hit", 3, 1, 3, 1, 1f / 40f, false));
							shipBullets.get(i).removeBullets();
						}
					}
				}
			}

		}

		// Check for Enemy Bullets
		for (int i = 0; i < enemyBulletList.size; i++) {
			if (enemyBulletList.get(i).getBoundingBox().overlaps(player.getBoundingBox())) {
				// Play Explosion on Player
				explosionList.add(new Explosion(player.getX() + MathUtils.random(0, 32),
						player.getY() + MathUtils.random(0, 32), 0, 0, "data/hit_and_explosions/impactHit.png",
						"data/audio/sound/Bomb Explosion.wav", "Player Hit", 3, 1, 3, 1, 1f / 40f));

				// Reduce Player Health
				HealthBar tempHP = (HealthBar) allHUDElements[0];
				tempHP.setHealthLeft(enemyBulletList.get(i).getDamageValue());

				// Remove Bullet
				enemyBulletList.get(i).removeBullets();

			}
		}

	}

	@Override
	public void render() {

		// Clear screen to Black Background
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

}
