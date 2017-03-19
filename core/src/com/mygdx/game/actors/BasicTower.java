package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;;

public interface BasicTower  {
	
	
	void init();
	Texture getBaseTexture();
	void setBaseTexture(Texture base);
	Texture getTurretTexture();
	void setTurretTexture(Texture turret);
}
