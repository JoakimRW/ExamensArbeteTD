package com.mygdx.game.entites.entitiycomponents;


import com.badlogic.ashley.core.Component;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class PositionComponent implements Component {
    public float x = 0.0f;
    public float y = 0.0f;

    public PositionComponent(float x ,float y){
        this.x = x;
        this.y = y;
    }
}
