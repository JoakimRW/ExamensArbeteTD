package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.mygdx.game.entites.entitiycomponents.*;

public class RenderSystem extends IteratingSystem{
    private SpriteBatch batch;
    private SkeletonRenderer<SpriteBatch> renderer;

    public RenderSystem(SpriteBatch batch){
        super(Family.all(SkeletonComponent.class,RenderableComponent.class , HealthComponent.class , StateComponent.class , DirectionComponent.class).get());
        //Family enemy = Family.all(SkeletonComponent.class,RenderableComponent.class , HealthComponent.class , StateComponent.class , DirectionComponent.class , DimensionComponent.class).get();
        this.batch = batch;
        renderer = new SkeletonRenderer<>();
        renderer.setPremultipliedAlpha(true);
    }



    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final int offsetX = 16;
        final int offsetY = 16;
        PositionComponent posComp = entity.getComponent(PositionComponent.class);
        StateComponent stateComp = entity.getComponent(StateComponent.class);
        DirectionComponent dirComp = entity.getComponent(DirectionComponent.class);
        SkeletonComponent skeletonComponent = entity.getComponent(SkeletonComponent.class);
        stateComp.animationState.update(deltaTime);
        stateComp.animationState.apply(skeletonComponent.skeleton);
        skeletonComponent.skeleton.updateWorldTransform();
        skeletonComponent.skeleton.setPosition(posComp.x + offsetX , posComp.y + offsetY);
        skeletonComponent.skeleton.getRootBone().setRotation(dirComp.angle);
        renderer.draw(batch,skeletonComponent.skeleton);
    }
}
