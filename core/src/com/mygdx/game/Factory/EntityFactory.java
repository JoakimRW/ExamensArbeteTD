package com.mygdx.game.Factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.*;

import com.mygdx.game.managers.LevelManager;

import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.PathFinder;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class EntityFactory{
    // Spawning location
    private final float _spawnX = LevelManager.tileSpawn.getCords().x / 32;
    private final float _spawnY = LevelManager.tileSpawn.getCords().y / 32;
    // End location
    private final float _endX = LevelManager.tileEnd.getCords().x / 32;
    private final float _endY = LevelManager.tileEnd.getCords().y / 32;
    private Engine _engine;

    public EntityFactory(Engine engine) {
        _engine = engine;
    }

    public void createEnemyEntity(@NotNull EnemyType enemyType){
        Entity entity = null;
        switch (enemyType){
            case BIRD: entity = createBirdEntity();
                break;

            case BLOODWORM: entity = createBloodWormEntity();
                break;
        }
        if(entity != null){
            _engine.addEntity(entity);
        }else System.out.println("Failed to create entity in entity factory :: cause :: entity is null");
    }

    public void createTowerEntity(@NotNull TowerType towerType , float x , float Y){
        Entity entity = null;
        // todo spawn tower
        if(entity != null){
            _engine.addEntity(entity);
        }else System.out.println("Failed to create entity in entity factory :: cause :: entity is null");
    }

    private Entity createBloodWormEntity() {
        // Components
        Entity entity = new Entity();
        PathComponent pathComponent = new PathComponent();
        SkeletonComponent skeletonComp = new SkeletonComponent(Assets.bloodWormSkeleton);
        PositionComponent positionComponent = new PositionComponent(new Vector2(_spawnX * 32, _spawnY * 32));
        HealthComponent healthComponent = new HealthComponent(100);
        VelocityComponent velocityComponent = new VelocityComponent(100f);
        DirectionComponent directionComponent = new DirectionComponent();
        RenderableComponent renderableComponent = new RenderableComponent();
        ArrayList<Node> path =
                PathFinder.findPath(new Vector2(_spawnX  , _spawnY )
                , new Vector2(_endX  , _endY)
                , false
                , false);
        skeletonComp.animationState.setData(Assets.bloodWormAnimationState.getData());
        skeletonComp.skeleton.setPosition(_spawnX * 32 , _spawnY * 32);
        skeletonComp.animationState.setAnimation(0 ,"MOVING",true);
        pathComponent.path = path;
        entity.add(pathComponent)
                .add(positionComponent)
                .add(skeletonComp)
                .add(healthComponent)
                .add(velocityComponent)
                .add(directionComponent)
                .add(renderableComponent);
        return entity;
    }

    private Entity createBirdEntity() {
        Entity entity = new Entity();
        PathComponent pathComponent = new PathComponent();
        SkeletonComponent skeletonComp = new SkeletonComponent(Assets.birdSkeleton);
        PositionComponent positionComponent = new PositionComponent(new Vector2(_spawnX * 32 , _spawnY * 32));
        HealthComponent healthComponent = new HealthComponent(100);
        VelocityComponent velocityComponent = new VelocityComponent(100f);
        DirectionComponent directionComponent = new DirectionComponent();
        RenderableComponent renderableComponent = new RenderableComponent();

        ArrayList<Node> path =
                PathFinder.findPath(new Vector2(_spawnX  , _spawnY )
                        , new Vector2(_endX , _endY )
                        , true
                        , true);
        skeletonComp.animationState.setData(Assets.birdAnimationState.getData());
        skeletonComp.skeleton.setPosition(_spawnX * 32 , _spawnY * 32);
        skeletonComp.animationState.setAnimation(0 ,"MOVING",true);
        pathComponent.path = path;
        entity.add(pathComponent)
                .add(positionComponent)
                .add(skeletonComp)
                .add(healthComponent)
                .add(velocityComponent)
                .add(directionComponent)
                .add(renderableComponent);
        return entity;
    }

}
