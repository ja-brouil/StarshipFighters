package com.jb.gameobjects.enemies.level2enemies;

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
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.enemies.level1enemies.EnemyBullets;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gamestates.PlayState;
import com.jb.main.Game;

public class BasicAlien2 extends GameObjects {

	// Graphics
	private TextureRegion[] sprite;

	// Physics
	private float maxSpeed;
	private float minimumSpeed;

	// GamePlay
	private Array<EnemyBullets> listofEnemyBullets;
	private Array<Explosion> listOfExplosion;
	private float enemyBulletSpeed;
	private long bulletCooldown;
	private long randomAttackCooldown;
	private float initialX, initialY;
	private int dropChance;
	private boolean stopDownwardMovement = false;

	// Get Arrays
	private PlayState playState;

	// Standard Constructor
	public BasicAlien2(float x, float y, float dx, float dy, long bulletCooldown, float bulletShootSpeed,
			int damageValue, AssetManager assetManager, PlayState playState) {
		super(x, y, dx, dy, assetManager);

		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.bulletCooldown = bulletCooldown;
		this.enemyBulletSpeed = bulletShootSpeed;
		this.damageValue = damageValue;
		this.assetManager = assetManager;
		this.playState = playState;
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
		sprite = new TextureRegion[3];
		for (int i = 0; i < sprite.length; i++) {
			sprite[i] = tmp[0][i];
		}

		// Get Bullet list
		listofEnemyBullets = playState.getEnemyBulletList();
		listOfExplosion = playState.getExplosionList();

		// Start rectangle
		collisionBounds = new Rectangle(x, y, allTexture.getWidth() / 3, allTexture.getHeight());

		// Active Status
		isActive = false;
	}

	// Update
	public void update(float dt) {

		// Set to active if within camera
		if (y - (playState.getGSM().getGame().getCamera().position.y * 2) < 100) {
			isActive = true;
		} else {
			return;
		}

		// Check if dead
		if (healthbar <= 0) {
			isDead = true;
			dropItems();
			listOfExplosion.add(new Explosion(this.x, this.y, 0, 0, playState, assetManager));
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
		if (TimeUtils.timeSinceMillis(bulletCooldown) > randomAttackCooldown && x < 0 && y > 0 && x > Game.WIDTH - 32
				&& y < playState.getGSM().getGame().getCamera().position.y + (Game.HEIGHT / 2) - 32) {
			addEnemyBullets(16, 0);
			randomAttackCooldown = MathUtils.random(1000, 2000);
		}
	}

	// Draw Enemies
	public void draw(SpriteBatch spriteBatch) {

		if (!isActive) {
			return;
		}

		// Draw Enemy
		spriteBatch.draw(sprite[0].getTexture(), x, y, 32, 33, 0, 0, 32, 33, false, true);
	}

	// Drop Enemies down if they are above the screen
	private void moveDownward() {
		if ((playState.getGSM().getGame().getCamera().position.y) - y > 50 && !stopDownwardMovement) {
			setVelX(4);
			setVelY(0.3f);
			stopDownwardMovement = true;
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

	// Add Bullets from enemies
	private void addEnemyBullets(int xOffset, int yOffset) {
		listofEnemyBullets.add(new EnemyBullets(getX() + xOffset, getY() + yOffset, 0, enemyBulletSpeed, damageValue,
				playState, assetManager));
		assetManager.get("data/audio/sound/bombLaunching.wav", Sound.class).play(1.0f);
		bulletCooldown = TimeUtils.millis();

	}

	// Drop Chance
	private void dropItems() {
		if (isDead) {
			if (dropChance == 0 && healthbar != -1) {
				playState.getAllItems().add(new EnergyTank(this.x, this.y, 0, -2, assetManager));
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
