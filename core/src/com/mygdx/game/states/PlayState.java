package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygdx.game.entites.systems.InputHandler;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.stages.GameStage;
import com.mygdx.game.stages.UiStage;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

public class PlayState extends GameState {
	
	public static int PLAYER_HEALTH = 30;
	public static boolean GAME_OVER = false;
	public static boolean START_GAME = false;

	private GameStage _gameStage;
	private UiStage _uIStage;
	PlayStateHelper _playStateHelper;
	EntityManager _entityManager;
	OrthographicCamera _gameCamera;
	OrthographicCamera _uiCamera;

	public PlayState(GameStateManager gsm, Engine ashleyEngine) {
		super(gsm);
		
		ashleyEngine.update(Gdx.graphics.getDeltaTime());
		LevelManager.loadLevel("maps/simple-map.tmx");
		_gameCamera = new OrthographicCamera();
		InputHandler inputhandler = new InputHandler();
		_entityManager = new EntityManager(ashleyEngine, _batch,_gameCamera,inputhandler);
		
		_uiCamera = new OrthographicCamera();
		_gameStage = new GameStage(gsm,ashleyEngine,_entityManager,_batch);
		_uIStage = new UiStage(_entityManager,_batch,_uiCamera);
		
		_playStateHelper = new PlayStateHelper(_batch, _gameStage, _uIStage, ashleyEngine);

		Table table = _uIStage.getTable();
		_playStateHelper.UiStageControl(table);

		InputMultiplexer multi = new InputMultiplexer();
		
		multi.addProcessor(inputhandler);
		multi.addProcessor(_uIStage);
		Gdx.input.setInputProcessor(multi);
	}

	@Override
	public void update(float delta) {
		// batch.setProjectionMatrix(game.getCamera().combined);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (!START_GAME)
				System.out.println("Game Started , Spawning first Wave");
			START_GAME = true;
		}
		if (PLAYER_HEALTH == 0) {
			GAME_OVER = true;
		}
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
	TileType getMousePosInGameWorld() {
	    Vector3 mousePos = _gsm.game().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	    Tile tile = LevelManager.getTile((int)mousePos.x >> 5 ,(int) mousePos.y >> 5);
	    TileType type = TileType.WALL;
	    if(tile != null){
	        type = tile.getType() == TileType.WALL ? TileType.WALL : TileType.FLOOR;
	    }
	    return type;
	}
	

}
