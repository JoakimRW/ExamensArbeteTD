package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.entites.entitiycomponents.enemy.EnemyComponent;
import com.mygdx.game.entites.entitiycomponents.player.PlayerComponent;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.PathFinder;

public class MoveToSystem extends IteratingSystem {

	Entity player;
	// private OrthographicCamera camera;
	// private ShapeRenderer sr;

	public MoveToSystem(OrthographicCamera camera) {
		super(Families.ENEMY);
		// this.camera = camera;
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		// sr = new ShapeRenderer();
		player = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent posComp = Mappers.POSITION_M.get(entity);
		DirectionComponent dirComp = Mappers.DIRECTION_M.get(entity);
		PathComponent pathComp = Mappers.PATH_M.get(entity);
		AngleComponent angleComp = Mappers.ANGLE_M.get(entity);
		VelocityComponent velocityComp = Mappers.VELCOITY_M.get(entity);
		OffsetComponent offComp = Mappers.OFFSET_M.get(entity);
		EnemyComponent ecomp = Mappers.ENEMY_M.get(entity);

		Vector2 start = new Vector2((posComp.position.x + offComp.offsetX) / 32,
				(posComp.position.y + offComp.offsetY) / 32);
		Vector2 end = new Vector2(LevelManager.tileEnd.getCords().x / 32, LevelManager.tileEnd.getCords().y / 32);

		pathComp.pathTimer += deltaTime;
		// if entity isn't flying update path
		// flying enemies doesn't have obstacles so there's no reason for it to
		// use findPath method
		if (pathComp.pathTimer >= 0.1f && entity.getComponent(FlyingComponent.class) == null) {
			pathComp.path = PathFinder.findPath(start, end, pathComp.canGoDiag, pathComp.isFlying);
			pathComp.pathTimer = 0;
		}

		if (pathComp.path != null) {
			float distance = start.cpy().scl(32).dst(end.cpy().scl(32));
			if (distance > 32) {
				moveTo(posComp, dirComp, angleComp, deltaTime, pathComp, velocityComp);
			} else {
				entity.removeAll();
				getEngine().removeEntity(entity);
                if (ecomp != null)
				player.getComponent(HealthComponent.class).health--;
			}
		}

	}

	private static void moveTo(PositionComponent pos, DirectionComponent dir, AngleComponent angleComp, float deltaTime,
			PathComponent pathComp, VelocityComponent vel) {
		// a xy point in the path array that the entity will go to
		float pointX = pathComp.path.get(pathComp.path.size() - 1).getCordinates().x * 32;
		float pointY = pathComp.path.get(pathComp.path.size() - 1).getCordinates().y * 32;

		double difX = pointX - pos.position.x;
		double difY = pointY - pos.position.y;
		// set direction
		float goalSpritelAngle = (float) Math.toDegrees(Math.atan2(difX, -difY));
		float goalRotAng = (float) Math.toDegrees(Math.atan2(difY, difX));
		
		angleComp.spriteAngle = MathUtils.lerpAngleDeg(angleComp.spriteAngle, goalSpritelAngle, 0.2f);
		angleComp.angle = MathUtils.lerpAngleDeg(angleComp.angle , goalRotAng , 0.5f);

		float angleX = (float) Math.cos(Math.toRadians(angleComp.angle));
		float angleY = (float) Math.sin(Math.toRadians(angleComp.angle));

		dir.direction.x = angleX;
		dir.direction.y = angleY;

		if (dir.direction.len() > 0)
			dir.direction = dir.direction.nor();

		vel.velocity.x = dir.direction.x * vel.maxSpeed;
		vel.velocity.y = dir.direction.y * vel.maxSpeed;

		pos.position.x += vel.velocity.x * deltaTime;
		pos.position.y += vel.velocity.y * deltaTime;

	}
	/** linear interpolation **/
	private static float approach(float goal, float current, float deltaTime) {
		float diffrence = goal - current;
		if (diffrence > deltaTime)
			return current + deltaTime;
		if (diffrence < deltaTime)
			return current - deltaTime;
		return goal;
	}

}
