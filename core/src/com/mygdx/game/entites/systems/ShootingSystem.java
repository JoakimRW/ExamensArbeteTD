package com.mygdx.game.entites.systems;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.ProjectileType;
import com.mygdx.game.Factory.TowerType;
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
		TowerStatComponent stats = Mappers.TOWER_STATS_M.get(entity);
		
		if (stats._towerType == TowerType.MISSILE_TURRET) {
			setTurretAngle(entity);
			TimeComponent timeComp = Mappers.TIME_M.get(entity);
			ArrayList<Entity> targets = targetComponent.getTargets();
			timeComp.time += deltaTime;
			
			
			if (timeComp.time > 1 / entity.getComponent(FireRateComponent.class)._fireRate) {
				for (int i = 0; i < targets.size(); i++ ) {
					
					if(!Families.FLYING.matches(targets.get(i))) {
						targetComponent.getTargets().remove(i);
						System.out.println("target size " +  targets.size());
						continue;
					}
					targetComponent.setTarget(targets.get(i));
					fireAtNearestEnemies(entity, deltaTime);
					
				}
				timeComp.time = 0;
			}
		} else {
			if(targetComponent.getTarget() != null)
				if(Families.FLYING.matches(targetComponent.getTarget())) return;
	
			setTurretAngle(entity);
			fireAtNearestEnemy(entity, deltaTime);
		}
	}

	private void fireAtNearestEnemies(Entity towerEntity, float deltaTime) {
		// get tower type to know witch projectile to spawn
	
		if (towerEntity.getComponent(MouseImageComponent.class) != null) {
			return;
		}
		
		TargetComponent targetComponent = Mappers.TARGET_M.get(towerEntity);
		Entity target = targetComponent.getTarget();

		if (target == null || target.getComponent(EnemyComponent.class) == null) {
			return;
		}
		PositionComponent towerPos = Mappers.POSITION_M.get(towerEntity);
		PositionComponent targetPos = Mappers.POSITION_M.get(target);
		DamageComponent dmg = Mappers.DAMAGE_M.get(towerEntity);
		
		double range = Mappers.RANGE_M.get(towerEntity).getRange();
		float distance = towerPos.position.dst(targetPos.position);

		if (distance > range) {
			return;
		}
		_entityFactory.createProjectileEntity(ProjectileType.MISSLE, towerEntity, targetComponent.getTarget(), dmg.getDamage());

	}

	private void fireAtNearestEnemy(Entity towerEntity, float deltaTime) {
		// get tower type to know witch projectile to spawn
		TowerStatComponent towerStat = Mappers.TOWER_STATS_M.get(towerEntity);
		TimeComponent time = Mappers.TIME_M.get(towerEntity);
		DamageComponent dmg = Mappers.DAMAGE_M.get(towerEntity);
		Entity target = Mappers.TARGET_M.get(towerEntity).getTarget();
		PositionComponent towerPos = Mappers.POSITION_M.get(towerEntity);
		if (target == null || target.getComponent(EnemyComponent.class) == null) {
			return;
		}
		PositionComponent targetPos = Mappers.POSITION_M.get(target);
		
		if (towerEntity.getComponent(MouseImageComponent.class) != null) {
			return;
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
			default:
				break;
			}

			time.time = 0;
		}
	}
	
	public void setTurretAngle(Entity tower){
		AngleComponent angle = Mappers.ANGLE_M.get(tower);
		PositionComponent towerPos = Mappers.POSITION_M.get(tower);
		TargetComponent target = Mappers.TARGET_M.get(tower);
		TowerStatComponent stats = Mappers.TOWER_STATS_M.get(tower);
		
		if (target.getTarget() != null) {
			if(Families.FLYING.matches(target.getTarget()) && stats._towerType != TowerType.MISSILE_TURRET) return;
			PositionComponent targetPos = Mappers.POSITION_M.get(target.getTarget());
			if(targetPos == null) return;
			double difX = targetPos.position.x - towerPos.position.x;
			double difY = targetPos.position.y - towerPos.position.y;
			// set direction
			float angleGoal = (float) Math.toDegrees(Math.atan2(difY, difX));
			angle.spriteAngle = MathUtils.lerpAngleDeg(angle.spriteAngle, angleGoal,
					Gdx.graphics.getDeltaTime() + 0.2f);
		}
	}
}
