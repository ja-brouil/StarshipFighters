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
	
	// PlayState Access
	private PlayState playState;
	
	// Physics
	private float maxSpeed;
	private float minimumSpeed;

	// Graphics
	private String pathname;
	private Animator shipAnimation;
	private float animationTime;
	private float animationFrameDuration;
	private Array<PlayerHit> listOfPlayerHits;

	// Basic Bullet Graphics
	private String basicBulletPathname = "data/ammo/bulletfinal.png";
	private TextureRegion[] bulletTexture;
	
	// Player Hit Graphics
	private String playerHitGraphicPathName = "data/hit_and_explosions/impactHit.png";
	
	// Asset Manager
	private AssetManager assetManager;

	// Sound Effects
	private String playerHitSoundPathName = "data/audio/sound/No Damage.wav";
	private String bulletShotSoundPathName;
	private Sound bulletSound;

	// GamePlay
	private Array<PlayerBullets> listOfBullets;
	private float bulletSpeed;
	private long bulletcooldown;
	private long bulletShootSpeed;
	private float maxHealth;
	
	// Cutscene
	private boolean outOfBoundsAllowed = false;
	
	// Input
	private PlayerInput playerInput;

	public Player(float x, float y, float dx, float dy, AssetManager assetManager, PlayState playState) {
		super(x, y, dx, dy, assetManager);

		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		bulletcooldown = TimeUtils.millis();
		this.playState = playState;
		this.assetManager = assetManager;
		

		// Graphics
		pathname = "data/spaceships/ship1.png";
		animationFrameDuration = 1f / 40f;

		// SFX
		bulletShotSoundPathName = "data/audio/sound/Basic Shot.wav";

		// Limits
		maxSpeed = 4;
		minimumSpeed = -4;
		bulletSpeed = 15;
		bulletShootSpeed = 200;

		// Start Player Loading
		init();

	}

	private void init() {
		// HealthPoints
		healthbar = 200;
		maxHealth = 200;
		isDead = false;

		// Note: Ship size is 64 x 64 pixels
		// Start Animation
		shipAnimation = new Animator(3, 8, pathname, 3, 1, animationFrameDuration, assetManager);

		// Start Rectangle Box
		collisionBounds = new Rectangle(x, y, 64, 64);

		// Load Sound
		assetManager.load(bulletShotSoundPathName, Sound.class);
		assetManager.load(playerHitSoundPathName, Sound.class);

		// Load Art
		assetManager.load(basicBulletPathname, Texture.class);
		assetManager.load(playerHitGraphicPathName, Texture.class);

		// Set Art and Sound Objects
		assetManager.finishLoading();
		bulletSound = assetManager.get(bulletShotSoundPathName, Sound.class);

		// Load Bullet Texture
		Texture tmpBulletTexture = assetManager.get(basicBulletPathname, Texture.class);
		TextureRegion[][] tmp = TextureRegion.split(tmpBulletTexture, tmpBulletTexture.getWidth() / 2,
				tmpBulletTexture.getHeight());
		bulletTexture = new TextureRegion[1];
		bulletTexture[0] = tmp[0][0];
		
		// Player Array Lists
		listOfBullets = new Array<PlayerBullets>();
		listOfPlayerHits = new Array<PlayerHit>();
		
		// Start Player Input
		playerInput = new PlayerInput(this);

	}

	// Update Player Status
	@Override
	public void update(float dt) {
		
		// Check if dead
		if (healthbar <= 0) {
			isDead = true;
			return;
		}
		
		// Set Health Limit
		if (healthbar > maxHealth) {
			healthbar = (int) maxHealth;
		}
		
		// Player Input
		playerInput.playerHandleInput(dt);

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
		
		// Update Payer Hits
		for (int i = 0; i < listOfPlayerHits.size; i++) {
			listOfPlayerHits.get(i).update(dt);
			// Remove from Array
			if (listOfPlayerHits.get(i).isPlayerHitAnimationDone()) {
				listOfPlayerHits.removeIndex(i);
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
		
		// Draw Player Hits
		for (PlayerHit playerHit: listOfPlayerHits) {
			playerHit.draw(spriteBatch);
		}
	}

	// Prevent Out of Bounds
	private void wrap() { 
		// Allow out of bounds
		if (outOfBoundsAllowed) {
			return;
		}
		
		// Wrap Around for X Coordinate
		if (x > Game.WIDTH - 64) {
			x = Game.WIDTH - 64;
		}
		if (x < 0) {
			x = 0;
		}
		
		// Wrap Around for Y Coordinate
		if (y > playState.getGSM().getGame().getCamera().position.y + (Game.HEIGHT / 2) - 64) {
			y = playState.getGSM().getGame().getCamera().position.y + (Game.HEIGHT / 2) - 64;
		}
		if (y < 0 + playState.getGSM().getGame().getCamera().position.y - 400) {
			y = 0 + playState.getGSM().getGame().getCamera().position.y - 400;
		}

	}

	// Limits
	private void setLimits() {
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

	// Shoot Code
	public void addBullets(int xOffset, int yOffset) {
		listOfBullets.add(new PlayerBullets(getX() + xOffset, getY() + yOffset, 0, bulletSpeed, assetManager, this));
		bulletSound.play(1.0f);
		bulletcooldown = TimeUtils.millis();
	}
	
	// Missile Code

	// Update HitBox
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
	
	public Array<PlayerHit> getPlayerHits(){
		return listOfPlayerHits;
	}

	public TextureRegion getBulletTexture() {
		return bulletTexture[0];
	}
	
	public PlayerInput getPlayerInput() {
		return playerInput;
	}

	public void setDX(float dx) {
		this.dx = dx;
	}

	public void setDY(float dy) {
		this.dy = dy;
	}
	
	public void setDeathStatus(boolean isDead) {
		this.isDead = isDead;
	}

	public long getBulletcooldown() {
		return bulletcooldown;
	}

	public long getBulletShootSpeed() {
		return bulletShootSpeed;
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public int getHealthBar() {
		return healthbar;
	}
	
	public PlayState getPlayState() {
		return playState;
	}
	
	public void setAllowedOutOfBounds(boolean outOfBoundsAllowed) {
		this.outOfBoundsAllowed = outOfBoundsAllowed;
	}
}
