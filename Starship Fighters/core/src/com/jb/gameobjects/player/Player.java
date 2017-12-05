package com.jb.gameobjects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jb.assetmanagers.ImageManager;
import com.jb.gameobjects.GameObjects;
import com.jb.main.Game;

public class Player extends GameObjects {

	// Physics
	private float maxSpeed;
	private float friction;
	private float minimumSpeed;

	// Graphics
	private ImageManager playerSpaceShip;
	private String pathname;
	private TextureRegion spaceship;

	public Player(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);

		// Graphics
		playerSpaceShip = Game.getImageManager();
		pathname = "data/spaceships/ship.png";

		// Limits
		maxSpeed = 10;
		friction = 2;
		minimumSpeed = -10;

		// Start Player Loading
		init();
	}

	public void init() {
		// Add SpaceShip to texture database
		// Note: Ship size is 64 x 64 pixels
		playerSpaceShip.loadTexture(pathname, "spaceship");

		// Get Spaceship
		spaceship = new TextureRegion(playerSpaceShip.getTexture("spaceship"), 64, 0, 64, 64);

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

		// Edge of the screen move to the other side
		wrap();

	}

	// Render Player
	public void draw(SpriteBatch spriteBatch) {

		spriteBatch.draw(spaceship, x, y);

	}

	// Wrap Around + Prevent out of bounds for Y
	private void wrap() {

		if (x > Game.WIDTH) {
			x = -10;
		}
		if (x < -50) {
			x = Game.WIDTH;
		}

		if (y > 740) {
			y = 740;
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
			dx -= 8 * dt;
		}
		// right
		if (right) {
			dx += 8 * dt;
		}
		// Up
		if (up) {
			dy += 8 * dt;
		}
		// Down
		if (down) {
			dy -= 8 * dt;
		}
		// Shoot
		if (shoot) {
			System.out.println("You are shooting!");
		}
		// Missile
		if (missile) {
			System.out.println("Shooting a missile!");
		}
	}

}
