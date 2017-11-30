package com.jb.gameobjects.player;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jb.gameobjects.GameObjects;
import com.jb.main.Game;

public class Player extends GameObjects {
	
	private float maxSpeed;
	private float acceleration;
	private float friction; 
	
	// Creating a box just as a test
	private float[] shapeX;
	private float[] shapeY;

	public Player(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
	
		
		// Limits
		maxSpeed = 30;
		acceleration = 200;
		friction = 10;
		
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
			dx -= 5;
			dy = 0;
		}
		
		// Right
		if (right) {
			dx += 5;
			dy = 0;
		}
		
		// Up
		if (up) {
			dy -= 5;
			dx = 0;
		}

		// Down
		if (down) {
			dy += 5;
			dx = 0;
		}
		
		// Set Maximum Speed
		clamp(maxSpeed, 0, dx);
		clamp(maxSpeed, 0, dy);
		
		// Update Speed
		x += dx;
		y += dy;
		
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
