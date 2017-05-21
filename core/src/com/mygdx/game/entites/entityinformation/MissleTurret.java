package com.mygdx.game.entites.entityinformation;

import com.mygdx.game.utils.Assets;

public class MissleTurret extends EntityInformation {
	public MissleTurret() {
		setDamage(50d);
		setFireRate(1);
		setRange(150d);
//		setSplashRadius(55f);
		setIsSplash(false);
		setAnimationStateData(Assets.missleTowerAnimationState.getData());
		setSkeleton(Assets.missleTowerSkeleton);
		setCost(100d);
		setDescription("THe missile tower fires mutlible missles at air targets , but cannot fire at ground targets.");
		setName("Missle Tower");
		setOffsetX(0);
		setOffsetY(0);
		setMultiTarget(true);
	}	
}
