package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Family;
import com.mygdx.game.entites.entitiycomponents.player.PlayerComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.ProjectileComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TowerComponent;

public class Families {
    public final static Family ENEMY = Family.all(SkeletonComponent.class , PathComponent.class).get();
    public final static Family PLAYER = Family.one(PlayerComponent.class).get();
    public final static Family TOWER = Family.all(TowerComponent.class).get();
    public final static Family RENDERABLE = Family.all(RenderableComponent.class, PositionComponent.class , SkeletonComponent.class).get();
    public final static Family PROJECTILE = Family.all(ProjectileComponent.class).get();

}
