package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.entites.entitiycomponents.HealthComponent;

public class HealthSystem extends IteratingSystem {

	private ComponentMapper<HealthComponent> hpComp;
	private Engine engine;

	public HealthSystem() {
		super(Family.all(HealthComponent.class).get());
		hpComp = ComponentMapper.getFor(HealthComponent.class);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = engine;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		if(hpComp.get(entity).health <= 0){
			entity.removeAll();
			engine.removeEntity(entity);
		}	
	}
	
}
