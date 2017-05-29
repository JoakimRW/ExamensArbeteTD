package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.GameStateManager.State;
import com.mygdx.game.view.stages.TutorialStage;

public class TutorialState extends GameState {
	
	private TutorialStage _tutorialStage;
	
	public TutorialState(GameStateManager gsm) {
		super(gsm);
		_tutorialStage = new TutorialStage();
		Gdx.input.setInputProcessor(_tutorialStage);
		_tutorialStage.getBackBtn().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				gsm.setState(State.MAINMENU);
			}
		});
	}

	@Override
	public void resize(int w, int h) {
		_tutorialStage.getViewport().update(w, h);
		_tutorialStage.getViewport().apply(true);
	}

	@Override
	public void update(float delta) {
		_tutorialStage.act(delta);
	}

	@Override
	public void render() {
		_tutorialStage.draw();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

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
