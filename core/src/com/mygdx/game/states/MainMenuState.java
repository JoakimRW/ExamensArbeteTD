package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.stages.MainMenuStage;

public class MainMenuState extends GameState {
	
	MainMenuStage stage;

	public MainMenuState(GameStateManager gsm) {
		super(gsm);
		stage = new MainMenuStage(gsm);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
