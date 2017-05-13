package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.AngleComponent;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.VelocityComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;

public class ProjectileMovementSystem extends IteratingSystem implements EntityListener {

	public ProjectileMovementSystem() {
		super(Families.PROJECTILE);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		if (entity.getComponent(DestinationComponent.class).getDestinationEntity() == null) {
			getEngine().removeEntity(entity);
		}

		moveToEnemy(entity, deltaTime);

	}

	private void moveToEnemy(Entity projectileEntity, float deltaTime) {

		if (projectileEntity.getComponent(DestinationComponent.class).getDestinationEntity()
				.getComponent(PositionComponent.class) == null) {
			return;
		}

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
		float rotAng = (float) Math.toDegrees(Math.atan2(difY, difX));
		angle.spriteAngle = spriteAngle;
		angle.angle = rotAng;

		float angleX = (float) Math.cos(Math.toRadians(angle.angle));
		float angleY = (float) Math.sin(Math.toRadians(angle.angle));

		velocity.velocity.x = angleX * velocity.maxSpeed;
		velocity.velocity.y = angleY * velocity.maxSpeed;

		position.x += velocity.velocity.x * deltaTime;
		position.y += velocity.velocity.y * deltaTime;

		if (position.dst(destination) < 5) {
			System.out.println("distance reached");
			System.out.println("Distance to enemy : " + position.dst(destination));
			dealDamage(projectileEntity, damage);
		}

	}

	private void dealDamage(Entity projectileEntity, double damage) {
		System.out.println("Damage dealt : " + damage);
		projectileEntity.getComponent(DestinationComponent.class).getDestinationEntity()
				.getComponent(HealthComponent.class).takeDamage(damage);
		getEngine().removeEntity(projectileEntity);
	}

	@Override
	public void entityAdded(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityRemoved(Entity entity) {
		for (Entity projectile : getEntities()) {
			if (projectile.getComponent(DestinationComponent.class).getDestinationEntity() == entity) {
				getEngine().removeEntity(projectile);
			}
		}

	}

}
