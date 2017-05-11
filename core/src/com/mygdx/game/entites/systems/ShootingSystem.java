package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.ProjectileType;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.MouseImageComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TargetComponent;

public class ShootingSystem extends IntervalIteratingSystem {

	public ShootingSystem() {
		super(Families.TOWER, 1);
	}

	@Override
	protected void updateInterval() {
		super.updateInterval();
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}

	@Override
	protected void processEntity(Entity entity) {

		fireAtNearestEnemy(entity);
	}

	private void fireAtNearestEnemy(Entity towerEntity) {

		if (towerEntity.getComponent(MouseImageComponent.class) != null) {
			return;
		}

		Entity target = towerEntity.getComponent(TargetComponent.class).getTarget();
		
		if (target == null) {
			System.out.println("NOTARGET");
			return;
		}
		getEngine().addEntity(EntityFactory.createProjectileEntity(ProjectileType.LASER, towerEntity, target));
	}

}
