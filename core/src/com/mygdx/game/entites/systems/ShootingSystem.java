package com.mygdx.game.entites.systems;

import java.util.Collections;
import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.ProjectileType;
import com.mygdx.game.entites.entitiycomponents.AngleComponent;
import com.mygdx.game.entites.entitiycomponents.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.VelocityComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;
import com.mygdx.game.entites.entitiycomponents.tower.RangeComponent;

public class ShootingSystem extends IteratingSystem {

	private Engine _engine;

	public ShootingSystem() {
		super(Families.TOWER);
		_engine = getEngine();

	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		if (Families.TOWER.matches(entity)) {
			fireAtNearestEnemy(entity, deltaTime);
		}
	}

	private void fireAtNearestEnemy(Entity towerEntity, float deltaTime) {

		Entity nearestEnemy;
		try {
			nearestEnemy = findNearestEnemy(towerEntity);
			if (nearestEnemy == null) {
				return;
			}
		} catch (OutOfRangeException e) {
			return;
		}

		Entity projectileEntity = EntityFactory.createProjectileEntity(ProjectileType.LASER, towerEntity, nearestEnemy);

		moveToEnemy(projectileEntity, deltaTime);

	}

	private void moveToEnemy(Entity projectileEntity, float deltaTime) {

		Vector2 position = projectileEntity.getComponent(PositionComponent.class).position;
		AngleComponent angle = projectileEntity.getComponent(AngleComponent.class);
		VelocityComponent velocity = projectileEntity.getComponent(VelocityComponent.class);
		Vector2 destination = projectileEntity.getComponent(DestinationComponent.class).getDestinationEntity()
				.getComponent(PositionComponent.class).position;
		double damage = projectileEntity.getComponent(DamageComponent.class).getDamage();

		float destinationX = destination.x;
		float destinationY = destination.y;
		double difX = destinationX - position.x;
		double difY = destinationY - position.y;
		// set direction
		float spriteAngle = (float) Math.toDegrees(Math.atan2(difX, -difY));
		float entityAngle = (float) Math.toDegrees(Math.atan2(difY, difX));
		angle.spriteAngle = spriteAngle;
		angle.angle = entityAngle;

		if (destination.len() > 0)
			destination = destination.nor();
		velocity.velocity.x = destination.x * velocity.maxSpeed;
		velocity.velocity.y = destination.y * velocity.maxSpeed;

		position.x += velocity.velocity.x * deltaTime;
		position.y += velocity.velocity.y * deltaTime;

		if (position == destination) {
			dealDamage(projectileEntity, damage);
		}

	}

	private void dealDamage(Entity projectileEntity, double damage) {
		projectileEntity.getComponent(DestinationComponent.class).getDestinationEntity()
				.getComponent(HealthComponent.class).takeDamage(damage);
		_engine.removeEntity(projectileEntity);
	}

	private Entity findNearestEnemy(Entity entity) throws OutOfRangeException {
		Vector2 towerPosition = entity.getComponent(PositionComponent.class).position;

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
		Double minKey = Collections.min(distanceMap.keySet());

		Double range = entity.getComponent(RangeComponent.class).getRange();
		if (range <= minKey) {
			return distanceMap.get(minKey);
		}
		throw new OutOfRangeException();

	}

	private static double compareDistance(Vector2 towerPosition, Vector2 enemyPosition) {

		float dx = towerPosition.x - enemyPosition.x;
		float dy = towerPosition.y - enemyPosition.y;
		return Math.sqrt(dx * dx + dy * dy);

	}
}
