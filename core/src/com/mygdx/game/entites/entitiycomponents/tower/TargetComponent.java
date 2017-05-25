package com.mygdx.game.entites.entitiycomponents.tower;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TargetComponent implements Component {
	private Entity target;
	private ArrayList<Entity> targets;
	private boolean isMultiTarget;
	private int maxTargets;
	
	public TargetComponent(Entity target , boolean isMultiTarget , int maxTargets){
		this.target = target;
		this.isMultiTarget = isMultiTarget;
		this.maxTargets = maxTargets;
		targets = new ArrayList<>();
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

	public void addTargets(ArrayList<Entity> targets) {
		this.targets = targets;
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


	public int getMaxTargets() {
		return maxTargets;
	}


	public void setMaxTargets(int maxTargets) {
		this.maxTargets = maxTargets;
	}

	
}
