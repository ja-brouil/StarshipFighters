package com.jb.level.level1;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.level1enemies.EnemyBullets;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gameobjects.items.Item;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.gameobjects.player.PlayerHit;
import com.jb.gamestates.PlayState;

public class Level1CollisionDetection {

	// Game Objects
	// Player
	private Player player;
	private Array<PlayerBullets> playerBulletList;

	// Enemies
	private Array<EnemyBullets> enemyBulletList;
	private Array<GameObjects> allEnemiesList;

	// Item List
	private Array<Item> allItems;

	public Level1CollisionDetection(PlayState playState, Level1 level1) {
		// Load Array Lists
		enemyBulletList = level1.getEnemyBulletList();
		allItems = playState.getAllItems();
		player = playState.getPlayer();
		playerBulletList = player.getBulletList();
		allEnemiesList = playState.getAllEnemies();
				
	}

	// Collision Handling
	public void checkCollisions() {
		checkPlayerBulletCollision();
		playerCollisionWithEnemies();
		enemyBulletsWithPlayer();
		playerWithEnergyTank();
	}
	
	// Player Bullet Collision
	private void checkPlayerBulletCollision() {
		for (int i = 0; i < playerBulletList.size; i++) {
			for (int j = 0; j < allEnemiesList.size; j++) {
				if (playerBulletList.get(i).getBoundingBox().overlaps(allEnemiesList.get(j).getBoundingBox())) {
					allEnemiesList.get(j).changeHealthBar(playerBulletList.get(i).getDamageValue());
					playerBulletList.get(i).removeBullets();
				}
			}
		}
	}
	


	// Player Collision with Enemies
	private void playerCollisionWithEnemies() {
		for (GameObjects enemy : allEnemiesList) {
			if (player.getBoundingBox().overlaps(enemy.getBoundingBox())) {
				player.changeHealthBar(enemy.getDamageValue());
				enemy.setHealthBar(-1);
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
				// Player Damage
				player.changeHealthBar(enemyBulletList.get(i).getDamageValue());
				// Remove Bullet
				enemyBulletList.get(i).setIsDead(true);
			}
		}
	}

	// Energy Tank Hitting Player
	private void playerWithEnergyTank() {
		for (Item energyTank : allItems) {
			if (energyTank.getBoundingBox().overlaps(player.getBoundingBox())){
				player.changeHealthBar(((EnergyTank) energyTank).getHealthRegenValue());
				energyTank.setDeathStatus(true);
			}
		}
	}
	
}
