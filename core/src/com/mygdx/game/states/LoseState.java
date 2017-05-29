package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.GameStateManager.State;
import com.mygdx.game.view.stages.LoseStage;

public class LoseState extends GameState {
	
	LoseStage loseStage;
	
	public LoseState(GameStateManager gsm) {
		super(gsm);
		loseStage = new LoseStage();
		
		Gdx.input.setInputProcessor(loseStage);
		
		loseStage.getPlayAgainBtn().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				_gsm.setState(State.PLAY);
			}
		});
		
		loseStage.getMainMenuBtn().addListener(new ClickListener(){
			 @Override
			public void clicked(InputEvent event, float x, float y) {
				 _gsm.setState(State.MAINMENU);
			}
		});
	}

	@Override
	public void resize(int w, int h) {
		loseStage.getViewport().update(w, h);
		loseStage.getViewport().apply(true);
	}

	@Override
	public void update(float delta) {
		loseStage.act();

	}

	@Override
	public void render() {
		loseStage.draw();
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
