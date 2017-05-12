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
import com.mygdx.game.entites.entitiycomponents.enemy.EnemyComponent;
import com.mygdx.game.entites.entitiycomponents.tower.RangeComponent;

public class ShootingSystem extends IteratingSystem {

	public ShootingSystem() {
		super(Families.TOWER, 1);
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		fireAtNearestEnemy(entity);
	}

	private void fireAtNearestEnemy(Entity towerEntity) {

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
		if (targetPos != null) {
			double difX = targetPos.position.x - towerPos.position.x;
			double difY = targetPos.position.y - towerPos.position.y;
			// set direction
			float angleGoal = (float) Math.toDegrees(Math.atan2(difY, difX));
			angle.spriteAngle = MathUtils.lerpAngleDeg(angle.spriteAngle, angleGoal,
					Gdx.graphics.getDeltaTime() + 0.2f);
		}

		double range = towerEntity.getComponent(RangeComponent.class).getRange();
		float distance = towerPos.position.dst(targetPos.position);

		if (distance > range) {
			return;
		}
		getEngine().addEntity(EntityFactory.createProjectileEntity(ProjectileType.LASER, towerEntity, target));
	}
}
