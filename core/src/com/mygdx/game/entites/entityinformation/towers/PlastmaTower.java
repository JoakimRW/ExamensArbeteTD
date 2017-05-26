package com.mygdx.game.entites.entityinformation.towers;

import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.utils.Assets;

public class PlastmaTower extends EntityInformation {
	public PlastmaTower() {
		setDamage(50d);
		setFireRate(0.75d);
		setRange(150d);
		setSplashRadius(35f);
		setAnimationStateData(Assets.plastmaTowerAnimationState.getData());
		setSkeleton(Assets.plastmaTowerSkeleton);
		setCost(50d);
		setDescription("The Plastma tower does heavy damage in an area.\n\nCan only fire at ground targets.");
		setName("Plastma Tower");
		setOffsetX(0);
		setOffsetY(0);
	}
}
