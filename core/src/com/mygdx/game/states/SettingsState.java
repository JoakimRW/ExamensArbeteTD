package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Game;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.GameStateManager.State;
import com.mygdx.game.view.stages.SettingsStage;

public class SettingsState extends GameState {
	SettingsStage settingsStage;
	public SettingsState(GameStateManager gsm) {
		super(gsm);
		settingsStage = new SettingsStage();
		Gdx.input.setInputProcessor(settingsStage);
		settingsStage.getSettingsPanel().getMusicVolSlider().addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Game.VOLUME_MUSIC = settingsStage.getSettingsPanel().getMusicVolSlider().getValue();
			}
			
		});
		
		settingsStage.getSettingsPanel().getSoundFxSlider().addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Game.VOLUME_SOUNDFX = settingsStage.getSettingsPanel().getSoundFxSlider().getValue();
			}
			
		});
		
		settingsStage.getOkButton().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				gsm.setState(State.MAINMENU);
			}
		});
	}

	@Override
	public void resize(int w, int h) {
		settingsStage.getViewport().update(w, h);
		settingsStage.getViewport().apply(true);
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
