package com.mygdx.game.controllers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.entites.entitiycomponents.MoneyComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;
import com.mygdx.game.entites.entitiycomponents.tower.FireRateComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TowerStatComponent;
import com.mygdx.game.entites.input.InputHandler;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

public class EntityModel extends InputAdapter {

	private WaveTimeManager waveMngr;
	private static EntityFactory _factory;
	private static GameStateManager _gsm;
	private static OrthographicCamera _gameCamera;
	private static Engine _ashleyEngine;
	private Entity _selectedTower;

	// upgrade info
	private double upgradeRange = 0;
	private double upgradeDmg = 0;
	private double upgradeFireRate = 0;
	private boolean isRangeGreenText;
	private boolean isDamageGreenText;
	private boolean isfireRateGreenText;

	public EntityModel(WaveTimeManager waveMngr, EntityFactory factory, GameStateManager gsm,
			OrthographicCamera gameCamera, Engine ashleyEngine) {

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
		InputHandler.setTowerInfoForPlacement(true,  towerType);
		Vector3 mousePos = _gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		_factory.createTowerEntity(towerType, mousePos.x, mousePos.y);

	}

	public void setSelectedTower(Entity entity) {
		_selectedTower = entity;
	}

	public Entity getSelectedTower() {
		return _selectedTower;
	}

	public String getNextWave() {
		return waveMngr.getEnemyName() != null ? waveMngr.getEnemyName().toString() : "";
	}

	public void sellSelectedTower() {
		if (_selectedTower != null) {
			Vector2 pos = _selectedTower.getComponent(PositionComponent.class).position.cpy();
			Tile tile = LevelManager.getTile((int) pos.x >> 5, (int) pos.y >> 5);
			if (tile != null) {
				tile.setEntity(null);
				tile.setType(TileType.FLOOR);
			}
			double sellValue = _selectedTower.getComponent(TowerStatComponent.class)._sellValue;
			_factory.createCoinEntity(pos.x, pos.y, (int) sellValue);
			_selectedTower.removeAll();
			_ashleyEngine.removeEntity(_selectedTower);
			setSelectedTower(null);
		}
	}

	public void upgradeSelectedTower() {
		if (_selectedTower != null) {

			DamageComponent dmgComp = _selectedTower.getComponent(DamageComponent.class);
			FireRateComponent fireRateComp = _selectedTower.getComponent(FireRateComponent.class);
			// RangeComponent rangeComp =
			// _selectedTower.getComponent(RangeComponent.class);
			TowerStatComponent stats = _selectedTower.getComponent(TowerStatComponent.class);

			int playerMoney = _factory.getPlayer().getComponent(MoneyComponent.class).money;
			int upgradePrice = (int) stats._upgradePrice;
			TowerType towerType = stats._towerType;

			if (playerMoney >= upgradePrice) {
				switch (towerType) {
				case BASIC_LASER_TURRET:
					fireRateComp._fireRate += fireRateComp.percentageIncrease * stats._towerLevel;
					dmgComp.setDamage(dmgComp.getDamage() + dmgComp.dmgIncrease * stats._towerLevel);
					break;
				default:
					return;
				}
				_factory.getPlayer().getComponent(MoneyComponent.class).money -= upgradePrice;
				_selectedTower.getComponent(TowerStatComponent.class).upgrade();
			}
		}
	}

	public double getUpgradeRange() {
		return upgradeRange;
	}

	public void setUpgradeRange(double upgradeRange) {
		this.upgradeRange = upgradeRange;
	}

	public double getUpgradeDmg() {
		return upgradeDmg;
	}

	public void setUpgradeDmg(double upgradeDmg) {
		this.upgradeDmg = upgradeDmg;
	}

	public double getUpgradeFireRate() {
		return upgradeFireRate;
	}

	public void setUpgradeFireRate(double upgradeFireRate) {
		this.upgradeFireRate = upgradeFireRate;
	}

	public void setisfireRateGreenText(boolean isfireRateGreenText) {
		this.isfireRateGreenText = isfireRateGreenText;
	}

	public boolean isRangeGreenText() {
		return isRangeGreenText;
	}

	public boolean isDamageGreenText() {
		return isDamageGreenText;
	}

	public boolean isIsfireRateGreenText() {
		return isfireRateGreenText;
	}

	public void setisDamageGreenText(boolean isDamageGreenText) {
		this.isDamageGreenText = isDamageGreenText;
	}

	public void setisRangeGreenText(boolean isRangeGreenText) {
		this.isRangeGreenText = isRangeGreenText;
	}
}
