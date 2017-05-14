package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class SplashComponent implements Component {

	public float radius;
	
	public SplashComponent (float radius){
		this.radius = radius;
	}
}
