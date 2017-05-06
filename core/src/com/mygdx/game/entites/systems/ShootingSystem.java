package com.mygdx.game.entites.systems;

import java.util.Collections;
import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;

public class ShootingSystem extends IteratingSystem {

	private Engine _engine;

	public ShootingSystem(Family family, Engine engine) {
		super(family);
		_engine = engine;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		if (Families.ENEMY.matches(entity)) {
			receiveFire(entity);
		}
		if (Families.TOWER.matches(entity)) {
			fireAtNearestEnemy(entity);
		}
	}

	private void fireAtNearestEnemy(Entity towerEntity) {

		Entity nearestEnemy = findNearestEnemy(towerEntity);

	}

	private Entity findNearestEnemy(Entity entity) {
		Vector2 towerPosition = entity.getComponent(PositionComponent.class).position;
		ImmutableArray<Entity> enemies = _engine.getEntitiesFor(Families.ENEMY);
		HashMap<Double, Entity> distanceMap = new HashMap<>(0, 1000000);
		for (Entity enemy : enemies) {
			Vector2 enemyPosition = enemy.getComponent(PositionComponent.class).position;
			double distance = compareDistance(towerPosition, enemyPosition);
			distanceMap.put(distance, enemy);
		}
		Double minKey = Collections.min(distanceMap.keySet());
		return distanceMap.get(minKey);
	}

	private static double compareDistance(Vector2 towerPosition, Vector2 enemyPosition) {

		float dx = towerPosition.x - enemyPosition.x;
		float dy = towerPosition.y - enemyPosition.y;
		return Math.sqrt(dx * dx + dy * dy);

	}

	private void receiveFire(Entity entity) {
		// TODO Auto-generated method stub

	}

}
