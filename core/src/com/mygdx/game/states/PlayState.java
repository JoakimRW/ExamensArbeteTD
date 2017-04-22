package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.entites.input.InputHandler;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.stages.UiView;
import com.mygdx.game.utils.Assets;

public class PlayState extends GameState {

	public static int PLAYER_HEALTH = 30;
	public static boolean GAME_OVER = false;
	public static boolean START_GAME = false;
	private final UiView _uiView;
	private EntityManager _entityManager;
	private OrthographicCamera _gameCamera;
	private OrthogonalTiledMapRenderer _renderer;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		Assets.loadGameStageAssets();
		Engine ashleyEngine = new Engine();
		ashleyEngine.update(Gdx.graphics.getDeltaTime());
		LevelManager.loadLevel("maps/simple-map.tmx");
		_renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
		_gameCamera = new OrthographicCamera();
		InputHandler inputhandler = new InputHandler();
		// behöver deklarera uiview här för att registrera inputprocessor
		_uiView = new UiView();
		_uiView.show();
		_entityManager = new EntityManager(ashleyEngine, _batch, _gameCamera, inputhandler, _uiView, gsm);

		InputMultiplexer multi = new InputMultiplexer();

		multi.addProcessor(_uiView.getStage());
		multi.addProcessor(inputhandler);
		Gdx.input.setInputProcessor(multi);

	}

	@Override
	public void resize(int w, int h) {
		_uiView.resize(w, h);
		_gameCamera.setToOrtho(false, w, h);
	}

	@Override
	public void update(float delta) {

		if (PLAYER_HEALTH == 0) {
			GAME_OVER = true;
		}
	}

	@Override
	public void render() {
		_renderer.setView(_gameCamera);
		_renderer.render();
		_batch.setProjectionMatrix(_gameCamera.combined);
		_batch.begin();
		_entityManager.update(Gdx.graphics.getDeltaTime());
		_batch.end();
		_uiView.render(Gdx.graphics.getDeltaTime());
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
