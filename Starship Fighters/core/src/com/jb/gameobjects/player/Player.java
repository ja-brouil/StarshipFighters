package com.jb.gameobjects.player;

import javax.xml.soap.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private Animator allAnimations;
	private TextureRegion[] shipAnimationLeft;
	private TextureRegion[] shipAnimationRight;
	private Animation<TextureRegion> leftAnimation;
	private Animation<TextureRegion> rightAnimation;
	private float animationTime;
	private float animationFrameDuration;
	
	// GamePlay 
	private Array<PlayerBullets> listOfBullets;
	private float bulletSpeed;
	private long bulletcooldown;

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

		// Start Player Loading
		init();
	}

	public void init() {
		// Note: Ship size is 64 x 64 pixels
		// Start Animation
		shipAnimation = new Animator(3, 8, pathname, 3, 1, animationFrameDuration);
		allAnimations = new Animator(3, 8, pathname, 3, 8, animationFrameDuration);
		
		shipAnimationLeft = new TextureRegion[3];
		for (int i = 0; i < shipAnimationLeft.length; i++) {
			shipAnimationLeft[i] = allAnimations.getTextureRegion(i + 3);
		}
		leftAnimation = new Animation<TextureRegion>(animationFrameDuration, shipAnimationLeft);
		
		shipAnimationRight = new TextureRegion[3];
		for (int i = 0; i < shipAnimationLeft.length; i++) {
			shipAnimationRight[i] = allAnimations.getTextureRegion(12 + i);
		}
		rightAnimation = new Animation<TextureRegion>(animationFrameDuration, shipAnimationRight);
		
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
		
		// Draw Ship
		// Get Time frame for animation
		animationTime += Gdx.graphics.getDeltaTime();
		// Orientation
		if (left && up) {
			spriteBatch.draw(rightAnimation.getKeyFrame(animationTime, true), x, y);
		} //else if (left && down) {
			//spriteBatch.draw(leftAnimation.getKeyFrame(animationTime, true), x, y);
		 else if (right && up) {
			spriteBatch.draw(leftAnimation.getKeyFrame(animationTime, true), x, y);
		} //else if (right && down) {
			//spriteBatch.draw(rightAnimation.getKeyFrame(animationTime, true), x, y);
		 else {
			spriteBatch.draw(shipAnimation.getAnimationFrames().getKeyFrame(animationTime, true), x, y);
		}
	}

	// Wrap Around + Prevent out of bounds for Y
	private void wrap() {
		// Wrap Around for X Coordinate
		if (x > Game.WIDTH) {
			x = 0;
		}
		if (x < 0) {
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
		// Shoot | Note: This will add a bullet to the array which is passed back to the playstate
		if (shoot) {
			if (TimeUtils.timeSinceMillis(bulletcooldown) > 200) {
				listOfBullets.add(new PlayerBullets(getX() + 32, getY() + 64, 0, bulletSpeed));
				bulletcooldown = TimeUtils.millis();
			}
			
		}
		// Missile | Note: This will add a missile to the array which is passed back to the playstate
		if (missile) {
			// Code for shooting missiles
		}
	}
	
	public Array<PlayerBullets> getBulletList() {
		return listOfBullets;
	}

}
