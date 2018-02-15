package com.jb.gameobjects.player;

import com.badlogic.gdx.utils.TimeUtils;

public class PlayerInput {
	
	// Input Variables
	private boolean left, right, up, down;
	private boolean shoot, missile;
	
	// Player
	private Player player;
	
	// Allow Input
	private boolean allowInput;

	public PlayerInput(Player player) {
		this.player = player;
		allowInput = true;
	}
	
	// Handle input
	public void playerHandleInput(float dt) {
			if (!allowInput) {
				return;
			}
		
			// Left | Right | Up | Down
			if (left) {
				player.setDX(player.getDX() -4);
			} else if (!left && !right) {
				player.setDX(0);
			}

			if (right) {
				player.setDX(player.getDX() + 4);
			} else if (!left && !right) {
				player.setDX(0);
			}

			if (up) {
				player.setDY(player.getDY() + 4);
			} else if (!up && !down) {
				player.setDY(0);
			}

			if (down) {
				player.setDY(player.getDY() - 4);
			} else if (!up && !down) {
				player.setDY(0);
			}

			// Shoot
			if (shoot) {
				if (TimeUtils.timeSinceMillis(player.getBulletcooldown()) > player.getBulletShootSpeed()) {
					player.addBullets(32, 64);
				}

			}
			// Missile
			if (missile) {
				// Code for shooting missiles
			}

		}

	// Set Player Keys
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
	
	public boolean getAllowedInput() {
		return allowInput;
	}
	
	public void setAllowedInput(boolean allowInput) {
		this.allowInput = allowInput;
	}
}
