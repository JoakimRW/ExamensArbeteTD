package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Family;

public class Families {
    public final static Family ENEMY = Family.all(SkeletonComponent.class , PathComponent.class).get();
    public final static Family PLAYER = Family.one(PlayerComponent.class).get();
    public final static Family TOWER = Family.all(PositionComponent.class , AngleComponent.class ,RenderableComponent.class , SkeletonComponent.class , OffsetComponent.class).get();
    public final static Family RENDERABLE = Family.all(RenderableComponent.class, PositionComponent.class , SkeletonComponent.class).get();
}
