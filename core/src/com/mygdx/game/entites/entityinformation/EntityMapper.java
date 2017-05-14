package com.mygdx.game.entites.entityinformation;

import java.util.HashMap;
import java.util.Map;

import com.mygdx.game.Factory.EnemyName;
import com.mygdx.game.Factory.ProjectileType;
import com.mygdx.game.Factory.TowerType;

public class EntityMapper {

	BasicLaserTurret _basicLaserTurret = new BasicLaserTurret();
	PlastmaTower _plastmaTower = new PlastmaTower();

	Bloodworm _bloodWorm = new Bloodworm();
	Bird _bird = new Bird();

	LaserProjectile _laserProjectile = new LaserProjectile();
	PlastmaProjectile _plastmaProjectile = new PlastmaProjectile();

	private Map<TowerType, EntityInformation> _towerMapper = new HashMap<>();
	private Map<EnemyName, EntityInformation> _enemyMapper = new HashMap<>();
	private Map<ProjectileType, EntityInformation> _projectileMapper = new HashMap<>();
	

	public EntityMapper() {
		_towerMapper.put(TowerType.BASIC_LASER_TURRET, _basicLaserTurret);
		_towerMapper.put(TowerType.PLASTMA_TOWER, _plastmaTower);

		_enemyMapper.put(EnemyName.BLOODWORM, _bloodWorm);
		_enemyMapper.put(EnemyName.BIRD, _bird);

		_projectileMapper.put(ProjectileType.LASER, _laserProjectile);
		
		_plastmaProjectile.setIsSplash(true);
		_plastmaProjectile.setSplashRadius(_plastmaTower.getSplashRadius());
		_projectileMapper.put(ProjectileType.PLASTMA, _plastmaProjectile);
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
