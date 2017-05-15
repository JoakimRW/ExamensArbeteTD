package com.mygdx.game.entites.systems;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.ProjectileType;
import com.mygdx.game.entites.entitiycomponents.AngleComponent;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.Mappers;
import com.mygdx.game.entites.entitiycomponents.MouseImageComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.TimeComponent;
import com.mygdx.game.entites.entitiycomponents.enemy.EnemyComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;
import com.mygdx.game.entites.entitiycomponents.tower.FireRateComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TargetComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TowerStatComponent;

public class ShootingSystem extends IteratingSystem {
	private EntityFactory _entityFactory;

	public ShootingSystem(EntityFactory entityFactory) {
		super(Families.TOWER);
		_entityFactory = entityFactory;
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TargetComponent targetComponent = Mappers.TARGET_M.get(entity);
		int i = 0;

		if (targetComponent.isMultiTarget()) {
			ArrayList<Entity> targets = targetComponent.getTargets();
			for (Entity target : targets) {
				targetComponent.setTarget(target);
				fireAtNearestEnemies(entity, deltaTime);
			}

		} else {

			fireAtNearestEnemy(entity, deltaTime);
		}
	}

	private void fireAtNearestEnemies(Entity towerEntity, float deltaTime) {
		// get tower type to know witch projectile to spawn
		TowerStatComponent towerStat = Mappers.TOWER_STATS_M.get(towerEntity);
		if (towerEntity.getComponent(MouseImageComponent.class) != null) {
			return;
		}
		TargetComponent targetComponent = Mappers.TARGET_M.get(towerEntity);
		Entity target = targetComponent.getTarget();

		if (target == null || target.getComponent(EnemyComponent.class) == null) {
			return;
		}

		AngleComponent angle = Mappers.ANGLE_M.get(towerEntity);
		PositionComponent towerPos = Mappers.POSITION_M.get(towerEntity);
		PositionComponent targetPos = Mappers.POSITION_M.get(target);
		TimeComponent time = Mappers.TIME_M.get(towerEntity);
		DamageComponent dmg = Mappers.DAMAGE_M.get(towerEntity);
		if (targetPos != null) {
			double difX = targetPos.position.x - towerPos.position.x;
			double difY = targetPos.position.y - towerPos.position.y;
			// set direction
			float angleGoal = (float) Math.toDegrees(Math.atan2(difY, difX));
			angle.spriteAngle = MathUtils.lerpAngleDeg(angle.spriteAngle, angleGoal,
					Gdx.graphics.getDeltaTime() + 0.2f);
		}

		double range = Mappers.RANGE_M.get(towerEntity).getRange();
		float distance = towerPos.position.dst(targetPos.position);

		if (distance > range) {
			return;
		}

		time.time += deltaTime;
		if (time.time > 1 / towerEntity.getComponent(FireRateComponent.class)._fireRate) {

			switch (towerStat._towerType) {
			case MISSILE_TURRET:
				ArrayList<Entity> targets = targetComponent.getTargets();

				for (Entity entity : targets) {
					_entityFactory.createProjectileEntity(ProjectileType.MISSLE, towerEntity, entity, dmg.getDamage());
				}
				break;
			default:
				break;
			}

			time.time = 0;
		}
	}

	private void fireAtNearestEnemy(Entity towerEntity, float deltaTime) {
		// get tower type to know witch projectile to spawn
		TowerStatComponent towerStat = Mappers.TOWER_STATS_M.get(towerEntity);
		if (towerEntity.getComponent(MouseImageComponent.class) != null) {
			return;
		}
		Entity target = Mappers.TARGET_M.get(towerEntity).getTarget();

		if (target == null || target.getComponent(EnemyComponent.class) == null) {
			return;
		}

		AngleComponent angle = Mappers.ANGLE_M.get(towerEntity);
		PositionComponent towerPos = Mappers.POSITION_M.get(towerEntity);
		PositionComponent targetPos = Mappers.POSITION_M.get(target);
		TimeComponent time = Mappers.TIME_M.get(towerEntity);
		DamageComponent dmg = Mappers.DAMAGE_M.get(towerEntity);
		if (targetPos != null) {
			double difX = targetPos.position.x - towerPos.position.x;
			double difY = targetPos.position.y - towerPos.position.y;
			// set direction
			float angleGoal = (float) Math.toDegrees(Math.atan2(difY, difX));
			angle.spriteAngle = MathUtils.lerpAngleDeg(angle.spriteAngle, angleGoal,
					Gdx.graphics.getDeltaTime() + 0.2f);
		}

		double range = Mappers.RANGE_M.get(towerEntity).getRange();
		float distance = towerPos.position.dst(targetPos.position);

		if (distance > range) {
			return;
		}

		time.time += deltaTime;
		if (time.time > 1 / towerEntity.getComponent(FireRateComponent.class)._fireRate) {

			switch (towerStat._towerType) {
			case BASIC_LASER_TURRET:
				_entityFactory.createProjectileEntity(ProjectileType.LASER, towerEntity, target, dmg.getDamage());
				break;
			case PLASTMA_TOWER:
				_entityFactory.createProjectileEntity(ProjectileType.PLASTMA, towerEntity, target, dmg.getDamage());
				break;
			case MISSILE_TURRET:
				_entityFactory.createProjectileEntity(ProjectileType.MISSLE, towerEntity, target, dmg.getDamage());
				break;
			default:
				break;
			}

			time.time = 0;
		}
	}
}
