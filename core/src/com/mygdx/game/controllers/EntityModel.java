package com.mygdx.game.controllers;

import com.badlogic.ashley.core.Engine;
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
	private static Engine _ashleyEngine;

	public EntityModel(WaveTimeManager waveMngr, EntityFactory factory, GameStateManager gsm, OrthographicCamera gameCamera, Engine ashleyEngine) {

		this.waveMngr = waveMngr;
		_factory = factory;
		_gsm = gsm;
		_gameCamera = gameCamera;
		_ashleyEngine = ashleyEngine;
	}

	public static void startNextWave() {
		WaveTimeManager.CURRENT_WAVE_TIME = 1;
	}

	/** when player has pressed a tower icon this method is called **/
	public static void beginTowerPlacing(TowerType towerType) {

		System.out.println("TURRET TYPE =  " + towerType);
		InputHandler.setTowerInfoForPlacement(true, _gsm, _factory, towerType,_gameCamera,_ashleyEngine);

	}

	public String getNextWave() {
		return waveMngr.getEnemyType() != null ? waveMngr.getEnemyType().toString() : "";
	}
}
