package com.mygdx.game.entites.entityinformation.towers;

import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.utils.Assets;

public class BasicLaserTurret extends EntityInformation {
	
	public BasicLaserTurret() {
		setDamage(20d);
		setFireRate(1.5d);
		setRange(150d);
		setAnimationStateData(Assets.laserTowerAnimationState.getData());
		setSkeleton(Assets.laserTowerSkeleton);
		setCost(30d);
		setMaxTargets(1);
		setDescription("The Laser Turret fires quickly but with low damage.\n\nCan only fire at ground targets.");
		setName("Laser Turret");
		setOffsetX(0);
		setOffsetY(0);
	}
}
