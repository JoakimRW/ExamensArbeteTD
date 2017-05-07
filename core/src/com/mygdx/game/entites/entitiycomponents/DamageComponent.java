package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component{
	private float _damage;

	public DamageComponent(float damage) {
		setDamage(damage);
	}

	public float getDamage() {
		return _damage;
	}

	public void setDamage(float damage) {
		_damage = damage;
	}

}
