package com.mygdx.game.entites.systems;

import java.util.Collections;
import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.Mappers;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.tower.RangeComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TargetComponent;

public class AimingSystem extends IteratingSystem {

	public AimingSystem() {
		super(Families.TOWER);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		Entity nearestEnemy = null;
		TargetComponent targetComponent = Mappers.TARGET_M.get(entity);
		if (targetComponent == null || outOfRange(entity, targetComponent.getTarget())) {
			nearestEnemy = findNearestEnemy(entity);
		} else {
			return;
		}

		if (nearestEnemy == null || outOfRange(entity, nearestEnemy)) {
			return;
		}
		setNewTarget(entity, nearestEnemy);
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

			double distance = towerPosition.dst(enemyPosition);
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
		if (range < minKey) {
			return null;
		}
		return distanceMap.get(minKey);

	}

	private static boolean outOfRange(Entity tower, Entity target) {
		if (tower == null || target == null) {
			return true;
		}
		return Mappers.POSITION_M.get(tower).position.dst(Mappers.POSITION_M.get(target).position) > Mappers.RANGE_M
				.get(tower).getRange();
	}

	private static void setNewTarget(Entity tower, Entity target) {
		tower.getComponent(TargetComponent.class).setTarget(target);
	}

}
