package com.mygdx.game.Factory;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.AngleComponent;
import com.mygdx.game.entites.entitiycomponents.DirectionComponent;
import com.mygdx.game.entites.entitiycomponents.FlyingComponent;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.MoneyComponent;
import com.mygdx.game.entites.entitiycomponents.MouseImageComponent;
import com.mygdx.game.entites.entitiycomponents.MousePositionComponent;
import com.mygdx.game.entites.entitiycomponents.OffsetComponent;
import com.mygdx.game.entites.entitiycomponents.PathComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.RenderableComponent;
import com.mygdx.game.entites.entitiycomponents.SkeletonComponent;
import com.mygdx.game.entites.entitiycomponents.TimeComponent;
import com.mygdx.game.entites.entitiycomponents.VelocityComponent;
import com.mygdx.game.entites.entitiycomponents.enemy.EnemyComponent;
import com.mygdx.game.entites.entitiycomponents.player.PlayerComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.ProjectileComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;
import com.mygdx.game.entites.entitiycomponents.tower.FireRateComponent;
import com.mygdx.game.entites.entitiycomponents.tower.RangeComponent;
import com.mygdx.game.entites.entitiycomponents.tower.SpecialTowerComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TargetComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TowerComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TowerStatComponent;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.PathFinder;

public class EntityFactory {
	// Spawning location
	private final float _spawnX = (LevelManager.tileSpawn.getCords().x + 16) / 32;
	private final float _spawnY = (LevelManager.tileSpawn.getCords().y + 16) / 32;
	// End location
	private final float _endX = (LevelManager.tileEnd.getCords().x + 16) / 32;
	private final float _endY = (LevelManager.tileEnd.getCords().y + 16) / 32;
	private Engine _engine;
	private Entity player;

	public EntityFactory(Engine engine) {
		_engine = engine;
	}

	public void createEnemyEntity(EnemyName enemy) {
		Entity entity = null;
		switch (enemy) {
		case BIRD:
			entity = createBirdEntity();
			break;

		case BLOODWORM:
			entity = createBloodWormEntity();
			break;
		}
		if (entity != null) {
			_engine.addEntity(entity);
		} else
			System.out.println("Failed to create entity in entity factory :: cause :: entity is null");
	}

	public Entity createTowerEntity(TowerType towerType, float x, float y) {

		switch (towerType) {
		case BASIC_LASER_TURRET:
			Entity turretEntity = createLaserTurret(x, y);
			_engine.addEntity(turretEntity);
			return turretEntity;
		default:
			System.out.println("Failed to create entity in entity factory :: cause :: entity is null");
			return null;
		}
	}

	public static Entity createProjectileEntity(ProjectileType projectileType, Entity startEntity,
			Entity targetEntity) {
		Entity entity = new Entity();
		SkeletonComponent skeletonComponent = new SkeletonComponent(Assets.coinSkeleton); // TODO
		PositionComponent positionComponent = new PositionComponent(
				startEntity.getComponent(PositionComponent.class).position.cpy());
		RenderableComponent renderableComponent = new RenderableComponent();
		AngleComponent angleComponent = new AngleComponent();
		DestinationComponent destinationComponent = new DestinationComponent(targetEntity);
		VelocityComponent velocityComponent = new VelocityComponent(600f); // TODO
		DamageComponent damageComponent = new DamageComponent(20d); // TODO
		ProjectileComponent projectileComponent = new ProjectileComponent();

		skeletonComponent.skeleton.setPosition(positionComponent.position.x, positionComponent.position.y);
		skeletonComponent.animationState.setData(Assets.coinAnimationState.getData());// TODO
		entity.add(projectileComponent) //
				.add(destinationComponent) //
				.add(angleComponent) //
				.add(renderableComponent)//
				.add(positionComponent) //
				.add(skeletonComponent) //
				.add(velocityComponent)//
				.add(damageComponent);

		return entity;
	}

	private static Entity createLaserTurret(float x, float y) {
		Entity entity = new Entity();
		SkeletonComponent skeletonComponent = new SkeletonComponent(Assets.laserTowerSkeleton);
		PositionComponent positionComponent = new PositionComponent(new Vector2(x, y));
		RenderableComponent renderableComponent = new RenderableComponent();
		AngleComponent angleComponent = new AngleComponent();
		MouseImageComponent mouseImageComponent = new MouseImageComponent();
		MousePositionComponent mousePositionComponent = new MousePositionComponent();
		RangeComponent rangeComponent = new RangeComponent(100d); // TODO
		TowerComponent towerComponent = new TowerComponent();
		TowerStatComponent towerStatComponent = new TowerStatComponent(25, "Laser Tower", TowerType.BASIC_LASER_TURRET);
		SpecialTowerComponent specialTowerComponent = new SpecialTowerComponent();
		TargetComponent targetComponent = new TargetComponent();
		TimeComponent timeComponent = new TimeComponent(0);
		skeletonComponent.skeleton.setPosition(x, y);
		skeletonComponent.animationState.setData(Assets.laserTowerAnimationState.getData());
		entity.add(skeletonComponent)//
				.add(mouseImageComponent) //
				.add(mousePositionComponent)//
				.add(positionComponent)//
				.add(new FireRateComponent(1d , 0.1))// 10 percent bonus on upgrade
				.add(positionComponent)//
				.add(angleComponent).add(renderableComponent)//
				.add(rangeComponent)//
				.add(specialTowerComponent)//
				.add(new DamageComponent(20d))//
				.add(towerComponent)//
				.add(towerStatComponent)//
				.add(targetComponent)
				.add(timeComponent);
		System.out.println("Laser tower Created");
		return entity;
	}

	private Entity createBloodWormEntity() {
		// Components
		Entity entity = new Entity();
		OffsetComponent ocomp = new OffsetComponent(16, 16);
		PathComponent pComp = new PathComponent(false, false);
		SkeletonComponent skeletonComp = new SkeletonComponent(Assets.bloodWormSkeleton);
		PositionComponent positionComponent = new PositionComponent(
				new Vector2(_spawnX * 32 - ocomp.offsetX, _spawnY * 32 - ocomp.offsetY));
		HealthComponent healthComponent = new HealthComponent(100);
		VelocityComponent velocityComponent = new VelocityComponent(75f);
		DirectionComponent directionComponent = new DirectionComponent();
		AngleComponent angleComponent = new AngleComponent();
		RenderableComponent renderableComponent = new RenderableComponent();
		ArrayList<Node> path = PathFinder.findPath(new Vector2(_spawnX, _spawnY), new Vector2(_endX, _endY),
				pComp.canGoDiag, pComp.isFlying);
		skeletonComp.animationState.setData(Assets.bloodWormAnimationState.getData());
		skeletonComp.skeleton.setPosition(_spawnX * 32, (_spawnY) * 32);
		skeletonComp.animationState.setAnimation(0, "MOVING", true);
		pComp.path = path;
		entity.add(pComp).add(positionComponent).add(skeletonComp).add(healthComponent).add(velocityComponent)
				.add(directionComponent).add(renderableComponent).add(ocomp).add(angleComponent)
				.add(new EnemyComponent());
		return entity;
	}

	private Entity createBirdEntity() {
		Entity entity = new Entity();
		OffsetComponent ocomp = new OffsetComponent(16, 0);
		PathComponent pComp = new PathComponent(true, true);
		SkeletonComponent skeletonComp = new SkeletonComponent(Assets.birdSkeleton);
		PositionComponent positionComponent = new PositionComponent(new Vector2(_spawnX * 32, _spawnY * 32));
		HealthComponent healthComponent = new HealthComponent(100);
		VelocityComponent velocityComponent = new VelocityComponent(100f);
		DirectionComponent directionComponent = new DirectionComponent();
		AngleComponent angleComponent = new AngleComponent();
		RenderableComponent renderableComponent = new RenderableComponent();
		ArrayList<Node> path = new ArrayList<>();
		path.add(new Node(new Vector2(_spawnX, _spawnY), null, 0, 0));
		path.add(new Node(new Vector2(_endX, _endY), null, 0, 0));
		skeletonComp.animationState.setData(Assets.birdAnimationState.getData());
		skeletonComp.skeleton.setPosition(_spawnX * 32, _spawnY * 32);
		skeletonComp.animationState.setAnimation(0, "MOVING", true);
		pComp.path = path;
		entity.add(pComp).add(positionComponent).add(skeletonComp).add(healthComponent).add(velocityComponent)
				.add(directionComponent).add(renderableComponent).add(ocomp).add(new FlyingComponent())
				.add(angleComponent).add(new EnemyComponent());
		return entity;
	}

	public void createCoinEntity(float x, float y, int moneyValue) {
		Entity entity = new Entity();
		OffsetComponent ocomp = new OffsetComponent(16, 16);
		SkeletonComponent scomp = new SkeletonComponent(Assets.coinSkeleton);
		TimeComponent tcomp = new TimeComponent(0.5f);
		VelocityComponent vcomp = new VelocityComponent(25f);
		PositionComponent pcomp = new PositionComponent(new Vector2(x, y));
		MoneyComponent mcomp = new MoneyComponent(moneyValue);
		RenderableComponent rcomp = new RenderableComponent();
		scomp.animationState.setData(Assets.coinAnimationState.getData());
		entity.add(pcomp).add(vcomp).add(tcomp).add(scomp).add(ocomp).add(mcomp).add(rcomp);
		_engine.addEntity(entity);
	}

	public void createPlayerEntity() {
		// player entity
		player = new Entity();
		player.add(new DirectionComponent()).add(new MoneyComponent(100)).add(new HealthComponent(30))
				.add(new PlayerComponent()).add(new DestinationComponent(null));
		_engine.addEntity(player);
	}

	public Entity getPlayer() {
		return player;
	}
}
