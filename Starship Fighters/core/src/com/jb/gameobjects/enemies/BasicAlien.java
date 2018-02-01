package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
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
import com.jb.main.Game;

public class BasicAlien extends GameObjects {

	// Physics
	private float maxSpeed;
	private float minimumSpeed;

	// Graphics
	private String pathName = "data/spaceships/BasicEnemy.png";
	private TextureRegion[] rolls; // 0 = normal, 1 = left, 2 = right
	private Texture allTexture;

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


	// Standard Constructor
	public BasicAlien(float x, float y, float dx, float dy, long bulletCooldown, float bulletShootSpeed,
			Array<EnemyBullets> listOfEnemyBullets, int damageValue, PlayState playState) {
		super(x, y, dx, dy);

		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.bulletCooldown = bulletCooldown;
		this.enemyBulletSpeed = bulletShootSpeed;
		this.listofEnemyBullets = listOfEnemyBullets;
		this.damageValue = damageValue;
		healthbar = 100;
		this.playState = playState;

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

		// Start Sprites
		allTexture = new Texture(Gdx.files.internal(pathName));
		TextureRegion[][] tmp = TextureRegion.split(allTexture, allTexture.getWidth() / 3, allTexture.getHeight() / 1);
		rolls = new TextureRegion[3];
		for (int i = 0; i < rolls.length; i++) {
			rolls[i] = tmp[0][i];
		}

		// Start rectangle
		collisionBounds = new Rectangle(x, y, allTexture.getWidth() / 3, allTexture.getHeight());
	}

	// Update Enemy Status
	public void update(float dt) {
		wrapXBound();

	}

	public void update(float dt, boolean xWrap, boolean yWrap) {

		// Update Movement
		x += dx;
		y += dy;
		collisionBounds.set(x, y, (96f / 3f), 33);

		// Movement Limits
		moveDownward();
		setLimits();

		// Wraps
		if (xWrap) {
			wrapXBound();
		}

		if (yWrap) {
			wrapYBound();
		}

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

	private void wrapYBound() {
		if (y > (Game.HEIGHT - 32)) {
			dy *= -1;
		}

		if (y < 0) {
			dy *= -1;
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
				.add(new EnemyBullets(getX() + xOffset, getY() + yOffset, 0, enemyBulletSpeed, damageValue, playState));
		bulletCooldown = TimeUtils.millis();
	}

	// Drop Chance
	private void dropItems() {
		if (healthbar <= 0) {
			if (dropChance == 0) {
				playState.getItemList().add(new EnergyTank(x, y, 0, -2, playState, 50));
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
	
	// Dispose Method
	public void dispose() {
		allTexture.dispose();
	}

}
