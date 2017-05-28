package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.view.stages.MainMenuStage;

public class MainMenuState extends GameState {

	MainMenuStage _stage;

	public MainMenuState(GameStateManager gsm) {
		super(gsm);

		_stage = new MainMenuStage(gsm);
		Gdx.input.setInputProcessor(_stage);
	}

	@Override
	public void resize(int w, int h) {
		_stage.getViewport().update(w, h);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		_stage.draw();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

}
