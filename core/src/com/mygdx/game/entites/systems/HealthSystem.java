package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.utils.Assets;

public class HealthSystem extends IteratingSystem {
	private SpriteBatch _batch;

	private ComponentMapper<HealthComponent> _hpm;
	private ComponentMapper<PositionComponent> _pm;
	private EntityFactory _entityFactory;

	public HealthSystem(SpriteBatch batch , EntityFactory factory) {
		super(Family.all(HealthComponent.class , PositionComponent.class).get());
		_pm = ComponentMapper.getFor(PositionComponent.class);
		_hpm = ComponentMapper.getFor(HealthComponent.class);
		_entityFactory = factory;
		_batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		HealthComponent hpComp = _hpm.get(entity);
		PositionComponent posComp = _pm.get(entity);
		hpComp.health -= 1f;
		if(hpComp.health <= 0){
			float deathX = posComp.position.x;
			float deathY = posComp.position.y;
			entity.removeAll();
			getEngine().removeEntity(entity);
			_entityFactory.createCoinEntity(deathX, deathY);
		}	
        drawHealthBar(hpComp, posComp);
	}

	private void drawHealthBar(HealthComponent healthComp , PositionComponent pos){
    	final float healthBarHeight = Assets.enemyRedHealthbarBG.getHeight();
    	final float healthBarWidth = Assets.enemyRedHealthbarBG.getWidth();
    	// getting the scale ratio
    	final float ratio = healthBarWidth / healthComp.maxHealth;
    	Sprite spriteBg = Assets.enemyRedHealthbarBG;
    	Sprite sprite  = Assets.enemyGreenHealthbarBG;
    	// todo center hp bar
    	sprite.setPosition(pos.position.x , pos.position.y);
    	spriteBg.setPosition(pos.position.x , pos.position.y);
    	sprite.setSize(ratio * healthComp.health , healthBarHeight);
    	// 100 %
    	if(healthComp.health <= healthComp.maxHealth) sprite.setColor(0,0.75f,0,1f);
    	// 75 %
    	if(healthComp.health <= 0.75 * healthComp.maxHealth) sprite.setColor(Color.YELLOW);
    	// 50%
    	if(healthComp.health <= 0.50 * healthComp.maxHealth) sprite.setColor(Color.ORANGE);
    	// 25%
    	if(healthComp.health <= 0.25 * healthComp.maxHealth) sprite.setColor(Color.RED);
    	// if enemy isn't hurt don't draw health.
    	if(healthComp.health != healthComp.maxHealth){
    		_batch.begin();
        	spriteBg.draw(_batch);
        	sprite.draw(_batch);
        	_batch.end();
    	}
    }	
}
