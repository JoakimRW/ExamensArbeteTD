package com.mygdx.game.entites.entityinformation;

import com.mygdx.game.utils.Assets;

public class BasicLaserTurret extends EntityInformation {
	
	public BasicLaserTurret() {
		setDamage(20d);
		setFireRate(1d);
		setRange(100d);
		setAnimationStateData(Assets.laserTowerAnimationState.getData());
		setSkeleton(Assets.laserTowerSkeleton);
		setCost(30d);
		setDescription("The Laser Turret fires quickly but with moderate damage.");
		setName("Laser Turret");
		setOffsetX(0);
		setOffsetY(0);
	}
}
