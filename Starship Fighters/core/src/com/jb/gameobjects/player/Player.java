package com.jb.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.animation.Animator;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gamestates.PlayState;
import com.jb.main.Game;

public class Player extends GameObjects {

	// Physics
	private float maxSpeed;
	//private float friction;
	private float minimumSpeed;

	// Graphics
	private String pathname;
	private Animator shipAnimation;
	private float animationTime;
	private float animationFrameDuration;
	
	// Sound Effects
	private SoundManager soundManager;
	private String bulletShotSoundPathName;
	private String bulletShotSoundName;
	
	// GamePlay 
	private Array<PlayerBullets> listOfBullets;
	private float bulletSpeed;
	private long bulletcooldown;
	private long bulletShootSpeed;
	private int healthPoints;
	private boolean isDead;
	private PlayState playState;

	public Player(float x, float y, float dx, float dy, Array<PlayerBullets> listOfBullets, PlayState playState) {
		super(x, y, dx, dy);
		
		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.listOfBullets = listOfBullets;
		this.playState = playState;
		bulletcooldown = TimeUtils.millis();

		// Graphics
		pathname = "data/spaceships/ship1.png";
		animationFrameDuration = 1f/40f;
		
		// SFX
		this.playState = playState;
		soundManager = playState.getGSM().getGame().getSoundManager();
		bulletShotSoundPathName = "data/audio/sound/Basic Shot.wav";
		bulletShotSoundName = "Shoot1";

		// Limits
		maxSpeed = 4;
		//friction = 5;
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
		shipAnimation = new Animator(3, 8, pathname, 3, 1, animationFrameDuration);
		
		// Start Rectangle Box
		collisionBounds = new Rectangle(x, y, 64, 64);
		
		// Start Sound
		soundManager.addSound(bulletShotSoundPathName, bulletShotSoundName);
		
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

		// Player Input
		playerHandleInput(dt);

		// Physics
		//applyFriction(dt);

		// Set Limits
		setLimits();

		// Update Speed
		this.x += dx;
		this.y += dy;
		
		// Update Rectangle Bounds
		updateRectangle(x, y);

		// Edge of the screen move to the other side
		wrap();
		
		// Check if dead
		if (healthPoints <= 0) {
			playState.setGameOver(true);
		}

	}

	// Render Player
	@Override
	public void draw(SpriteBatch spriteBatch) {	
		
		// Draw Ship
		// Get Time frame for animation
		animationTime += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(shipAnimation.getAnimationFrames().getKeyFrame(animationTime, true), x, y);
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

	// Apply Friction to ship 
	/*
	private void applyFriction(float dt) {
		// Apply Friction to the ship
		// X coordinate
		if (dx != 0 && dx > 0) {
			dx -= friction * dt;
		} else if (dx != 0 && dx < 0) {
			dx += friction * dt;
		}
		// Y coordinate
		if (dy != 0 && dy > 0) {
			dy -= friction * dt;
		} else if (dy != 0 && dy < 0) {
			dy += friction * dt;
		}
	}
	*/

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
			dx += -4; //* dt;
		} else if (!left && !right) {
			dx = 0;
		}
		
		if (right) {
			dx += 4; //* dt;
		} else if (!left && !right) {
			dx = 0;
		}
		
		if (up) {
			dy += 4; //* dt;
		} else if (!up && !down) {
			dy = 0;
		}
		
		if (down) {
			dy += -4; //* dt;
		} else if (!up && !down) {
			dy = 0;
		}
		
		
		// Shoot | Note: This will add a bullet to the array which is passed back to the PlayState
		if (shoot) {
			if (TimeUtils.timeSinceMillis(bulletcooldown) > bulletShootSpeed) {
				addBullets(32, 64);	
				soundManager.playSound(bulletShotSoundName, 1.0f);
			}
			
		}
		// Missile | Note: This will add a missile to the array which is passed back to the PlayState
		if (missile) {
			// Code for shooting missiles
		}
		
		
	}
	
	// Shoot Code
	private void addBullets(int xOffset, int yOffset) {
		listOfBullets.add(new PlayerBullets(getX() + xOffset, getY() + yOffset, 0, bulletSpeed));
		bulletcooldown = TimeUtils.millis();
	}
	
	// Update Rectangle 
	private void updateRectangle(float x, float y) {
		collisionBounds.set(x, y, 64,64);
	}
	
	// Dispose Method
	public void dispose() {
		shipAnimation.dispose();
	}
	
	// Setters and Getters
	
	public Array<PlayerBullets> getBulletList() {
		return listOfBullets;
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
	
	// Not needed override
	@Override
	public void update(float dt, boolean xWrap, boolean yWrap) {}

}
