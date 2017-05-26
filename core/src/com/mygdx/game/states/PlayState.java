package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.input.InputHandler;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.managers.GameStateManager.State;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.view.stages.UiStage;

public class PlayState extends GameState {

	public static boolean GAME_OVER = false;
	public static boolean START_GAME = false;
	public static boolean PAUSE = false;
	public static int CURRENT_LIVING_ENEMIES = 0;
	public static int MAX_WAVES = 20;
	private UiStage _uiStage;
	private EntityManager _entityManager;
	private OrthographicCamera _gameCamera;
	private OrthogonalTiledMapRenderer _renderer;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		Assets.loadGameStageAssets();
		Engine ashleyEngine = new Engine();
		LevelManager.loadLevel("maps/simple-map.tmx");
		_renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
		_gameCamera = new OrthographicCamera();
		EntityFactory entityFactory = new EntityFactory(ashleyEngine);
		InputHandler inputhandler = new InputHandler(_gameCamera,entityFactory,gsm,ashleyEngine);
		// behöver deklarera uiview här för att registrera inputprocessor
		_uiStage = new UiStage();
		_entityManager = new EntityManager(ashleyEngine, _batch, _gameCamera, inputhandler, _uiStage, gsm, entityFactory);

		InputMultiplexer multi = new InputMultiplexer();


		multi.addProcessor(_uiStage.getStage());
		multi.addProcessor(inputhandler);
		Gdx.input.setInputProcessor(multi);

	}

	@Override
	public void resize(int w, int h) {
	
		_uiStage.resize(w, h);
		_gameCamera.setToOrtho(false, w, h);
	}

	@Override
	public void update(float delta) {
		if(GAME_OVER){
			_gsm.setState(State.LOSE);
			WaveTimeManager.WAVE = 0;
	        GAME_OVER = false;
	        START_GAME = false;
	        PAUSE = false;
		}
		if(WaveTimeManager.WAVE == MAX_WAVES && CURRENT_LIVING_ENEMIES <= 0){
			System.out.println("won game");
			_gsm.setState(State.WIN);
			WaveTimeManager.WAVE = 0;
	        GAME_OVER = false;
	        START_GAME = false;
	        PAUSE = false;
		}
	}

	@Override
	public void render() {
		_renderer.setView(_gameCamera);
		_renderer.render();
		_batch.setProjectionMatrix(_gameCamera.combined);
		_uiStage.getStage().act(Gdx.graphics.getDeltaTime());
		_entityManager.update(Gdx.graphics.getDeltaTime());
		_uiStage.draw();
	}

	@Override
	public void dispose() {
        Gdx.input.setInputProcessor(null);
        _entityManager.dispose();
        LevelManager.dispose();
        Assets.dispose();
    }

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
}
