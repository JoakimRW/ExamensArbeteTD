package com.mygdx.game.entites.systems;

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
import com.mygdx.game.entites.entitiycomponents.tower.RangeComponent;

public class ShootingSystem extends IteratingSystem {
	public ShootingSystem() {
		super(Families.TOWER);
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		fireAtNearestEnemy(entity,deltaTime);
	}

	private void fireAtNearestEnemy(Entity towerEntity, float deltaTime) {

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
			getEngine().addEntity(EntityFactory.createProjectileEntity(ProjectileType.LASER, towerEntity, target , dmg.getDamage()));
			time.time = 0;
		}
	}
}
