package com.jb.gameobjects.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gamestates.PlayState;
import com.jb.level.level1.Level1;
import com.jb.main.Game;

public class BasicAlien extends GameObjects {
	
	// Graphics
	private TextureRegion[] rolls;
	
	// Sound
	private Sound bulletSound;

	// Physics
	private float maxSpeed;
	private float minimumSpeed;

	// GamePlay
	private Array<EnemyBullets> listofEnemyBullets;
	private float enemyBulletSpeed;
	private long bulletCooldown;
	private long randomAttackCooldown;
	private float initialX, initialY;
	private int damageValue;
	private int dropChance;

	// Get Arrays
	private PlayState playState;
	private Level1 level1;


	// Standard Constructor
	public BasicAlien(float x, float y, float dx, float dy, long bulletCooldown, float bulletShootSpeed,
			int damageValue, Level1 level1, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);

		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.bulletCooldown = bulletCooldown;
		this.enemyBulletSpeed = bulletShootSpeed;
		this.damageValue = damageValue;
		this.level1 = level1;
		this.assetManager = assetManager;
		listofEnemyBullets = level1.getEnemyBulletList();
		healthbar = 100;
		isDead = false;

		// GamePlay Starting Positions
		initialX = x;
		initialY = y;

		// Limits
		maxSpeed = 10;
		minimumSpeed = maxSpeed * -1;

		// Load Initial enemy data
		init();
	}

	// Initial load
	private void init() {


		// Start Shooting
		bulletCooldown = TimeUtils.millis();
		enemyBulletSpeed = 5;
		randomAttackCooldown = MathUtils.random(200, 1000);
		
		// Drop Chance | 10% chance
		dropChance = MathUtils.random(0, 9);

		// Get Sprites
		Texture allTexture = assetManager.get("data/spaceships/BasicEnemy.png", Texture.class);
		TextureRegion[][] tmp = TextureRegion.split(allTexture, allTexture.getWidth() / 3, allTexture.getHeight() / 1);
		rolls = new TextureRegion[3];
		for (int i = 0; i < rolls.length; i++) {
			rolls[i] = tmp[0][i];
		}

		// Start rectangle
		collisionBounds = new Rectangle(x, y, allTexture.getWidth() / 3, allTexture.getHeight());
	}


	// Update
	public void update(float dt) {
		
		// Check if dead
		if (healthbar <= 0) {
			isDead = true;
			return;
		}

		// Update Movement
		x += dx;
		y += dy;
		collisionBounds.set(x, y, (96f / 3f), 33);

		// Movement Limits
		moveDownward();
		setLimits();

		// Wraps
		wrapXBound();

		// Update Shoot | Don't shoot if out of the screen
		if (TimeUtils.timeSinceMillis(bulletCooldown) > randomAttackCooldown && x < Game.WIDTH && x > 0
				&& y < Game.HEIGHT && y > 0) {
			addEnemyBullets(16, 0);
			randomAttackCooldown = MathUtils.random(1000, 2000);
		}
		
		// Drop Items
		dropItems();

	}

	// Draw Enemies
	public void draw(SpriteBatch spriteBatch) {
		// Draw Enemy
		spriteBatch.draw(rolls[0].getTexture(), x, y, 32, 33, 0, 0, 32, 33, false, true);
	}


	// Prevent out of bounds
	private void wrapXBound() {
		// Flip speed if reached the end of the screen
		if (x > (Game.WIDTH - 32)) {
			dx *= -1;
		}

		if (x < 0) {
			dx *= -1;
		}
	}


	// Drop Enemies down if they are above the screen
	private void moveDownward() {
		if (getY() <= getInitialY() - 200 && getDY() < 0) {
			setVelX(4);
			setVelY(0);
		}
	}

	// Set Speed Limits on Enemies
	private void setLimits() {

		if (dx > maxSpeed) {
			dx = maxSpeed;
		}

		if (dx < minimumSpeed) {
			dx = minimumSpeed;
		}
	}

	// Add Bullets from enemies
	private void addEnemyBullets(int xOffset, int yOffset) {
		listofEnemyBullets
				.add(new EnemyBullets(getX() + xOffset, getY() + yOffset, 0, enemyBulletSpeed, damageValue, playState, assetManager));
		bulletSound = assetManager.get("data/audio/sound/bombLaunching.wav", Sound.class);
		bulletSound.play(1.0f);
		bulletCooldown = TimeUtils.millis();
		
	}

	// Drop Chance
	private void dropItems() {
		if (isDead) {
			if (dropChance == 0) {
				level1.getEnergyTankList().add(new EnergyTank(x, y, 0, -2, playState, 50, assetManager));
			}
		}
	}
	
	// Get initial starting x and y
	public float getInitialX() {
		return initialX;
	}

	public float getInitialY() {
		return initialY;
	}
	
	public void setDrop(int dropChance) {
		this.dropChance = dropChance;
	}

}
