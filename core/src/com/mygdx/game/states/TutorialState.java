package com.mygdx.game.states;

import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.view.stages.TutorialStage;

public class TutorialState extends GameState {
	
	private TutorialStage _tutorialStage;
	
	public TutorialState(GameStateManager gsm) {
		super(gsm);
		_tutorialStage = new TutorialStage();
	}

	@Override
	public void resize(int w, int h) {
		_tutorialStage.getViewport().update(w, h);
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
