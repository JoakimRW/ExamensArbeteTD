package com.mygdx.game.entites.entityinformation.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.utils.Assets;

public class MissleProjectile extends EntityInformation {
	public MissleProjectile() {
		setProjectileSprite(new Sprite(Assets.missile));
		setVelocity(180);
		setOffsetX(8);
		setOffsetY(8);
		setSoundEffect(Assets.laserTurretFire);
	}
}
