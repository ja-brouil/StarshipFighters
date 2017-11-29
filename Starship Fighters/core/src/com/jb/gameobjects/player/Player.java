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
		
		// Center Screen, for testing purposes. Change this later
		x = Game.WIDTH / 2;
		y = Game.HEIGHT / 2;
		
		// Limits
		maxSpeed = 300;
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
		
		shapeX[0] = 100;
		shapeY[0] = 100;
		
		shapeX[1] = 100;
		shapeY[1] = 110;
		
		shapeX[2] = 110;
		shapeY[2] = 100;
		
		shapeX[3] = 110;
		shapeY[3] = 110;
	}
	
	public void setLeft(boolean b) { left = b;}
	public void setRight(boolean b) { up = b;}
	public void setUp(boolean b) { up = b; }
	
	public void update(float dt) {
		
	}
	
	public void draw(ShapeRenderer sr) {
		
		// White
		sr.setColor(1,1,1,1);
		
		// Drawing
		sr.begin(ShapeType.Line);
		
		for (int i = 0, j = 0; i < shapeX.length; j = i++) {
			
			sr.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
			
		}
		
		sr.end();
	}
	

}
