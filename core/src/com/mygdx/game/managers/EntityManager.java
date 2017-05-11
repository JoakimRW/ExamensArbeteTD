package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.controllers.EntityModel;
import com.mygdx.game.controllers.UIStageController;
import com.mygdx.game.entites.input.InputHandler;
import com.mygdx.game.entites.systems.CameraMovementSystem;
import com.mygdx.game.entites.systems.CoinSystem;
import com.mygdx.game.entites.systems.HealthSystem;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.PlayerInputSystem;
import com.mygdx.game.entites.systems.PlayerStatSystem;
import com.mygdx.game.entites.systems.ProjectileMovementSystem;
import com.mygdx.game.entites.systems.RenderSystem;
import com.mygdx.game.entites.systems.ShootingSystem;
import com.mygdx.game.entites.systems.TargetFinderSystem;
import com.mygdx.game.entites.systems.TowerPlacementSystem;
import com.mygdx.game.entites.systems.TowerSelectionSystem;
import com.mygdx.game.stages.UiView;
import com.mygdx.game.states.PlayState;

public class EntityManager {
	private final CoinSystem coinSystem;
	private EntityFactory _entityFactory;
	private UIStageController uiController;
	private Engine _ashleyEngine;
	private WaveTimeManager _waveManager;
	private InputHandler inputhandler;

	public EntityManager(Engine ashleyEngine, SpriteBatch batch, OrthographicCamera gameCamera,
			InputHandler inputhandler, UiView _uiView, GameStateManager gsm) {
		this._ashleyEngine = ashleyEngine;
		this.inputhandler = inputhandler;

		_entityFactory = new EntityFactory(ashleyEngine);
		_waveManager = new WaveTimeManager(_entityFactory);
		EntityModel _entityModel = new EntityModel(_waveManager, _entityFactory, gsm, gameCamera, ashleyEngine);
		uiController = new UIStageController(_uiView, _entityModel, gsm);
		_entityFactory.createPlayerEntity();

		MoveToSystem moveToSystem = new MoveToSystem(gameCamera);
		coinSystem = new CoinSystem(gameCamera);
		PlayerStatSystem statSystem = new PlayerStatSystem(uiController, _entityModel);
		TowerPlacementSystem towerSystem = new TowerPlacementSystem(batch);
		ShootingSystem shootingSystem = new ShootingSystem();
		ProjectileMovementSystem projectileMovementSystem = new ProjectileMovementSystem();
		TargetFinderSystem targetFinderSystem = new TargetFinderSystem();
		RenderSystem renderSystem = new RenderSystem(batch);
		HealthSystem healthSystem = new HealthSystem(batch, _entityFactory);
		PlayerInputSystem playerInputSys = new PlayerInputSystem();
		inputhandler.registerInputHandlerSystem(playerInputSys);
		CameraMovementSystem camSys = new CameraMovementSystem(gameCamera);
		TowerSelectionSystem towerSelectSystem = new TowerSelectionSystem(_entityFactory, gameCamera);
		ashleyEngine.addSystem(statSystem);
		ashleyEngine.addSystem(towerSelectSystem);
		ashleyEngine.addSystem(moveToSystem);
		ashleyEngine.addSystem(towerSystem);
		ashleyEngine.addSystem(renderSystem);
		ashleyEngine.addSystem(healthSystem);
		ashleyEngine.addSystem(playerInputSys);
		ashleyEngine.addSystem(camSys);
		ashleyEngine.addSystem(coinSystem);
		ashleyEngine.addSystem(shootingSystem);
		ashleyEngine.addSystem(projectileMovementSystem);
		ashleyEngine.addSystem(targetFinderSystem);

	}

	public void update(float deltaTime) {
		if (PlayState.PAUSE) {
			uiController.showPauseWindow();
		} else {
			uiController.hidePauseWindow();
			inputhandler.pullInput();
			_waveManager.tick(deltaTime);
			_ashleyEngine.update(deltaTime);
		}
	}

	public void dispose() {
		coinSystem.dispose();
		_entityFactory = null;
		_waveManager = null;
	}
}
