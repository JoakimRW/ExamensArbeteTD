package com.mygdx.game.entites.entityinformation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.utils.Assets;

public class PlastmaProjectile extends EntityInformation {
	public PlastmaProjectile() {
		setProjectileSprite(new Sprite(Assets.laserSmall));
		setVelocity(600);
		setOffsetX(8);
		setOffsetY(8);
		setIsSplash(true);
		setSoundEffect(Assets.laserTurretFire);
	}
}
