package com.jb.gameobjects.player;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jb.gameobjects.GameObjects;

public class Player extends GameObjects {
	
	private float maxSpeed;
	private float friction; 
	private float offSetAmount;
	private float minimumSpeed;
	
	// Creating a box just as a test
	private float[] shapeX;
	private float[] shapeY;

	public Player(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
		offSetAmount = 100;
		
		// Limits
		maxSpeed = 30;
		friction = 2;
		minimumSpeed = -30;
		
		// Start Debug Box
		shapeX = new float[4];
		shapeY = new float[4];
		
		// Set Vertices
		setShape();
		
	}
	
	// Set Box Vertices
	private void setShape() {
		
		shapeX[0] = x;
		shapeY[0] = y;
		
		shapeX[1] = x;
		shapeY[1] = y + 100;
		
		shapeX[2] = x + 100;
		shapeY[2] = y;
		
		shapeX[3] = x + 100;
		shapeY[3] = y + 100;
	}
	
	// Set Direction
	public void setLeft(boolean b) { left = b;}
	public void setRight(boolean b) { right = b;}
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	
	// Update Player Status
	public void update(float dt) {
		
		
		// Left
		if (left) {
			dx -= 5 * dt;
		}
		// right
		if (right) {
			dx += 5 * dt;	
		}
		// Up
		if (up) {
			dy += 5 * dt;
		}
		// Down
		if (down) {
			dy -= 5 * dt;	 
		}
		
		// Apply Friction to the ship
		// X coordinate
		if (dx != 0 && dx > 0) {
			dx -= friction * dt;
		} else if (dx != 0 && dx < 0) {
			dx += friction * dt;
		}
		// Y coordinate
		if (dy != 0 && dy > 0) {
			dy -= friction * dt;
		} else if (dy != 0 && dy < 0) {
			dy += friction * dt;
		}
		
		// Set Maximum Speed
		if (dx > maxSpeed) { dx = maxSpeed; }
		if (dx < minimumSpeed) { dx = minimumSpeed; }
		if (dy > maxSpeed) { dy = maxSpeed; }
		if (dy < minimumSpeed) { dy = minimumSpeed; }
		
		// Update Speed
		x += dx;
		y += dy;
		
		// Edge of the screen move to the other side
		wrap((int) offSetAmount);
		
		
		// Update Shape Coordinates
		setShape();
		
	}
	
	// Render Player
	public void draw(ShapeRenderer sr) {
		
		// White
		sr.setColor(1,1,1,1);
		
		// Drawing
		sr.begin(ShapeType.Line);
		
		sr.line(shapeX[0],shapeY[0], shapeX[1], shapeY[1]);
		sr.line(shapeX[1],shapeY[1], shapeX[3], shapeY[3]);
		sr.line(shapeX[3],shapeY[3], shapeX[2], shapeY[2]);
		sr.line(shapeX[2],shapeY[2], shapeX[0], shapeY[0]);
		
		sr.end();
	}
	

}
