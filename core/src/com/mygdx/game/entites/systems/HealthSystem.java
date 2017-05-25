package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.Mappers;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.enemy.EnemyComponent;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.Assets;

public class HealthSystem extends IteratingSystem {
	private SpriteBatch _batch;
	private EntityFactory _entityFactory;

	public HealthSystem(SpriteBatch batch, EntityFactory factory) {
		super(Family.all(HealthComponent.class, PositionComponent.class).get());
		_entityFactory = factory;
		_batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		HealthComponent hpComp = Mappers.HEALTH_M.get(entity);
		PositionComponent posComp = Mappers.POSITION_M.get(entity);
		EnemyComponent enemyComp = Mappers.ENEMY_M.get(entity);
		if (hpComp.isDead) {
			float deathX = posComp.position.x;
			float deathY = posComp.position.y;
			entity.removeAll();
			getEngine().removeEntity(entity);
			if(enemyComp != null){
				PlayState.CURRENT_LIVING_ENEMIES --;
			}
			_entityFactory.createCoinEntity(deathX, deathY, WaveTimeManager.WAVE + MathUtils.random(1));
		}
		if(enemyComp != null){
			drawHealthBar(hpComp, posComp);
		}
	}

	private void drawHealthBar(HealthComponent healthComp, PositionComponent pos) {
		final float healthBarHeight = Assets.enemyRedHealthbarBG.getHeight();
		final float healthBarWidth = Assets.enemyRedHealthbarBG.getWidth();
		// getting the scale ratio
		final double ratio = healthBarWidth / healthComp.maxHealth;
		Sprite spriteBg = Assets.enemyRedHealthbarBG;
		Sprite sprite = Assets.enemyGreenHealthbarBG;
		// todo center hp bar
		sprite.setPosition(pos.position.x, pos.position.y);
		spriteBg.setPosition(pos.position.x, pos.position.y);
		if(healthComp.health > 0)
			sprite.setSize((float) (ratio * healthComp.health), healthBarHeight);
		// 100 %
		if (healthComp.health <= healthComp.maxHealth)
			sprite.setColor(0, 0.75f, 0, 1f);
		// 75 %
		if (healthComp.health <= 0.75 * healthComp.maxHealth)
			sprite.setColor(Color.YELLOW);
		// 50%
		if (healthComp.health <= 0.50 * healthComp.maxHealth)
			sprite.setColor(Color.ORANGE);
		// 25%
		if (healthComp.health <= 0.25 * healthComp.maxHealth)
			sprite.setColor(Color.BROWN);
		// if enemy isn't hurt don't draw health.
		if (healthComp.health != healthComp.maxHealth) {
			_batch.begin();
			spriteBg.draw(_batch);
			sprite.draw(_batch);
			_batch.end();
		}
	}
}
