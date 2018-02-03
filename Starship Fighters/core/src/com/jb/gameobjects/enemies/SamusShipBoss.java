package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.animation.Animator;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.gamestates.PlayState;
import com.jb.main.Game;

public class SamusShipBoss extends GameObjects {

	// Right and Left Wing
	private boolean isLeftWingDead;
	private boolean isRightWingDead;
	private boolean isMiddleInvincible;
	private boolean isDead;

	// GamePlay
	private int leftWingHealth;
	private int rightWingHealth;
	private int bossBulletDamage;
	private int rightWingExplosionCounter;
	private int leftWingExplosionCounter;
	private int bossDeathExplosionCounter;
	
	// Asset Manager
	private AssetManager assetManager;

	// Graphics
	private float width, height;
	private Animator enemyBossSprite;
	private String filePath;
	private float frameLengthTime;
	private float animationTime;

	// SFX
	private SoundManager soundManager;
	private String bossSoundFilePath = "data/audio/sound/bossDamage.wav";
	private String bossSoundName = "Boss Hit";
	private String bossSpawnSoundFilePath = "data/audio/sound/bombLaunching.wav";
	private String bossSpawnSoundName = "Boss Spawn Sound";

	// Boss Music
	private MusicManager musicManager; 
	private String bossMusic = "data/audio/music/bossbattle.mp3";
	private String bossMusicName = "Boss Battle";

	// HitBoxes
	private Rectangle[] bossHitBoxes;

	// Array
	private Array<EnemyBullets> enemyBullets;
	private Array<Explosion> explosionList;
	private Array<GameObjects> listOfAliens;
	private PlayState playState;

	// timers
	private long timeSinceBattleBegan;
	private long rightWingExplosionTimer, leftWingExplosionTimer, bossDeathExplosionTimer;
	private long bulletCooldown, bulletCooldownRight, bulletCooldownLeft;
	private long randomAttackCooldown, randomAttackCooldownLeft, randomAttackCooldownRight;

	public SamusShipBoss(float x, float y, float dx, float dy, Array<EnemyBullets> enemyBullets,
			Array<Explosion> explosionList, Array<GameObjects> basicAliens, PlayState playState, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);

		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.enemyBullets = enemyBullets;
		this.explosionList = explosionList;
		this.listOfAliens = basicAliens;
		this.playState = playState;
		this.assetManager = assetManager;

		// Start Boss
		init();

	}

	// Start the boss
	private void init() {
		
		// Music
		musicManager.addMusic(bossMusic, bossMusicName);
		
		// sound
		soundManager.addSound(bossSoundFilePath, bossSoundName);
		soundManager.addSound(bossSpawnSoundFilePath, bossSpawnSoundName);

		// Start Graphics
		filePath = "data/spaceships/samushipboss.png";
		frameLengthTime = 1f / 2f;
		enemyBossSprite = new Animator(3, 4, filePath, 3, 4, frameLengthTime, assetManager);

		// Set Wing Booleans
		isLeftWingDead = false;
		isRightWingDead = false;
		isMiddleInvincible = true;
		isDead = false;
		bossBulletDamage = -20;
		randomAttackCooldown = MathUtils.random(200, 800);
		randomAttackCooldownLeft = MathUtils.random(400, 1000);
		randomAttackCooldownRight = MathUtils.random(400, 1000);

		// HP
		leftWingHealth = 1500;
		rightWingHealth = 1500;
		healthbar = 3000;

		// Dimension Size
		width = 196;
		height = 107;

		// Hit Boxes | 0 = left, 1 = center, 2 = right
		// We should probably store the values of these numbers in an array for the
		// extra dimensions next time so we can efficiently loop it
		// Might need to find a better algorithm for this
		bossHitBoxes = new Rectangle[3];
		bossHitBoxes[0] = new Rectangle(x, y, 48, height);
		bossHitBoxes[1] = new Rectangle(x + 49, y + 27, 98, 75);
		bossHitBoxes[2] = new Rectangle(x + 148, y, 48, height);

		// Start Timer
		timeSinceBattleBegan = TimeUtils.millis();
		bulletCooldownLeft = TimeUtils.millis();
		bulletCooldownRight = TimeUtils.millis();
		leftWingExplosionTimer = TimeUtils.millis();
		rightWingExplosionTimer = TimeUtils.millis();
		bossDeathExplosionTimer = TimeUtils.millis();

		// Graphics Explosion Counter
		rightWingExplosionCounter = 0;
		leftWingExplosionCounter = 0;
		bossDeathExplosionCounter = 0;

	}

	// Update the Boss
	@Override
	public void update(float dt) {

		// Update Boss Position
		x += dx;
		y += dy;
		checkMovementLimits();

		// Update Hit Box positions
		bossHitBoxes[0].setPosition(x, y);
		bossHitBoxes[1].setPosition(x + 49, y + 27);
		bossHitBoxes[2].setPosition(x + 148, y);
		
		// Check if hit by player bullets
		checkCollisions();

		// Wing Check
		checkWingStatus();

		// Shoot check
		shootBullets();

		// Spawn Small Enemies every 15 seconds
		spawnEnemies();

		// Check Boss Death
		checkMiddleHP();
	}

	// Movement Limits
	private void checkMovementLimits() {

		if (x > (Game.WIDTH - (194 * 0.6))) {
			dx *= -1;
		}

		if (x < (0 - (194 / 3))) {
			dx *= -1;
		}

		if (y <= 674) {
			dy = 0;
			dx = 2;
			y = 675;
			musicManager.loopMusic(bossMusicName);
		}
	}

	// Helper Methods
	// Bullet Shooting
	private void shootBullets() {
		// Shoot Bullets
		// Right Wing
		if (!isRightWingDead && rightWingHealth > 0) {
			if (TimeUtils.timeSinceMillis(bulletCooldownRight) > randomAttackCooldownRight && x < Game.WIDTH && x > 0
					&& y < Game.HEIGHT && y > 0) {
				addBossBullets(175, 0);
				randomAttackCooldownRight = MathUtils.random(200, 1000);
				bulletCooldownRight = TimeUtils.millis();
			}
		}

		// Left Wing
		if (!isLeftWingDead && leftWingHealth > 0) {
			if (TimeUtils.timeSinceMillis(bulletCooldownLeft) > randomAttackCooldownLeft && x < Game.WIDTH && x > 0
					&& y < Game.HEIGHT && y > 0) {
				addBossBullets(20, 0);
				randomAttackCooldownLeft = MathUtils.random(200, 1000);
				bulletCooldownLeft = TimeUtils.millis();
			}
		}

		// Both Wings have died
		if (isLeftWingDead && isRightWingDead && getHP() > 0) {
			if (TimeUtils.timeSinceMillis(bulletCooldown) > randomAttackCooldown && x < Game.WIDTH && x > 0
					&& y < Game.HEIGHT && y > 0) {
				addBossBullets(98, 30);
				randomAttackCooldown = MathUtils.random(200, 800);
				bulletCooldown = TimeUtils.millis();
			}
		}
	}

	// Wing Checks
	private void checkWingStatus() {
		// Check if Wings are dead
		if (rightWingHealth <= 0 && !isRightWingDead) {
			if (TimeUtils.timeSinceMillis(rightWingExplosionTimer) > 200 && rightWingExplosionCounter < 10) {
				// Add delayed explosions
				explosionList.add(new Explosion((x + 148) + MathUtils.random(-48, 48), y, 48, height, playState));
				rightWingExplosionTimer = TimeUtils.millis();
				rightWingExplosionCounter++;
			}

			// Kill Right Wing
			if (rightWingExplosionCounter == 10) {
				setRightWingDead(true);
			}
		}

		if (leftWingHealth <= 0 && !isLeftWingDead) {
			if (TimeUtils.timeSinceMillis(leftWingExplosionTimer) > 200 && leftWingExplosionCounter < 10) {
				// Add delayed explosions
				explosionList.add(new Explosion(x + MathUtils.random(-48, 48), y, 48, height, playState));
				leftWingExplosionTimer = TimeUtils.millis();
				leftWingExplosionCounter++;
			}

			// Kill Left Wing
			if (leftWingExplosionCounter == 10) {
				setLeftWingDead(true);
			}
		}

		// If Both wings are dead, then set middle to be vulnerable
		if (isLeftWingDead && isRightWingDead && isMiddleInvincible) {
			isMiddleInvincible = false;
		}
	}

	// Check Boss HP
	private void checkMiddleHP() {
		if (healthbar < 0) {
			dx = 0;
			dy = 0;
			if (TimeUtils.timeSinceMillis(bossDeathExplosionTimer) > 200 && bossDeathExplosionCounter < 15) {
				// Add delayed explosions
				explosionList.add(new Explosion(x + (0.5f * MathUtils.random(0, width)),
						(y + (0.5f * MathUtils.random(0, height))), width, height, playState));
				bossDeathExplosionTimer = TimeUtils.millis();
				bossDeathExplosionCounter++;
				isDead = true;
			}
		}
	}

	// Spawn Enemies
	private void spawnEnemies() {
		// Check if its been 5 seconds
		if (TimeUtils.timeSinceMillis(timeSinceBattleBegan) > 5000 && healthbar > 0) {
			listOfAliens.add(new BasicAlien(x + (width / 2), y + (height / 2), 3, 0, 1000L, -15, enemyBullets, -20, playState.getLevel1(), null, null));
			listOfAliens.add(new BasicAlien(x + (width / 2), y + (height / 2), -3, 0, 1000L, -15, enemyBullets, -20, playState));
			timeSinceBattleBegan = TimeUtils.millis();
			soundManager.playSound(bossSpawnSoundName, 1f);
		}

	}
	
	// Collision Check
	private void checkCollisions() {
		// Check Boss Hit | 0 = left, 1 = center, 2 = right
		if (this != null) {
			for (int h = 0; h < bossHitBoxes.length; h++) {
				for (int a = 0; a < playState.getShipBullets().size; a++) {
					PlayerBullets tmpShipBullets = playState.getShipBullets().get(a);
					if (tmpShipBullets.getBoundingBox().overlaps(this.getSpecificHitBox(h))) {
						if (h == 0) {
							if (!this.getLeftWingStatus()) {
								this.setLeftWingHealth(tmpShipBullets.getDamageValue());
							}
							explosionList.add(new Explosion(tmpShipBullets.getX(), tmpShipBullets.getY(), 0, 0,
									"data/hit_and_explosions/impactHit.png", "data/audio/sound/Bomb Explosion.wav",
									"Boss Hit", 3, 1, 3, 1, 1f / 40f, false, playState));
							tmpShipBullets.removeBullets();
						}
						if (h == 1) {
							if (!this.getMiddleInvincibleStatus()) {
								this.setHP(tmpShipBullets.getDamageValue(), false);
							}
							tmpShipBullets.removeBullets();
							explosionList.add(new Explosion(tmpShipBullets.getX(), tmpShipBullets.getY(), 0, 0,
									"data/hit_and_explosions/impactHit.png", "data/audio/sound/Bomb Explosion.wav",
									"Boss Hit", 3, 1, 3, 1, 1f / 40f, false, playState));
						}
						if (h == 2) {
							if (!this.getRightWingStatus()) {
								this.setRightWingHealth(tmpShipBullets.getDamageValue());
							}
							explosionList.add(new Explosion(this.getSpecificHitBox(h).getX(), tmpShipBullets.getY(),
									0, 0, "data/hit_and_explosions/impactHit.png",
									"data/audio/sound/Bomb Explosion.wav", "Boss Hit", 3, 1, 3, 1, 1f / 40f, false, playState));
							tmpShipBullets.removeBullets();
						}
					}
				}
			}
		}
	}
	
	// Render Method
	@Override
	public void draw(SpriteBatch spriteBatch) {
		// Draw Boss
		// Get Time for animation
		animationTime += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(enemyBossSprite.getAnimationFrames().getKeyFrame(animationTime, true), x, y);
	}
	
	
	
	// Add Boss Bullets
	private void addBossBullets(float xOffset, float yOffset) {
		enemyBullets.add(new EnemyBullets(getX() + xOffset, getY() + yOffset, 0, 5, bossBulletDamage, playState));
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
		this.leftWingHealth += leftWingHealth;
	}

	public int getRightWingHealth() {
		return rightWingHealth;
	}

	public void setRightWingHealth(int rightWingHealth) {
		this.rightWingHealth += rightWingHealth;
	}

	public Rectangle[] getHitBoxes() {
		return bossHitBoxes;
	}

	public Rectangle getSpecificHitBox(int i) {
		return bossHitBoxes[i];
	}

	public boolean getLeftWingStatus() {
		return isLeftWingDead;
	}

	public boolean getRightWingStatus() {
		return isRightWingDead;
	}

	public boolean getMiddleInvincibleStatus() {
		return isMiddleInvincible;
	}

	public boolean getDeathStatus() {
		return isDead;
	}

	@Override
	public void update(float dt, boolean xWrap, boolean yWrap) {}
}
