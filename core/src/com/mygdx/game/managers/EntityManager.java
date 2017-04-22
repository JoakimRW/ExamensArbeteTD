package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.controllers.EntityModel;
import com.mygdx.game.controllers.UIStageController;
import com.mygdx.game.entites.entitiycomponents.DirectionComponent;
import com.mygdx.game.entites.input.InputHandler;
import com.mygdx.game.entites.systems.CameraMovementSystem;
import com.mygdx.game.entites.systems.HealthSystem;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.PlayerInputSystem;
import com.mygdx.game.entites.systems.RenderSystem;
import com.mygdx.game.stages.UiView;
import com.mygdx.game.states.PlayState;

public class EntityManager {
	private EntityFactory _entityFactory;
	private UIStageController uiController;
	private Engine _ashleyEngine;
	private WaveTimeManager _waveManager;
//	private OrthographicCamera _gameCamera;
	private InputHandler inputhandler;

	public EntityManager(Engine ashleyEngine, SpriteBatch batch, OrthographicCamera gameCamera,
			InputHandler inputhandler, UiView _uiView, GameStateManager gsm) {

		_entityFactory = new EntityFactory(ashleyEngine);
		_waveManager = new WaveTimeManager(_entityFactory);
		EntityModel _entityModel = new EntityModel(_waveManager, _entityFactory, gsm, gameCamera,ashleyEngine);
		uiController = new UIStageController(_uiView, _entityModel);
		// player entity
		Entity playerEntity = new Entity();
		// component for camera (only need direction)
		playerEntity.add(new DirectionComponent());

		this._ashleyEngine = ashleyEngine;
//		this._gameCamera = gameCamera;
		this.inputhandler = inputhandler;
		MoveToSystem moveToSystem = new MoveToSystem();
		RenderSystem renderSystem = new RenderSystem(batch);
		HealthSystem healthSystem = new HealthSystem(batch);
		PlayerInputSystem playerInputSys = new PlayerInputSystem();
		inputhandler.registerInputHandlerSystem(playerInputSys);
		CameraMovementSystem camSys = new CameraMovementSystem(gameCamera);
		ashleyEngine.addSystem(moveToSystem);
		ashleyEngine.addSystem(renderSystem);
		ashleyEngine.addSystem(healthSystem);
		ashleyEngine.addSystem(playerInputSys);
		ashleyEngine.addSystem(camSys);
		// add player entity
		ashleyEngine.addEntity(playerEntity);
	}

	public void update(float deltaTime) {
		if (PlayState.PAUSE) {
			if (!uiController.getPauseWindow().isVisible())
				System.out.println("show pause window");
			uiController.getPauseWindow().setVisible(true);
		} else {
			if (uiController.getPauseWindow().isVisible()) {
				System.out.println("hide pause window");
				uiController.getPauseWindow().setVisible(false);
			}
			inputhandler.pullInput();
			_waveManager.tick(deltaTime);
			_ashleyEngine.update(deltaTime);
		}
	}

	public void dispose() {
		_entityFactory = null;
		_waveManager = null;
	}
}
