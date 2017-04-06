package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.esotericsoftware.spine.AnimationState;
import com.mygdx.game.entites.entitiycomponents.StateComponent;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class StateSystem extends IteratingSystem {
    private ComponentMapper<StateComponent> sm;

    public StateSystem() {
        super(Family.all(StateComponent.class).get());

        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        StateComponent state = sm.get(entity);
            state.time += deltaTime;
    }
}
