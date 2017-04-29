package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class OffsetComponent implements Component {
    public float offsetX = 0;
    public float offsetY = 0;
    public OffsetComponent(float offsetX , float offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
