package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class TimeComponent implements Component {
	public float time = 0f;
	public float lifeTime = 0f;
	public TimeComponent(float lifeTime) {
		this.lifeTime = lifeTime;
	}
}
