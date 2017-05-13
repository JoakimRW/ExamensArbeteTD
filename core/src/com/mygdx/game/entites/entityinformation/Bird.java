package com.mygdx.game.entites.entityinformation;

import com.mygdx.game.utils.Assets;

public class Bird extends EntityInformation {

	public Bird() {
		setOffsetX(16);
		setOffsetY(0);
		setFlying(true);
		setSkeleton(Assets.birdSkeleton);
		setAnimationStateData(Assets.birdAnimationState.getData());
		setVelocity(100);
		setHp(100);
	}
}
