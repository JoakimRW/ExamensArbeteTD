package com.mygdx.game.entites.systems;

import java.util.Collections;
import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.tower.RangeComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TargetComponent;

public class TargetFinderSystem extends IntervalIteratingSystem {

	public TargetFinderSystem() {
		super(Families.TOWER, 0.1f);
	}

	@Override
	protected void processEntity(Entity entity) {

		Entity nearestEnemy = findNearestEnemy(entity);
		if (nearestEnemy == null) {
			return;
		}
		System.out.println("Nearest enemy = " + nearestEnemy);
		entity.getComponent(TargetComponent.class).setTarget(nearestEnemy);

	}

	private Entity findNearestEnemy(Entity towerEntity) {
		Vector2 towerPosition = towerEntity.getComponent(PositionComponent.class).position;

		if (getEngine().getEntitiesFor(Families.ENEMY) == null) {
			return null;
		}
		ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(Families.ENEMY);
		HashMap<Double, Entity> distanceMap = new HashMap<>();
		for (Entity enemy : enemies) {
			Vector2 enemyPosition = enemy.getComponent(PositionComponent.class).position;
			double distance = compareDistance(towerPosition, enemyPosition);
			distanceMap.put(distance, enemy);
		}
		if (distanceMap.isEmpty()) {
			return null;
		}

		Double minKey = Collections.min(distanceMap.keySet());
		RangeComponent component = towerEntity.getComponent(RangeComponent.class);
		if (component == null) {
			return null;
		}
		Double range = component.getRange();
		if (range > minKey) {
			return null;
		}
		return distanceMap.get(minKey);

	}

	private static double compareDistance(Vector2 towerPosition, Vector2 enemyPosition) {

		float dx = towerPosition.x - enemyPosition.x;
		float dy = towerPosition.y - enemyPosition.y;
		return Math.sqrt(dx * dx + dy * dy);

	}

}
