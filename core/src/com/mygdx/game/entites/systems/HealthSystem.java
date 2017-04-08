package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.utils.Assets;

public class HealthSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	private Engine engine;
	private SpriteBatch batch;

	public HealthSystem(SpriteBatch batch) {
		this.batch = batch;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = engine;
		entities = engine.getEntitiesFor(Family.all(HealthComponent.class , PositionComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for(Entity entity : entities){
			HealthComponent hpComp = entity.getComponent(HealthComponent.class);
			PositionComponent posComp = entity.getComponent(PositionComponent.class);
			checkEntityHealth(entity, hpComp);
			drawHealthbar(hpComp, posComp);
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
    	if(healthComp.health <= healthComp.maxHealth) sprite.setColor(Color.GREEN);
    	// 75 %
    	if(healthComp.health <= 0.75 * healthComp.maxHealth) sprite.setColor(Color.YELLOW);
    	// 50%
    	if(healthComp.health <= 0.50 * healthComp.maxHealth) sprite.setColor(Color.ORANGE);
    	// 25%
    	if(healthComp.health <= 0.25 * healthComp.maxHealth) sprite.setColor(Color.RED);
    	
    	//if(healthComp.health != healthComp.maxHealth){
        	spriteBg.draw(batch);
        	sprite.draw(batch);
    	//}
    }
    
    private void checkEntityHealth(Entity entity , HealthComponent hpComp){
		if(hpComp.health <= 0){
			entity.removeAll();
			engine.removeEntity(entity);
		}	
    }
	
}
