package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
	public float speed = 0;
	public VelocityComponent(float speed) {
		this.speed = speed;
	}
}
