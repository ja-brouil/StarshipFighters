package com.jb.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jb.HUD.HealthBar;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gameobjects.items.Item;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.gamestates.PlayState;

/*
 * Physics Class for Collision detection
 */

public class CollisionHandling {

	// Game Objects
	private Player player;
	private Array<PlayerBullets> shipBullets;
	private Array<GameObjects> basicAliens;
	private Array<EnemyBullets> enemyBulletList;
	private Array<Explosion> explosionList;
	private Array<Item> itemList;
	private PlayState playState;

	public CollisionHandling(PlayState playState) {

		this.playState = playState;
		player = playState.getPlayer();
		shipBullets = playState.getShipBullets();
		basicAliens = playState.getBasicAliens();
		enemyBulletList = playState.getEnemyBulletList();
		explosionList = playState.getExplosionList();
		itemList = playState.getItemList();
	}

	public void update(float dt) {
		shipBulletCollision();
		playerCollisionWithObjects();
		enemyBulletPlayerCollision();
		checkItemCollision();
	}

	// Ship Bullet
	private void shipBulletCollision() {
		// Check if bullet hit an enemy
		// For the best responsiveness, have a check for both contains method and
		// overlap
		for (int i = 0; i < shipBullets.size; i++) {
			for (int j = 0; j < basicAliens.size; j++) {
				if (shipBullets.get(i).getBoundingBox().contains(basicAliens.get(j).getBoundingBox())
						|| basicAliens.get(j).getBoundingBox().contains(shipBullets.get(i).getBoundingBox())
						|| shipBullets.get(i).getBoundingBox().overlaps(basicAliens.get(j).getBoundingBox())
						|| basicAliens.get(j).getBoundingBox().overlaps(shipBullets.get(i).getBoundingBox())) {
					basicAliens.get(j).setHP(shipBullets.get(i).getDamageValue(), false);
					shipBullets.get(i).removeBullets();
				}
			}
		}
	}

	// Player Colliding
	private void playerCollisionWithObjects() {
		// Check if player hits Enemies on Screen
		for (int i = 0; i < basicAliens.size; i++) {
			if (basicAliens.get(i).getBoundingBox().contains(player.getBoundingBox())
					|| basicAliens.get(i).getBoundingBox().overlaps(player.getBoundingBox())) {
				((BasicAlien) basicAliens.get(i)).setDrop(1);
				basicAliens.get(i).setHP(-1, true);
				HealthBar tempHP = (HealthBar) playState.getHUD(0);
				tempHP.setHealthLeft(-tempHP.getTotalHealth() * 0.25f);
			}
		}
	}

	private void enemyBulletPlayerCollision() {
		// Check Collision for enemies hitting the player
		for (int i = 0; i < enemyBulletList.size; i++) {
			if (enemyBulletList.get(i).getBoundingBox().contains(player.getBoundingBox())
					|| player.getBoundingBox().contains(enemyBulletList.get(i).getBoundingBox())) {
				// Play Explosion on Player
				explosionList.add(new Explosion((player.getX() + MathUtils.random(0, 32)),
						player.getY() + MathUtils.random(0, 32), 0, 0, "data/hit_and_explosions/impactHit.png",
						"data/audio/sound/Bomb Explosion.wav", "Player Hit", 3, 1, 3, 1, 1f / 40f, playState));

				// Reduce Player Health
				HealthBar tempHP = (HealthBar) playState.getHUD(0);
				tempHP.setHealthLeft(enemyBulletList.get(i).getDamageValue());

				// Remove Bullet
				enemyBulletList.get(i).dispose();
				enemyBulletList.get(i).removeBullets();
			}
		}
	}

	private void checkItemCollision() {
		// Check for Items colliding with player
		for (int i = 0; i < itemList.size; i++) {
			if (itemList.get(i).getBoundingBox().overlaps(player.getBoundingBox())
					|| player.getBoundingBox().overlaps(itemList.get(i).getBoundingBox())) {
				EnergyTank tmpEnergyTank = (EnergyTank) itemList.get(i);
				HealthBar tempBar = (HealthBar) playState.getHUD(0);
				tempBar.setHealthLeft(tmpEnergyTank.getHealthRegenValue());
				itemList.get(i).dispose();
				itemList.removeIndex(i);
				i--;
			}
		}
	}

}
