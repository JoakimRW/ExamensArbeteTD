package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {
    public float x;
    public float y;

    public PositionComponent(float x ,float y){
        this.x = x;
        this.y = y;
    }
}
