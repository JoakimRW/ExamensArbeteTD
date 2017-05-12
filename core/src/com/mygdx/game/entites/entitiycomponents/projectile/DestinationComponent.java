package com.mygdx.game.entites.entitiycomponents.projectile;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.mygdx.game.entites.entitiycomponents.enemy.EnemyComponent;

public class DestinationComponent implements Component {
	
	private Entity _destinationEntity;

	public DestinationComponent(Entity destinationEntity) {
		_destinationEntity = destinationEntity;
	
	}

	public Entity getDestinationEntity() {
		return _destinationEntity;
	}

	public void setDestinationEntity(Entity destinationEntity) {
		_destinationEntity = destinationEntity;
	}
	
	@Override
	public String toString() {
		if(_destinationEntity.getComponent(EnemyComponent.class) != null){
			return "Enemy";
		}
		return "not enemy";
		
	}

}
