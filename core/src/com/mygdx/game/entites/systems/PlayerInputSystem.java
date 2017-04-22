package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.entites.entitiycomponents.DirectionComponent;
import com.mygdx.game.entites.entitiycomponents.PathComponent;
import com.mygdx.game.entites.entitiycomponents.RenderableComponent;
import com.mygdx.game.entites.entitiycomponents.SkeletonComponent;
import com.mygdx.game.entites.entitiycomponents.TextureComponent;
import com.mygdx.game.entites.input.InputHandlerIF;

public class PlayerInputSystem extends EntitySystem implements InputHandlerIF {
	ImmutableArray<Entity> playerList;
	// private Vector2 camDir;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		playerList = getEngine().getEntitiesFor(
				Family.one(DirectionComponent.class).exclude(SkeletonComponent.class, RenderableComponent.class,
						PathComponent.class, TextureComponent.class, RenderableComponent.class).get());
		// camDir = new Vector2();
	}

	@Override
	public void moveCam(int xAxis, int yAxis) {
		playerList.get(0).getComponent(DirectionComponent.class).xAxis = xAxis;
		playerList.get(0).getComponent(DirectionComponent.class).yAxis = yAxis;
	}
}
