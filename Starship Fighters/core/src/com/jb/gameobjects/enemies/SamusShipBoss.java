package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;
import com.jb.main.Game;

public class SamusShipBoss extends GameObjects{

	// Right and Left Wing
	private boolean isLeftWingDead;
	private boolean isRightWingDead;
	
	// GamePlay
	private int leftWingHealth;
	private int rightWingHealth;
	private int bossBulletDamage;
	
	// Graphics
	private float width, height;
	private Animator enemyBossSprite;
	private String filePath;
	private float frameLengthTime;
	private float animationTime;
	
	// HitBoxes
	private Rectangle[] bossHitBoxes;
	
	// Bullet Array
	private Array<EnemyBullets> enemyBullets;
	
	// timers
	private long timeSinceBattleBegan;
	private long bulletCooldown, bulletCooldownRight, bulletCooldownLeft;
	private long randomAttackCooldown, randomAttackCooldownLeft, randomAttackCooldownRight;
	
	public SamusShipBoss(float x, float y, float dx, float dy, Array<EnemyBullets> enemyBullets) {
		super(x, y, dx, dy);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.enemyBullets = enemyBullets;
		
		// Start Boss
		init();
		
	}
	
	// Start the boss
	private void init() {
		
		// Start Graphics
		filePath = "data/spaceships/samushipboss.png";
		frameLengthTime = 1f / 2f;
		enemyBossSprite = new Animator(3, 2, filePath, 3, 2, frameLengthTime);
		
		// Set Wing Booleans
		isLeftWingDead = false;
		isRightWingDead = false;
		bossBulletDamage = -20;
		randomAttackCooldown = MathUtils.random(400, 1000);
		randomAttackCooldownLeft = MathUtils.random(400, 1000);
		randomAttackCooldownRight = MathUtils.random(400, 1000);
		
		// HP
		leftWingHealth = 300;
		rightWingHealth = 300;
		healthbar = 1000;
		
		// Dimension Size
		width = enemyBossSprite.getSpriteWidth();
		height = enemyBossSprite.getSpriteHeight();
		
		// Start Timer
		timeSinceBattleBegan = TimeUtils.millis();
		bulletCooldownLeft = TimeUtils.millis();
		bulletCooldownRight = TimeUtils.millis();
		
	}

	// Update the Boss
	@Override
	public void update(float dt) {
		
		// Update Boss Position
		x += dx;
		y += dy;
		checkMovementLimits();
		
		// Shoot Bullets
		if (!isRightWingDead) {
			if (TimeUtils.timeSinceMillis(bulletCooldownRight) > randomAttackCooldownRight && x < Game.WIDTH && x > 0 && y < Game.HEIGHT && y > 0) {
				addBossBullets(20, 0);
				randomAttackCooldownRight = MathUtils.random(200, 1000);
				bulletCooldownRight = TimeUtils.millis();
			}
		}
		
		if (!isLeftWingDead) {
			if (TimeUtils.timeSinceMillis(bulletCooldownLeft) > randomAttackCooldownLeft && x < Game.WIDTH && x > 0 && y < Game.HEIGHT && y > 0) {
				addBossBullets(175, 0);
				randomAttackCooldownLeft = MathUtils.random(200, 1000);
				bulletCooldownLeft = TimeUtils.millis();
			}
		}
		
		
		
		// Shoot Laser
		// Code to shoot special laser
		
		
	}
	
	// Movement Limits
	private void checkMovementLimits() {
		
		if (x > (Game.WIDTH - (194 * 0.6))) {
			dx *= -1;
		}
		
		if (x < (0 - (194 / 3))) {
			dx *= -1;
		}
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		// Draw Boss
		// Get Time for animation
		animationTime += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(enemyBossSprite.getAnimationFrames().getKeyFrame(animationTime, true), x, y);
	}
	
	private void addBossBullets(float xOffset, float yOffset) {
		enemyBullets.add(new EnemyBullets(getX() + xOffset, getY() + yOffset, 0, 5, bossBulletDamage));
	}
	
	// Getters | Setters
	public boolean isLeftWingDead() {
		return isLeftWingDead;
	}

	public void setLeftWingDead(boolean isLeftWingDead) {
		this.isLeftWingDead = isLeftWingDead;
	}

	public boolean isRightWingDead() {
		return isRightWingDead;
	}

	public void setRightWingDead(boolean isRightWingDead) {
		this.isRightWingDead = isRightWingDead;
	}

	public int getLeftWingHealth() {
		return leftWingHealth;
	}

	public void setLeftWingHealth(int leftWingHealth) {
		this.leftWingHealth = leftWingHealth;
	}

	public int getRightWingHealth() {
		return rightWingHealth;
	}

	public void setRightWingHealth(int rightWingHealth) {
		this.rightWingHealth = rightWingHealth;
	}

}
