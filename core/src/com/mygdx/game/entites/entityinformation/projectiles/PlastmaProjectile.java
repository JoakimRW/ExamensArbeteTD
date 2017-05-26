package com.mygdx.game.entites.entityinformation.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.utils.Assets;

public class PlastmaProjectile extends EntityInformation {
	public PlastmaProjectile() {
		setProjectileSprite(new Sprite(Assets.plastmaProj));
		setVelocity(600);
		setOffsetX(8);
		setOffsetY(8);
		setIsSplash(true);
		setSoundEffect(Assets.laserTurretFire);
	}
}
