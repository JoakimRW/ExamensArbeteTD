package com.mygdx.game.entites.entityinformation;

import com.mygdx.game.utils.Assets;

public class PlastmaTower extends EntityInformation {
	public PlastmaTower() {
		setDamage(50d);
		setFireRate(0.75d);
		setRange(150d);
		setSplashRadius(30f);
		setAnimationStateData(Assets.laserTowerAnimationState.getData());
		setSkeleton(Assets.laserTowerSkeleton);
		setCost(50d);
		setDescription("The Plastma Tower does heavier damage in an area");
		setName("Plastma Tower");
		setOffsetX(0);
		setOffsetY(0);
	}
}
