package com.mygdx.game.entites.entityinformation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.utils.Assets;

public class LaserProjectile extends EntityInformation {
	public LaserProjectile() {
		setProjectileSprite(new Sprite(Assets.laserSmall));
		setVelocity(600);
		setOffsetX(8);
		setOffsetY(8);
		setSoundEffect(Assets.laserTurretFire);
	}
}
