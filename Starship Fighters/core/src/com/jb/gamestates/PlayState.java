package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.handler.GameStateManager;
import com.jb.input.GameKeys;

public class PlayState extends GameState{
	
	private Player player;
	private Array<PlayerBullets> shipBullets;
	private BasicAlien basicAlienTest;
	private Array<EnemyBullets> enemyBulletList;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
		
	}
	
	@Override
	public void init() {
		shipBullets = new Array<>();
		enemyBulletList = new Array<>();
		player = new Player(300, 150, 0, 0, shipBullets);
		basicAlienTest = new BasicAlien(500, 700, 3, 0, 1000L, -15, enemyBulletList);
	}

	@Override
	public void handleInput() {
		
		// Escape to quit to exit faster. Remember to remove this later!
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		// Update Keys
		GameKeys.update();
	}

	@Override
	public void update(float dt) {
		
		// Set Game Keys
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		player.setDown(GameKeys.isDown(GameKeys.DOWN));
		player.setShoot(GameKeys.isDown(GameKeys.SPACE));
		player.setMissile(GameKeys.isPressed(GameKeys.SHIFT));
		
		// Check Input
		handleInput();
		
		// Update Player | Bullets | Missiles
		player.update(dt);
		for (int i = 0; i < shipBullets.size; i++) {
			shipBullets.get(i).update(dt);
			// Remove Bullets
			if (shipBullets.get(i).getRemovalStatus()) {
				shipBullets.removeIndex(i);
			}
		}
		basicAlienTest.update(dt);
		for (int i = 0; i < enemyBulletList.size; i++) {
			enemyBulletList.get(i).update(dt);
			// Remove Bullets
			if (enemyBulletList.get(i).getRemovalStatus()) {
				enemyBulletList.removeIndex(i);
			}
		}
		
		// Check Collisions
		checkCollision();
		
		// Update Level
		
	}
	
	// Collision and Bullets
	private void checkCollision() {
		
	}

	@Override
	public void render() {
		
		// Clear screen to Black Background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		player.draw(spriteBatch);
		for (int i = 0; i < shipBullets.size; i++) {
			shipBullets.get(i).draw(spriteBatch);
		}
		basicAlienTest.render(spriteBatch);
		for (int i = 0; i < enemyBulletList.size; i++) {
			enemyBulletList.get(i).render(spriteBatch);
		}
		spriteBatch.end();
		
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		
	}

	
}
