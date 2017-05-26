package com.mygdx.game.entites.entityinformation.enemies;

import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.utils.Assets;

public class Bird extends EntityInformation {

	public Bird() {
		setOffsetX(16);
		setOffsetY(0);
		setFlying(true);
		setSkeleton(Assets.birdSkeleton);
		setAnimationStateData(Assets.birdAnimationState.getData());
		setVelocity(70);
		setHp(20);
	}
}
