package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.controllers.EntityModel;
import com.mygdx.game.controllers.UIStageController;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.systems.AimingSystem;
import com.mygdx.game.entites.systems.CameraMovementSystem;
import com.mygdx.game.entites.systems.CoinSystem;
import com.mygdx.game.entites.systems.HealthSystem;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.PlayerInputSystem;
import com.mygdx.game.entites.systems.PlayerStatSystem;
import com.mygdx.game.entites.systems.ProjectileMovementSystem;
import com.mygdx.game.entites.systems.RenderSystem;
import com.mygdx.game.entites.systems.ShootingSystem;
import com.mygdx.game.entites.systems.SplashDamageSystem;
import com.mygdx.game.entites.systems.SpriteRenderSystem;
import com.mygdx.game.entites.systems.TowerPlacementSystem;
import com.mygdx.game.entites.systems.TowerSelectionSystem;
import com.mygdx.game.input.InputHandler;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.view.stages.UiView;

public class EntityManager {
	private final CoinSystem coinSystem;
	private EntityFactory _entityFactory;
	private UIStageController uiController;
	private Engine _ashleyEngine;
	private WaveTimeManager _waveManager;
	private InputHandler inputhandler;//

	public EntityManager(Engine ashleyEngine, SpriteBatch batch, OrthographicCamera gameCamera,
			InputHandler inputhandler, UiView _uiView, GameStateManager gsm,EntityFactory entityFactory) {
		this._ashleyEngine = ashleyEngine;
		this.inputhandler = inputhandler;

		setEntityFactory(entityFactory);
		_waveManager = new WaveTimeManager(getEntityFactory());
		EntityModel _entityModel = new EntityModel(_waveManager, getEntityFactory(), gameCamera, ashleyEngine);
		uiController = new UIStageController(_uiView, _entityModel, gsm);
		getEntityFactory().createPlayerEntity();

		MoveToSystem moveToSystem = new MoveToSystem(gameCamera);
		coinSystem = new CoinSystem(gameCamera);
		PlayerStatSystem statSystem = new PlayerStatSystem(uiController, _entityModel);
		TowerPlacementSystem towerSystem = new TowerPlacementSystem(batch);
		ShootingSystem shootingSystem = new ShootingSystem(entityFactory);
		ProjectileMovementSystem projectileMovementSystem = new ProjectileMovementSystem();
		AimingSystem aimingSystem = new AimingSystem();
		RenderSystem renderSystem = new RenderSystem(batch);
		HealthSystem healthSystem = new HealthSystem(batch, getEntityFactory());
		PlayerInputSystem playerInputSys = new PlayerInputSystem();
		inputhandler.registerInputHandlerSystem(playerInputSys);
		CameraMovementSystem camSys = new CameraMovementSystem(gameCamera);
		TowerSelectionSystem towerSelectSystem = new TowerSelectionSystem(getEntityFactory(), gameCamera);
		SpriteRenderSystem spriteRenderSystem = new SpriteRenderSystem(batch);
		SplashDamageSystem splashDamageSystem = new SplashDamageSystem();
		
		ashleyEngine.addSystem(statSystem);
		ashleyEngine.addSystem(towerSelectSystem);
		ashleyEngine.addSystem(moveToSystem);
		ashleyEngine.addSystem(towerSystem);
		ashleyEngine.addSystem(spriteRenderSystem);
		ashleyEngine.addSystem(renderSystem);
		ashleyEngine.addSystem(healthSystem);
		ashleyEngine.addSystem(playerInputSys);
		ashleyEngine.addSystem(camSys);
		ashleyEngine.addSystem(coinSystem);
		ashleyEngine.addSystem(splashDamageSystem);
		ashleyEngine.addSystem(shootingSystem);
		ashleyEngine.addSystem(projectileMovementSystem);
		ashleyEngine.addSystem(aimingSystem);
		
		ashleyEngine.addEntityListener(Families.PROJECTILE,splashDamageSystem);
		ashleyEngine.addEntityListener(Families.ENEMY, projectileMovementSystem);
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
		setEntityFactory(null);
		_waveManager = null;
	}

	public EntityFactory getEntityFactory() {
		return _entityFactory;
	}

	public void setEntityFactory(EntityFactory entityFactory) {
		_entityFactory = entityFactory;
	}
}
