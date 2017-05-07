package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
	public float health = 0;
	public int maxHealth = 0;
	public boolean isDead = false;

	public HealthComponent(int maxHealth) {
		this.health = maxHealth;
		this.maxHealth = maxHealth;
	}
	
	public void takeDamage(double dmg){
		health -= dmg;
		if (health <= 0) isDead = true;
	}
	
}
