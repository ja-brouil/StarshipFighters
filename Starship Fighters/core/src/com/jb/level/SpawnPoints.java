package com.jb.level;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.jb.gameobjects.enemies.EnemyTypes;
import com.jb.gameobjects.enemies.level2enemies.BasicAlien2;
import com.jb.gameobjects.enemies.level2enemies.KamikazeAlien2;
import com.jb.gamestates.PlayState;

@SuppressWarnings("serial")
public class SpawnPoints extends Vector2{
	
	// Type of Enemy
	private EnemyTypes enemyType;
	
	// PlayState and Asset Manager
	private AssetManager assetManager;
	private PlayState playState;
	
	/**
	 * Generic Spawn Points
	 * @param x
	 * @param y
	 */
	public SpawnPoints(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * With Enemy types. x and y position of object, enemytype and level access
	 * @param x
	 * @param y
	 * @param enemyType
	 * @param level
	 */
	public SpawnPoints(float x, float y, EnemyTypes enemyType, Level level) {
		this.x = x;
		this.y = y;
		this.enemyType = enemyType;
		playState = level.getPlayState();
		assetManager = level.getAssetManager();
	}
	
	public Object spawnEnemy() {
		if (enemyType == EnemyTypes.BasicAlien) {
			return new BasicAlien2(x, y, 0, -5, 1000L, -15, -25, assetManager , playState);
		} else if (enemyType == EnemyTypes.KamikazeAlien) {
			return new KamikazeAlien2(x, y, 0, -8, -40, assetManager, playState);
		}
		return null;
	}
	
	public EnemyTypes getEnemytype() {
		return enemyType;
	}
}
