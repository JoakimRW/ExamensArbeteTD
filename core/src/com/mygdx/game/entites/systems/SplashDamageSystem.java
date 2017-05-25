package com.mygdx.game.entites.systems;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;
import com.mygdx.game.entites.entitiycomponents.Mappers;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.SplashComponent;
import com.mygdx.game.entites.entitiycomponents.tower.DamageComponent;

public class SplashDamageSystem extends IteratingSystem implements EntityListener{

	public SplashDamageSystem() {
		super(Families.PROJECTILE);
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}

	@Override
	public void entityAdded(Entity entity) {
			
	}

	@Override
	public void entityRemoved(Entity entity) {
		SplashComponent splash = Mappers.SPLASH_M.get(entity);
		
		DamageComponent dmgComp = Mappers.DAMAGE_M.get(entity);
		Entity targetedEnemy = Mappers.DESTINATION_M.get(entity).getDestinationEntity();
		PositionComponent splashPos = Mappers.POSITION_M.get(targetedEnemy);		
		
		if(splash != null && splashPos != null){
			ImmutableArray<Entity> enemies = getEngine().getEntitiesFor(Families.ENEMY);
			final ArrayList<Entity> enemiesToDamage = new ArrayList<>();
			for(int i = 0; i < enemies.size() ; i++){
				if(enemies.get(i).equals(targetedEnemy)) continue;
				PositionComponent enemyPos = Mappers.POSITION_M.get(enemies.get(i));
				if(enemiesToDamage.contains(enemies.get(i))) continue;
					if(enemyPos.position.dst(splashPos.position) < splash.radius){
						// todo 
						enemiesToDamage.add(enemies.get(i));
					}
			}
			
			
			
			for(Entity enemy : enemiesToDamage){
				HealthComponent enemyHp = Mappers.HEALTH_M.get(enemy);
				enemyHp.takeDamage(dmgComp.getDamage());
			}	
		}
	}
	
	

}
