package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.GameStateManager.State;
import com.mygdx.game.view.stages.WinStage;

public class WinState extends GameState {
	WinStage winStage;
	public WinState(GameStateManager gsm) {
		super(gsm);
		winStage = new WinStage();
		
		Gdx.input.setInputProcessor(winStage);
		
		winStage.getPlayAgainBtn().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				_gsm.setState(State.PLAY);
			}
		});
		
		winStage.getMainMenuBtn().addListener(new ClickListener(){
			 @Override
			public void clicked(InputEvent event, float x, float y) {
				 _gsm.setState(State.MAINMENU);
			}
		});
	}

	@Override
	public void resize(int w, int h) {
		
	}

	@Override
	public void update(float delta) {
		winStage.act();
	}

	@Override
	public void render() {
		winStage.draw();
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
	

	}

	@Override
	public void resume() {
	

	}

}
