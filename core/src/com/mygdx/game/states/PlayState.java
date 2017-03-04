package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.stages.GameStage;
import com.mygdx.game.stages.UiStage;

public class PlayState extends GameState {

	private GameStage gameStage;
	private UiStage uIStage;
	private Engine ashleyEngine;
	PlayStateHelper playStateHelper;
	private EntityManager entityManager;


	public PlayState(GameStateManager gsm) {
		super(gsm);
		gameStage = new GameStage(gsm);
		uIStage = new UiStage(gsm);
		playStateHelper = new PlayStateHelper();
		
		Table table = uIStage.getTable();
		PlayStateHelper.UiStageControl(table);
		
		InputMultiplexer multi = new InputMultiplexer();
		multi.addProcessor(gameStage);
		multi.addProcessor(uIStage);
		Gdx.input.setInputProcessor(multi);
	}

	@Override
	public void update(float delta) {
		batch.setProjectionMatrix(game.getCamera().combined);
		gameStage.act();
		uIStage.act();
	}

	@Override
	public void render() {
		batch.setProjectionMatrix(game.getCamera().combined);
		gameStage.draw();
		uIStage.draw();
	}

	@Override
	public void dispose() {
		gameStage.dispose();
		uIStage.dispose();
	}

}
