package com.mygdx.game.controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.entites.input.InputHandler;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.WaveTimeManager;

public class EntityModel extends InputAdapter {

	private WaveTimeManager waveMngr;
	private static EntityFactory _factory;
	private static GameStateManager _gsm;
	private static OrthographicCamera _gameCamera;

	public EntityModel(WaveTimeManager waveMngr, EntityFactory factory, GameStateManager gsm, OrthographicCamera gameCamera) {

		this.waveMngr = waveMngr;
		_factory = factory;
		_gsm = gsm;
		_gameCamera = gameCamera;
	}

	public static void startNextWave() {
		WaveTimeManager.CURRENT_WAVE_TIME = 1;
	}

	/** when player has pressed a tower icon this method is called **/
	public static void beginTowerPlacing(TowerType towerType) {

		System.out.println("TURRET TYPE =  " + towerType);
		InputHandler.setTowerInfoForPlacement(true, _gsm, _factory, towerType,_gameCamera);

	}

	public String getNextWave() {
		return waveMngr.getEnemyType() != null ? waveMngr.getEnemyType().toString() : "";
	}
}
