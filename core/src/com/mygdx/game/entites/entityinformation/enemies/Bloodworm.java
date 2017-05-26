package com.mygdx.game.entites.entityinformation.enemies;

import com.mygdx.game.entites.entityinformation.EntityInformation;
import com.mygdx.game.utils.Assets;

public class Bloodworm extends EntityInformation{

	
	public Bloodworm() {
		setOffsetX(16);
		setOffsetY(16);
		setFlying(false);
		setAnimationStateData(Assets.bloodWormAnimationState.getData());
		setVelocity(50);
		setHp(20);
		setSkeleton(Assets.bloodWormSkeleton);
	}
}
