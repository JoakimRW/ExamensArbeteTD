package com.mygdx.game.entites.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.entites.entitiycomponents.player.PlayerComponent;
import com.mygdx.game.entites.input.InputHandlerIF;

public class PlayerInputSystem extends IteratingSystem implements InputHandlerIF {

	private int camXdir = 0;
	private int camYdir = 0;
	private Entity selectedTower;

	public PlayerInputSystem() {
		super(Family.all(PlayerComponent.class).get());
	}

	@Override
	public void setCamDir(int xAxis, int yAxis) {
		camXdir = xAxis;
		camYdir = yAxis;
	}

	@Override
	public void setAsSelectedTower(Entity tower) {
            selectedTower = tower;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DirectionComponent dcomp = Mappers.DIRECTION_M.get(entity);
        DestinationComponent destionation = Mappers.DESTINATION_M.get(entity);
        destionation.setDestinationEntity(selectedTower);
		dcomp.xAxis = camXdir;
		dcomp.yAxis = camYdir;
	}
}
