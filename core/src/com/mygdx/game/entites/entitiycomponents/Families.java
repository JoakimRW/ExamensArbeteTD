package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Family;
import com.mygdx.game.entites.entitiycomponents.enemy.EnemyComponent;
import com.mygdx.game.entites.entitiycomponents.player.PlayerComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.ProjectileComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TowerComponent;

public class Families {
	public final static Family ENEMY = Family.one(EnemyComponent.class).get();
	public final static Family PLAYER = Family.one(PlayerComponent.class).get();
	public final static Family TOWER = Family.one(TowerComponent.class).get();
	public final static Family RENDERABLE = Family
			.all(RenderableComponent.class, PositionComponent.class, SkeletonComponent.class).get();
	public final static Family PROJECTILE = Family.one(ProjectileComponent.class).get();
	public final static Family SPRITE = Family.all(SpriteComponent.class , AngleComponent.class , PositionComponent.class ).get();
	public final static Family FLYING = Family.one(FlyingComponent.class).get();

}
