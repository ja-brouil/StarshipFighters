package com.jb.gamestates;

public class VictoryState extends GameState{

	public VictoryState(GameStateManager gsm) {
		super(gsm);
		
		init();
	}

	@Override
	public void init() {
		System.out.println("YOU WIN!");
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
