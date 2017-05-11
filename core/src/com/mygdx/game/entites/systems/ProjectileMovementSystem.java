package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.AngleComponent;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.VelocityComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;

public class ProjectileMovementSystem extends IteratingSystem {

	public ProjectileMovementSystem() {
		super(Families.PROJECTILE);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

//		System.out.println("STARTING PROJECTILE MOVEMENT");
		moveToEnemy(entity, deltaTime);
		
//		getEntities().forEach(projectile -> System.out.println("projectile nr :" + projectile.getComponent(PositionComponent.class).position));

	}

	private void moveToEnemy(Entity projectileEntity, float deltaTime) {
		
		if (projectileEntity.getComponent(DestinationComponent.class).getDestinationEntity().getComponent(PositionComponent.class) == null) {
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
		float entityAngle = (float) Math.toDegrees(Math.atan2(difY, difX));
		angle.spriteAngle = spriteAngle;
		angle.angle = entityAngle;

		if (destination.len() > 0) {
			destination = destination.nor();
		}
		velocity.velocity.x = destination.x * velocity.maxSpeed;
		velocity.velocity.y = destination.y * velocity.maxSpeed;

		position.x += velocity.velocity.x * deltaTime;
		position.y += velocity.velocity.y * deltaTime;

		

		if (position.dst(position) < 1) {
			
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

}
