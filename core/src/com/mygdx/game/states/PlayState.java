package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.stages.GameStage;
import com.mygdx.game.stages.UiStage;

public class PlayState extends GameState {

	private GameStage _gameStage;
	private UiStage _uIStage;
	PlayStateHelper _playStateHelper;
	DragAndDrop _dragAndDrop = new DragAndDrop();
	EntityManager _entityManager;

	public PlayState(GameStateManager gsm, Engine ashleyEngine) {
		super(gsm);
		ashleyEngine.update(Gdx.graphics.getDeltaTime());
		LevelManager.loadLevel("maps/simple-map.tmx");
		_entityManager = new EntityManager(ashleyEngine, _batch);
		_gameStage = new GameStage(gsm,ashleyEngine,_entityManager,_batch);
		_uIStage = new UiStage(_entityManager,_batch);
		
		_playStateHelper = new PlayStateHelper(_batch, _gameStage, _uIStage, _dragAndDrop, ashleyEngine);

		Table table = _uIStage.getTable();
		_playStateHelper.UiStageControl(table);

		InputMultiplexer multi = new InputMultiplexer();
		multi.addProcessor(_gameStage);
		multi.addProcessor(_uIStage);
		Gdx.input.setInputProcessor(multi);
	}

	@Override
	public void update(float delta) {
		// batch.setProjectionMatrix(game.getCamera().combined);
		_gameStage.act();
		_uIStage.act();
	}

	@Override
	public void render() {
		// batch.setProjectionMatrix(game.getCamera().combined);
		_gameStage.draw();
		_uIStage.draw();
	}

	@Override
	public void dispose() {
		_gameStage.dispose();
		_uIStage.dispose();
	}

}
