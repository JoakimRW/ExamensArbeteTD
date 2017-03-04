package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class StateComponent implements Component{
    private int state = 0;
    public float time = 0.0f;

    public int get() {
        return state;
    }

    public void set(int newState) {
        state = newState;
        time = 0.0f;
    }
}
