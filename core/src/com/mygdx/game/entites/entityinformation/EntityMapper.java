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

	LaserProjectile _laserProjectile = new LaserProjectile();

	private Map<TowerType, EntityInformation> _towerMapper = new HashMap<>();
	private Map<EnemyName, EntityInformation> _enemyMapper = new HashMap<>();
	private Map<ProjectileType, EntityInformation> _projectileMapper = new HashMap<>();

	public EntityMapper() {
		_towerMapper.put(TowerType.BASIC_LASER_TURRET, _basicLaserTurret);

		_enemyMapper.put(EnemyName.BLOODWORM, _bloodWorm);
		_enemyMapper.put(EnemyName.BIRD, _bird);

		_projectileMapper.put(ProjectileType.LASER, _laserProjectile);
	}

	public EntityInformation getTowerInformation(TowerType towerType) {
		return _towerMapper.get(towerType);
	}

	public EntityInformation getEnemyInformation(EnemyName enemyName) {
		return _enemyMapper.get(enemyName);
	}

	public EntityInformation getProjectileInformation(ProjectileType projectileType) {
		return _projectileMapper.get(projectileType);
	}
}
