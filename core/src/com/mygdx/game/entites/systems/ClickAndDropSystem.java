package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class ClickAndDropSystem extends IteratingSystem {

	public ClickAndDropSystem(Family family) {
		super(family);
		// TODO Auto-generated constructor stub
	}

	public ClickAndDropSystem(Family family, int priority) {
		super(family, priority);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// TODO Auto-generated method stub

	}

}
