package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
	public double health = 0;
	public double maxHealth = 0;
	public boolean isDead = false;

	public HealthComponent(double maxHealth) {
		this.health = maxHealth;
		this.maxHealth = maxHealth;
	}
	
	public void takeDamage(double dmg){
		health -= dmg;
		if (health <= 0) isDead = true;
	}
	
}
