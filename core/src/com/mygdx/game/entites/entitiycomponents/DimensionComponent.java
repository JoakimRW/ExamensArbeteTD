package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

/**
 * Created by MichaelSjogren on 2017-03-05.
 */
public class DimensionComponent implements Component {
    public float width = 0;
    public float height = 0;
    public DimensionComponent(float width, float height){
        this.width = width;
        this.height = height;
    }
}
