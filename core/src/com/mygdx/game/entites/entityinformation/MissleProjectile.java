package com.mygdx.game.entites.entityinformation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.utils.Assets;

public class MissleProjectile extends EntityInformation {
	public MissleProjectile() {
		setProjectileSprite(new Sprite(Assets.laserSmall));
		setVelocity(150);
		setOffsetX(8);
		setOffsetY(8);
		setSoundEffect(Assets.laserTurretFire);
	}
}
