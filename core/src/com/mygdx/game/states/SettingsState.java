package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.view.stages.SettingsStage;

public class SettingsState extends GameState {
	SettingsStage settingsStage;
	public SettingsState(GameStateManager gsm) {
		super(gsm);
		settingsStage = new SettingsStage();
		Gdx.input.setInputProcessor(settingsStage);
	}

	@Override
	public void resize(int w, int h) {
		settingsStage.getViewport().update(w, h);
		
	}

	@Override
	public void update(float delta) {
		settingsStage.act(delta);
		
	}

	@Override
	public void render() {
		settingsStage.draw();
		
	}

	@Override
	public void dispose() {
			
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}
