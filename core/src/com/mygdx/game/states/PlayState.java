package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.stages.GameStage;
import com.mygdx.game.stages.UiStage;

public class PlayState extends GameState {

	private GameStage gameStage;
	private UiStage uiStage;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		gameStage = new GameStage(gsm);
		uiStage = new UiStage(gsm);
		Gdx.input.setInputProcessor(gameStage);
	}

	@Override
	public void update(float delta) {
		gameStage.act();
		uiStage.act();
		
	}

	@Override
	public void render() {
		gameStage.draw();
		uiStage.draw();
		
	}

	@Override
	public void dispose() {
		gameStage.dispose();
		uiStage.dispose();
	}

}
