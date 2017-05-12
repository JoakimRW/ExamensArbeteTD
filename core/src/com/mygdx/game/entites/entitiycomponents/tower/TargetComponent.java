package com.mygdx.game.entites.entitiycomponents.tower;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TargetComponent implements Component {
	private Entity target;

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}
	
	@Override
	public String toString() {
		return "target = " +target;
	}

}
