package com.jb.level.level1;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.gameobjects.player.PlayerHit;
import com.jb.gamestates.PlayState;

public class Level1CollisionDetection {

	// Game Objects
	// Player
	private Player player;
	private Array<PlayerBullets> playerBulletList;

	// Basic Aliens
	private Array<BasicAlien> basicAlienList;
	private Array<EnemyBullets> enemyBulletList;

	// Explosion Array
	private Array<Explosion> explosionList;

	// Item List
	private Array<EnergyTank> energyTankList;

	public Level1CollisionDetection(PlayState playState, Level1 level1) {
		// Load Array Lists
		basicAlienList = level1.getBasicAlienList();
		enemyBulletList = level1.getEnemyBulletList();
		explosionList = level1.getExplosionList();
		energyTankList = level1.getEnergyTankList();
		player = playState.getPlayer();
		playerBulletList = player.getBulletList();
	}

	// Collision Handling
	public void checkCollisions() {
		playerBulletsWithEnemies();
		playerCollisionWithEnemies();
		enemyBulletsWithPlayer();
	}

	// Player Bullets with Enemies
	private void playerBulletsWithEnemies() {
		for (int i = 0; i < playerBulletList.size; i++) {
			for (int j = 0; j < basicAlienList.size; j++) {
				if (playerBulletList.get(i).getBoundingBox().contains(basicAlienList.get(j).getBoundingBox())
						|| basicAlienList.get(j).getBoundingBox().contains(playerBulletList.get(i).getBoundingBox())
						|| playerBulletList.get(i).getBoundingBox().overlaps(basicAlienList.get(j).getBoundingBox())
						|| basicAlienList.get(j).getBoundingBox().overlaps(playerBulletList.get(i).getBoundingBox())) {
					basicAlienList.get(j).setHP(playerBulletList.get(i).getDamageValue(), false);
					playerBulletList.get(i).removeBullets();
				}
			}
		}
	}

	// Player Collision with Enemies
	private void playerCollisionWithEnemies() {
		for (int i = 0; i < basicAlienList.size; i++) {
			if (basicAlienList.get(i).getBoundingBox().contains(player.getBoundingBox())
					|| basicAlienList.get(i).getBoundingBox().overlaps(player.getBoundingBox())) {
				basicAlienList.get(i).setDrop(1);
				basicAlienList.get(i).setHP(-1, true);

				// Add code to reduce player health here
			}
		}
	}

	// Enemy Bullets hitting Player
	private void enemyBulletsWithPlayer() {
		for (int i = 0; i < enemyBulletList.size; i++) {
			if (enemyBulletList.get(i).getBoundingBox().contains(player.getBoundingBox())
					|| player.getBoundingBox().contains(enemyBulletList.get(i).getBoundingBox())) {
				player.getPlayerHits().add(new PlayerHit(player.getX() + MathUtils.random(0, 32),
						player.getY() + MathUtils.random(0, 32), 0, 0, 3, 1, 3, 1, 1f / 40f, player.getAssetManager()));
				// Add Code to Reduce Player Health
				// Remove Bullet
				enemyBulletList.get(i).removeBullets();
			}
		}
	}

	// Energy Tank Hitting Player
	private void playerWithEnergyTank() {

	}

}
