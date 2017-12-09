package com.jb.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;
import com.jb.main.Game;

public class Player extends GameObjects {

	// Physics
	private float maxSpeed;
	private float friction;
	private float minimumSpeed;

	// Graphics
	private String pathname;
	private Animator shipAnimation;
	private float animationTime;
	private float animationFrameDuration;
	private long flashingTimer;
	
	// GamePlay 
	private Array<PlayerBullets> listOfBullets;
	private float bulletSpeed;
	private long bulletcooldown;
	private long bulletShootSpeed;
	private int healthPoints;

	public Player(float x, float y, float dx, float dy, Array<PlayerBullets> listOfBullets) {
		super(x, y, dx, dy);
		
		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.listOfBullets = listOfBullets;
		bulletcooldown = TimeUtils.millis();

		// Graphics
		pathname = "data/spaceships/ship1.png";
		animationFrameDuration = 1f/40f;

		// Limits
		maxSpeed = 10;
		friction = 2;
		minimumSpeed = -10;
		bulletSpeed = 15;
		bulletShootSpeed = 200;

		// Start Player Loading
		init();
		
	}

	public void init() {
		// HealthPoints
		healthPoints = 100;
		
		// Note: Ship size is 64 x 64 pixels
		// Start Animation
		shipAnimation = new Animator(3, 8, pathname, 3, 1, animationFrameDuration);
		
		// Start Rectangle Box
		
		
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
	public void update(float dt) {

		// Player Input
		playerHandleInput(dt);

		// Physics
		applyFriction(dt);

		// Set Limits
		setLimits();

		// Update Speed
		x += dx;
		y += dy;
		
		// Update Rectangle Bounds
		updateRectangle(x, y);

		// Edge of the screen move to the other side
		wrap();
		

	}

	// Render Player
	public void draw(SpriteBatch spriteBatch) {
		
		// Draw Ship
		// Get Time frame for animation
		animationTime += Gdx.graphics.getDeltaTime();
		
		spriteBatch.draw(shipAnimation.getAnimationFrames().getKeyFrame(animationTime, true), x, y);
		
		
	}

	// Wrap Around + Prevent out of bounds for Y
	private void wrap() {
		// Wrap Around for X Coordinate
		if (x > Game.WIDTH) {
			x = 0;
		}
		if (x < 0 - 64) {
			x = Game.WIDTH;
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
		// Left
		if (left) {
			dx -= 15 * dt;
		}
		// right
		if (right) {
			dx += 15 * dt;
		}
		// Up
		if (up) {
			dy += 15 * dt;
		}
		// Down
		if (down) {
			dy -= 15 * dt;
		}
		// Shoot | Note: This will add a bullet to the array which is passed back to the PlayState
		if (shoot) {
			if (TimeUtils.timeSinceMillis(bulletcooldown) > bulletShootSpeed) {
				addBullets(32, 64);		
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
		collisionBounds.set(x, y, x + 64, y + 64);
	}
	
	public Array<PlayerBullets> getBulletList() {
		return listOfBullets;
	}
	
	public void setHP(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	public int getHP() {
		return healthPoints;
	}

}
