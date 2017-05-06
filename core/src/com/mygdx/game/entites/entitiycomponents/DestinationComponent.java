package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

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

}
