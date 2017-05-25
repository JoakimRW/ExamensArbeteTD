package com.mygdx.game.entites.entitiycomponents.tower;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TargetComponent implements Component {
	private Entity target;
	private ArrayList<Entity> targets = new ArrayList<>();
	private boolean isMultiTarget;
	private int maxTargets = 1;
	
	public TargetComponent(Entity target , boolean isMultiTarget , int maxTargets){
		this.target = target;
		this.isMultiTarget = isMultiTarget;
		this.maxTargets = maxTargets;
	}
	

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

	public void addTargets(List<Entity> target) {
		targets.addAll(target);
	}

	public ArrayList<Entity> getTargets() {
		return targets;
	}

	public boolean isMultiTarget() {
		return isMultiTarget;
	}

	public void setMultiTarget(boolean isMultiTarget) {
		this.isMultiTarget = isMultiTarget;
	}

}
