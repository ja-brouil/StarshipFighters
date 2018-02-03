package com.jb.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;
import com.jb.gamestates.PlayState;
import com.jb.main.Game;

public class Player extends GameObjects {

	// Physics
	private float maxSpeed;
	private float minimumSpeed;

	// Graphics
	private String pathname;
	private Animator shipAnimation;
	private float animationTime;
	private float animationFrameDuration;

	// Basic Bullet Graphics
	private String basicBulletPathname = "data/ammo/bulletfinal.png";
	private TextureRegion[] bulletTexture;

	// Asset Manager
	private AssetManager assetManager;

	// Sound Effects
	private String bulletShotSoundPathName;
	private Sound bulletSound;

	// GamePlay
	private Array<PlayerBullets> listOfBullets;
	private float bulletSpeed;
	private long bulletcooldown;
	private long bulletShootSpeed;
	private int healthPoints;
	private boolean isDead;
	private PlayState playState;

	public Player(float x, float y, float dx, float dy, PlayState playState, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);

		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.playState = playState;
		bulletcooldown = TimeUtils.millis();
		this.assetManager = assetManager;
		this.playState = playState;

		// Graphics
		pathname = "data/spaceships/ship1.png";
		animationFrameDuration = 1f / 40f;

		// SFX
		bulletShotSoundPathName = "data/audio/sound/Basic Shot.wav";

		// Limits
		maxSpeed = 4;
		// friction = 5;
		minimumSpeed = -4;
		bulletSpeed = 15;
		bulletShootSpeed = 200;

		// Start Player Loading
		init();

	}

	public void init() {

		// HealthPoints
		healthPoints = 100;
		isDead = false;

		// Note: Ship size is 64 x 64 pixels
		// Start Animation
		shipAnimation = new Animator(3, 8, pathname, 3, 1, animationFrameDuration, assetManager);

		// Start Rectangle Box
		collisionBounds = new Rectangle(x, y, 64, 64);

		// Load Sound
		assetManager.load(bulletShotSoundPathName, Sound.class);

		// Load Basic Bullet Art
		assetManager.load(basicBulletPathname, Texture.class);

		// Set Art and Sound Objects
		assetManager.finishLoading();
		bulletSound = assetManager.get(bulletShotSoundPathName, Sound.class);

		// Load Bullet Texture
		Texture tmpBulletTexture = assetManager.get(basicBulletPathname, Texture.class);
		TextureRegion[][] tmp = TextureRegion.split(tmpBulletTexture, tmpBulletTexture.getWidth() / 2,
				tmpBulletTexture.getHeight());
		bulletTexture = new TextureRegion[1];
		bulletTexture[0] = tmp[0][0];
		
		// Bullet Array List
		listOfBullets = new Array<PlayerBullets>();

	}

	// Key Input
	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setShoot(boolean b) {
		shoot = b;
	}

	public void setMissile(boolean b) {
		missile = b;
	}

	// Update Player Status
	@Override
	public void update(float dt) {
		
		// Check if dead
			// Code for dead
		

		// Player Input
		playerHandleInput(dt);

		// Set Limits
		setLimits();

		// Update Speed
		this.x += dx;
		this.y += dy;

		// Update Rectangle Bounds
		updateRectangle(x, y);

		// Edge of the screen move to the other side
		wrap();
		
		// Update Bullets
		for (int i = 0; i < listOfBullets.size; i++) {
			listOfBullets.get(i).update(dt);
			// Remove Bullets
			if (listOfBullets.get(i).getRemovalStatus()) {
				listOfBullets.removeIndex(i);
				i--;
			}
		}

	}

	// Render Player
	@Override
	public void draw(SpriteBatch spriteBatch) {

		// Draw Ship
		animationTime += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(shipAnimation.getAnimationFrames().getKeyFrame(animationTime, true), x, y);
		
		// Draw Bullets
		for (PlayerBullets playerBullets: listOfBullets) {
			playerBullets.draw(spriteBatch);
		}
	}

	// Prevent Out of Bounds
	private void wrap() {
		// Wrap Around for X Coordinate
		if (x > Game.WIDTH - 64) {
			x = Game.WIDTH - 64;
		}
		if (x < 0) {
			x = 0;
		}

		// Wrap Around for Y Coordinate
		if (y > Game.HEIGHT - 64) {
			y = Game.HEIGHT - 64;
		}
		if (y < 0) {
			y = 0;
		}

	}

	// Limits
	private void setLimits() {
		// Set Maximum Speed
		if (dx > maxSpeed) {
			dx = maxSpeed;
		}
		if (dx < minimumSpeed) {
			dx = minimumSpeed;
		}
		if (dy > maxSpeed) {
			dy = maxSpeed;
		}
		if (dy < minimumSpeed) {
			dy = minimumSpeed;
		}
	}

	// Handle input
	private void playerHandleInput(float dt) {
		// Left | Right | Up | Down
		if (left) {
			dx += -4;
		} else if (!left && !right) {
			dx = 0;
		}

		if (right) {
			dx += 4;
		} else if (!left && !right) {
			dx = 0;
		}

		if (up) {
			dy += 4;
		} else if (!up && !down) {
			dy = 0;
		}

		if (down) {
			dy += -4;
		} else if (!up && !down) {
			dy = 0;
		}

		// Shoot
		if (shoot) {
			if (TimeUtils.timeSinceMillis(bulletcooldown) > bulletShootSpeed) {
				addBullets(32, 64);
			}

		}
		// Missile
		if (missile) {
			// Code for shooting missiles
		}

	}

	// Shoot Code
	private void addBullets(int xOffset, int yOffset) {
		listOfBullets.add(new PlayerBullets(getX() + xOffset, getY() + yOffset, 0, bulletSpeed, assetManager, this));
		bulletSound.play(1.0f);
		bulletcooldown = TimeUtils.millis();
	}

	// Update Rectangle
	private void updateRectangle(float x, float y) {
		collisionBounds.set(x, y, 64, 64);
	}

	// Setters and Getters
	public Array<PlayerBullets> getBulletList() {
		return listOfBullets;
	}

	public Array<PlayerBullets> getPlayerBulletList() {
		return listOfBullets;
	}

	public TextureRegion getBulletTexture() {
		return bulletTexture[0];
	}

	public void setHP(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public int getHP() {
		return healthPoints;
	}

	public void setDX(float dx) {
		this.dx = dx;
	}

	public void setDY(float dy) {
		this.dy = dy;
	}

	public boolean getDeathStatus() {
		return isDead;
	}

	public void setDeathStatus(boolean isDead) {
		this.isDead = isDead;
	}
}
