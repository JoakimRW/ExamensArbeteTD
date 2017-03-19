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
            HealthComponent healthComp = entity.getComponent(HealthComponent.class);
            drawHealthbar(healthComp, posComp);
            TextureRegion region =  (TextureRegion) animComp.animations.get(stateComp.get()).getKeyFrame(stateComp.time , true);
            batch.draw( region
                    ,posComp.x,posComp.y,region.getRegionWidth() / 2 , region.getRegionHeight() / 2
            ,region.getRegionWidth() ,region.getRegionHeight(),1,1,dirComp.angle);
        }
    }
    
    private void drawHealthbar(HealthComponent healthComp , PositionComponent pos){
    	float healthbarHeight = Assets.enemyRedHealthbarBG.getHeight();
    	float healthbarWidth = Assets.enemyRedHealthbarBG.getWidth();
    	// getting the scale ratio
    	float ratio = healthbarWidth / healthComp.maxHealth;
    	Sprite spriteBg = Assets.enemyRedHealthbarBG;
    	Sprite sprite  = Assets.enemyGreenHealthbarBG;
    	sprite.setPosition(pos.x, pos.y);
    	spriteBg.setPosition(pos.x, pos.y);
    	sprite.setSize(ratio * healthComp.health , healthbarHeight);
    	// 100 %
    	if(healthComp.health <= 1 * healthComp.maxHealth) sprite.setColor(Color.GREEN);
    	// 75 %
    	if(healthComp.health <= 0.75 * healthComp.maxHealth) sprite.setColor(Color.YELLOW);
    	// 50%
    	if(healthComp.health <= 0.50 * healthComp.maxHealth) sprite.setColor(Color.ORANGE);
    	// 25%
    	if(healthComp.health <= 0.25 * healthComp.maxHealth) sprite.setColor(Color.RED);
    	if(healthComp.health != healthComp.maxHealth){
        	spriteBg.draw(batch);
        	sprite.draw(batch);
    	}
    }
}
