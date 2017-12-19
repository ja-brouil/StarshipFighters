package com.jb.gamestates;

import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;

public class GameOverState extends GameState{

	private Player player;
	private Array<PlayerBullets> shipBullets;
	private Array<GameObjects> basicAliens;
	private Array<EnemyBullets> enemyBulletList;
	private Array<Explosion> explosionList;

	public GameOverState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void handleInput() {
		
	}

	@Override
	public void update(float dt) {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void dispose() {
		
	}

}
