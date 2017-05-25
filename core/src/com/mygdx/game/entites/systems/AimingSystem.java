package com.mygdx.game.entites.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		if (targetComponent.isMultiTarget()) {
			targetComponent.addTargets(findNearestEnemies(entity));
		} else {
			setNewTarget(entity, nearestEnemy);
		}
	}

	private ArrayList<Entity> findNearestEnemies(Entity towerEntity) {

		Vector2 towerPosition = towerEntity.getComponent(PositionComponent.class).position;
		TargetComponent targetComp = Mappers.TARGET_M.get(towerEntity);

		if (getEngine().getEntitiesFor(Families.ENEMY) == null) {
			return null;
		}

		RangeComponent component = towerEntity.getComponent(RangeComponent.class);
		if (component == null) {
			return null;
		}
		Double range = component.getRange();

		ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(Families.ENEMY);
		HashMap<Double, Entity> distanceMap = new HashMap<>();
		for (int i  = 0; i < enemies.size(); i++) {
			Vector2 enemyPosition = enemies.get(i).getComponent(PositionComponent.class).position;

			double distance = towerPosition.dst(enemyPosition);

			if (distance < range) {
				distanceMap.put(distance, enemies.get(i));
			}
		}
		if (distanceMap.isEmpty() || distanceMap.keySet().isEmpty()) {
			return null;
		}

		Map<Double, Entity> multiDistanceList = new HashMap<>();

		for (int i = 0; i < targetComp.getMaxTargets(); i++) {
			if (distanceMap.isEmpty() || distanceMap.keySet().isEmpty()) {
				break;
			}
			Double min = Collections.min(distanceMap.keySet());
			multiDistanceList.put(min, distanceMap.get(min));
			distanceMap.remove(min);
		}
		return new ArrayList<Entity>(multiDistanceList.values());

	}

	private Entity findNearestEnemy(Entity towerEntity) {
		Vector2 towerPosition = towerEntity.getComponent(PositionComponent.class).position;

		if (getEngine().getEntitiesFor(Families.ENEMY) == null) {
			return null;
		}
		ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(Families.ENEMY);
		HashMap<Double, Entity> distanceMap = new HashMap<>();
		for (int i = 0; i < enemies.size(); i++) {
			Vector2 enemyPosition = enemies.get(i).getComponent(PositionComponent.class).position;

			double distance = towerPosition.dst(enemyPosition);
			distanceMap.put(distance, enemies.get(i));
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
		if (tower == null || target == null || tower.getComponent(PositionComponent.class) == null
				|| tower.getComponent(RangeComponent.class) == null
				|| target.getComponent(PositionComponent.class) == null) {
			return true;
		}
		return Mappers.POSITION_M.get(tower).position.dst(Mappers.POSITION_M.get(target).position) > Mappers.RANGE_M
				.get(tower).getRange();
	}

	private static void setNewTarget(Entity tower, Entity target) {
		tower.getComponent(TargetComponent.class).setTarget(target);
	}

}
