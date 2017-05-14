package com.mygdx.game.entites.entitiycomponents.tower;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TargetComponent implements Component {
	private Entity target;
	private ArrayList<Entity> targets = new ArrayList<>();

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

	public void addTargets(Entity target) {
		targets.add(target);
	}

	public ArrayList<Entity> getTargets() {
		// TODO Auto-generated method stub
		return targets;
	}

}
