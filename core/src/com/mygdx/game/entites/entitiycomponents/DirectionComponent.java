package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class DirectionComponent implements Component {
    public float angle = 0.0f;

    public DirectionComponent(float angle){
        this.angle = angle;
    }
}
