package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.utils.Assets;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class RenderSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private SpriteBatch batch;
    ShapeRenderer sr;

    public RenderSystem(SpriteBatch batch , ShapeRenderer sr){
        this.batch = batch;
        this.sr = sr;
    }

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(RenderableComponent.class , HealthComponent.class , AnimationComponent.class , StateComponent.class , DirectionComponent.class , DimensionComponent.class).get());
    }

    public void update(float deltaTile){
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            PositionComponent posComp = entity.getComponent(PositionComponent.class);
            AnimationComponent animComp = entity.getComponent(AnimationComponent.class);
            StateComponent stateComp = entity.getComponent(StateComponent.class);
            DirectionComponent dirComp = entity.getComponent(DirectionComponent.class);
            TextureRegion region =  (TextureRegion) animComp.animations.get(stateComp.get()).getKeyFrame(stateComp.time , true);
            batch.draw( region
                    ,posComp.x,posComp.y,region.getRegionWidth() / 2 , region.getRegionHeight() / 2
            ,region.getRegionWidth() ,region.getRegionHeight(),1,1,dirComp.angle);
        }
    }
}
