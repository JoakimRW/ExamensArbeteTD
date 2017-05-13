package com.mygdx.game.entites.entityinformation;

import com.mygdx.game.utils.Assets;

public class Bloodworm extends EntityInformation{

	
	public Bloodworm() {
		setOffsetX(16);
		setOffsetY(16);
		setFlying(false);
		setAnimationStateData(Assets.bloodWormAnimationState.getData());
		setVelocity(75);
		setHp(100);
		setSkeleton(Assets.bloodWormSkeleton);
	}
}
