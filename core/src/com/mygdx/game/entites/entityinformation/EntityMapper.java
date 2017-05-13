package com.mygdx.game.entites.entityinformation;

import java.util.HashMap;
import java.util.Map;

import com.mygdx.game.Factory.EnemyType;
import com.mygdx.game.Factory.ProjectileType;
import com.mygdx.game.Factory.TowerType;

public class EntityMapper {

	BasicLaserTurret basicLaserTurret = new BasicLaserTurret();
	private Map<TowerType,EntityInformation> mapper = new HashMap<>();

	public EntityMapper() {
	mapper.put(TowerType.BASIC_LASER_TURRET,basicLaserTurret);
	
	System.out.println(" basic laser turret  test"+ basicLaserTurret);
	}

	public EntityInformation getTowerInformation(TowerType towerType) {
		return mapper.get(towerType);
	}
	
	public static EntityInformation getEnemyInformation(EnemyType enemyType){
		return null;
	}
	
	public static EntityInformation getProjectileInformation(ProjectileType projectileType){
		return null;
	}
}
