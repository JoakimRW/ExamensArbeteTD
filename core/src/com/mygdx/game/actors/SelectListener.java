package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public interface SelectListener {
	void select(Actor actor, InputEvent event);
}
