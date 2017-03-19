package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
	public int health = 0;
	public HealthComponent(int health) {
		this.health = health;
	}
	
}
