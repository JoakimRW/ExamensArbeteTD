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
        super(Families.RENDERABLE);
        this.batch = batch;
        renderer = new SkeletonRenderer<>();
        renderer.setPremultipliedAlpha(true);
    }



    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        OffsetComponent ocomp = Mappers.OFFSET_M.get(entity);
        PositionComponent pos = Mappers.POSITION_M.get(entity);
        AngleComponent angleComp = Mappers.ANGLE_M.get(entity);
        SkeletonComponent skeletonComponent = Mappers.SKELETON_M.get(entity);
        skeletonComponent.animationState.update(deltaTime);
        skeletonComponent.animationState.apply(skeletonComponent.skeleton);
        float offsetX = 16;
        float offsetY = 16;
        if (ocomp != null){
            offsetX = ocomp.offsetX;
            offsetY = ocomp.offsetY;
        }
        skeletonComponent.skeleton.setPosition(pos.position.x + offsetX, pos.position.y + offsetY );
        if (angleComp != null)
        skeletonComponent.skeleton.getRootBone().setRotation(angleComp.spriteAngle);
        skeletonComponent.skeleton.updateWorldTransform();
        batch.begin();
        renderer.draw(batch,skeletonComponent.skeleton);
        batch.end();
    }
}
