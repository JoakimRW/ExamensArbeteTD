package com.mygdx.game.entites.entityinformation;

import java.util.HashMap;
import java.util.Map;

import com.mygdx.game.Factory.EnemyName;
import com.mygdx.game.Factory.ProjectileType;
import com.mygdx.game.Factory.TowerType;

public class EntityMapper {

	BasicLaserTurret _basicLaserTurret = new BasicLaserTurret();

	Bloodworm _bloodWorm = new Bloodworm();
	Bird _bird = new Bird();

	private Map<TowerType, EntityInformation> _towerMapper = new HashMap<>();
	private Map<EnemyName, EntityInformation> _enemyMapper = new HashMap<>();

	public EntityMapper() {
		_towerMapper.put(TowerType.BASIC_LASER_TURRET, _basicLaserTurret);

		_enemyMapper.put(EnemyName.BLOODWORM, _bloodWorm);
		_enemyMapper.put(EnemyName.BIRD, _bird);

		System.out.println(" basic laser turret  test" + _basicLaserTurret);
	}

	public EntityInformation getTowerInformation(TowerType towerType) {
		return _towerMapper.get(towerType);
	}

	public EntityInformation getEnemyInformation(EnemyName enemyName) {
		return _enemyMapper.get(enemyName);
	}

	public EntityInformation getProjectileInformation(ProjectileType projectileType) {
		return null;
	}
}
