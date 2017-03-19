package com.mygdx.game.entites.towers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actors.BasicTower;

public class LaserTurret extends Entity implements BasicTower{
	private Texture _turret;
	private Texture _base;

	public LaserTurret()  {
		super();
		
		_turret = new Texture(Gdx.files.internal("towers/lvl1/turret.png"));
		_base = new Texture(Gdx.files.internal("towers/lvl1/base.png"));
		init();
	}
	
	@Override
	public Texture getBaseTexture() {
		return _base;
	}

	@Override
	public Texture getTurretTexture() {
		return _turret;
	}

	@Override
	public void setBaseTexture(Texture base) {
		_base = base;
	}

	@Override
	public void setTurretTexture(Texture turret) {
		_turret = turret;
	}

	@Override
	public void init() {
		
	}

	

}
