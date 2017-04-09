package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
	public Vector2 velocity = new Vector2(0,0);
	public float maxSpeed;
	public VelocityComponent(float speed) {
		maxSpeed = speed;
	}
}
