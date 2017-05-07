package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class TowerStatComponent implements Component {
	

	private Double _fireRate;
	private Double _range;
	private Double _cost;

	public TowerStatComponent(Double fireRate, Double range , Double cost) {
		_fireRate = fireRate;
		_range = range;
		_cost = cost;
		// TODO Auto-generated constructor stub
	}

	public Double getFireRate() {
		return _fireRate;
	}

	public void setFireRate(Double fireRate) {
		_fireRate = fireRate;
	}

	public Double getRange() {
		return _range;
	}

	public void setRange(Double range) {
		_range = range;
	}

	public Double getCost() {
		return _cost;
	}

	public void setCost(Double cost) {
		_cost = cost;
	}

}
