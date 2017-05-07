package com.mygdx.game.entites.input;

import com.badlogic.ashley.core.Entity;

public interface InputHandlerIF {
	void setCamDir(int xAxis , int yAxis);

    void setAsSelectedTower(Entity tower);
}
