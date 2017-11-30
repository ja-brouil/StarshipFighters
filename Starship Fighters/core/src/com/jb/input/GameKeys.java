package com.jb.input;

public class GameKeys {
	
	private static boolean[] keys;
	private static boolean[] previousKeys;
	
	public static final int NUM_KEYS = 8;
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int ENTER = 4; // Start || Pause
	public static final int ESCAPE = 5; // Back || Exit Program
	public static final int SPACE = 6; // Shoot
	public static final int SHIFT = 7; // Shoot Special
	
	static {
		keys = new boolean[NUM_KEYS];
		previousKeys = new boolean[NUM_KEYS];
	}
	
	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++){
			previousKeys[i] = keys[i];
		}
	}
	
	public static void setKey(int k, boolean b) {
		keys[k] = b;
	}
	
	// Constant when held
	public static boolean isDown(int k) {
		return keys[k];
	}
	
	// When key is pressed
	public static boolean isPressed(int k) {
		return keys[k] && !previousKeys[k];
	}
	

}
