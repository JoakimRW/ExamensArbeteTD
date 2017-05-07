package com.mygdx.game.entites.entitiycomponents.tower;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component{
	private Double _damage;

	public DamageComponent(Double damage) {
		setDamage(damage);
	}

	public Double getDamage() {
		return _damage;
	}

	public void setDamage(Double damage) {
		_damage = damage;
	}

}
