package com.mygdx.game.Factory;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;
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
import com.mygdx.game.entites.entitiycomponents.SplashComponent;
import com.mygdx.game.entites.entitiycomponents.SpriteComponent;
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
import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.entites.entityinformation.EntityMapper;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.states.PlayState;
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
	private EntityMapper _entityMapper;

	public EntityFactory(Engine engine) {
		_engine = engine;
		_entityMapper = new EntityMapper();
	}

	public void createEnemyEntity(EnemyName enemy, double health) {
		Entity entity = null;
		entity = createEnemy(health, enemy);
		if (entity != null) {
			_engine.addEntity(entity);
		} else
			System.out.println("Failed to create entity in entity factory :: cause :: entity is null");
	}

	public void createTowerEntity(TowerType towerType, float x, float y) {
		createTurret(x, y, towerType);
	}

	public void createProjectileEntity(ProjectileType projectileType, Entity startEntity, Entity targetEntity,
			double damage) {
		EntityInformation information = _entityMapper.getProjectileInformation(projectileType);
		System.out.println(projectileType);
		Entity entity = new Entity();
		SpriteComponent spriteComponent = new SpriteComponent(information.getProjectileSprite());
		PositionComponent positionComponent = new PositionComponent(
				startEntity.getComponent(PositionComponent.class).position.cpy());
		RenderableComponent renderableComponent = new RenderableComponent();
		AngleComponent angleComponent = new AngleComponent();
		DestinationComponent destinationComponent = new DestinationComponent(targetEntity);
		VelocityComponent velocityComponent = new VelocityComponent((float) information.getVelocity());
		DamageComponent damageComponent = new DamageComponent(damage);
		ProjectileComponent projectileComponent = new ProjectileComponent();
		OffsetComponent offsetComp = new OffsetComponent(information.getOffsetX(), information.getOffsetY());

		spriteComponent.sprite.setPosition(positionComponent.position.x, positionComponent.position.y);
		entity.add(projectileComponent) //
				.add(destinationComponent) //
				.add(angleComponent) //
				.add(renderableComponent)//
				.add(positionComponent) //
				.add(spriteComponent) //
				.add(velocityComponent)//
				.add(offsetComp)//
				.add(damageComponent);
		if (information.isSplash())
			entity.add(new SplashComponent(information.getSplashRadius()));
		information.getSoundEffect().stop();
		information.getSoundEffect().play(Game.VOLUME_SOUNDFX);
		_engine.addEntity(entity);
	}

	private void createTurret(float x, float y, TowerType towerType) {
		EntityInformation information = _entityMapper.getTowerInformation(towerType);
		Entity entity = new Entity();
		SkeletonComponent skeletonComponent = new SkeletonComponent(information.getSkeleton());
		PositionComponent positionComponent = new PositionComponent(new Vector2(x, y));
		RenderableComponent renderableComponent = new RenderableComponent();
		AngleComponent angleComponent = new AngleComponent();
		MouseImageComponent mouseImageComponent = new MouseImageComponent();
		MousePositionComponent mousePositionComponent = new MousePositionComponent();
		RangeComponent rangeComponent = new RangeComponent(information.getRange());
		TowerComponent towerComponent = new TowerComponent();
		TowerStatComponent towerStatComponent = new TowerStatComponent(information.getCost(), information.getName(),
				towerType);
		SpecialTowerComponent specialTowerComponent = new SpecialTowerComponent();
		TargetComponent targetComponent = new TargetComponent(null , information.isMultiTarget() , information.getMaxTargets());
		TimeComponent timeComponent = new TimeComponent(0);
		OffsetComponent offsetComponent = new OffsetComponent(information.getOffsetX(), information.getOffsetY());
		skeletonComponent.skeleton.setPosition(x, y);
		skeletonComponent.animationState.setData(information.getAnimationStateData());
		targetComponent.setMultiTarget(information.isMultiTarget());

		entity.add(skeletonComponent)//
				.add(mouseImageComponent) //
				.add(mousePositionComponent)//
				.add(positionComponent)//
				.add(new FireRateComponent(information.getFireRate(), 0.1))//
				.add(positionComponent)//
				.add(angleComponent)//
				.add(renderableComponent)//
				.add(rangeComponent)//
				.add(specialTowerComponent)//
				.add(new DamageComponent(information.getDamage()))//
				.add(towerComponent)//
				.add(towerStatComponent)//
				.add(targetComponent) //
				.add(timeComponent) //
				.add(offsetComponent); //
		_engine.addEntity(entity);
	}

	private Entity createEnemy(double health, EnemyName enemy) {
		
		EntityInformation information = _entityMapper.getEnemyInformation(enemy);

		Entity entity = new Entity();
		OffsetComponent ocomp = new OffsetComponent(information.getOffsetX(), information.getOffsetY());
		PathComponent pComp = new PathComponent(information.isFlying(), information.isFlying());
		SkeletonComponent skeletonComp = new SkeletonComponent(information.getSkeleton());
		PositionComponent positionComponent = new PositionComponent(
				new Vector2(_spawnX * 32 - ocomp.offsetX, _spawnY * 32 - ocomp.offsetY));
		HealthComponent healthComponent = new HealthComponent(health);
		VelocityComponent velocityComponent = new VelocityComponent((float) information.getVelocity());
		DirectionComponent directionComponent = new DirectionComponent();
		AngleComponent angleComponent = new AngleComponent();
		RenderableComponent renderableComponent = new RenderableComponent();

		if (information.isFlying()) {
			ArrayList<Node> path = new ArrayList<>();
			path.add(new Node(new Vector2(_spawnX, _spawnY), null, 0, 0));
			path.add(new Node(new Vector2(_endX, _endY), null, 0, 0));
			pComp.path = path;
		} else {
			ArrayList<Node> path = PathFinder.findPath(new Vector2(_spawnX, _spawnY), new Vector2(_endX, _endY),
					pComp.canGoDiag, pComp.isFlying);
			pComp.path = path;
		}
		skeletonComp.animationState.setData(information.getAnimationStateData());
		skeletonComp.skeleton.setPosition(_spawnX * 32, (_spawnY) * 32);
		skeletonComp.animationState.setAnimation(0, "MOVING", true);
		entity.add(pComp)//
				.add(positionComponent)//
				.add(skeletonComp)//
				.add(healthComponent)//
				.add(velocityComponent)//
				.add(directionComponent)//
				.add(renderableComponent)//
				.add(ocomp)//
				.add(angleComponent)//
				.add(new EnemyComponent());

		if (information.isFlying()) {
			entity.add(new FlyingComponent());
		}
		PlayState.CURRENT_LIVING_ENEMIES ++;
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
		entity.add(pcomp)//
				.add(vcomp)//
				.add(tcomp)//
				.add(scomp)//
				.add(ocomp)//
				.add(mcomp)//
				.add(rcomp);
		_engine.addEntity(entity);
	}

	public void createPlayerEntity() {
		// player entity
		player = new Entity();
		player.add(new DirectionComponent())//
				.add(new MoneyComponent(250))//
				.add(new HealthComponent(30))//
				.add(new PlayerComponent())//
				.add(new DestinationComponent(null));
		_engine.addEntity(player);
	}

	public Entity getPlayer() {
		return player;
	}
}
