package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class HealthComponent implements Component {
	public int health = 0;
	public int maxHealth = 0;
	public boolean isTakingDmg = false;
	public HealthComponent(int maxHealth) {
		this.health = maxHealth;
		this.maxHealth = maxHealth;
	}
	
	public void takeDamage(int dmg , Entity entity){
		health -= dmg;
	}
	
}
