package com.mygdx.game.entites.entitiycomponents;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class PositionComponent implements Component {
    public float x;
    public float y;

    public PositionComponent(float x ,float y){
        this.x = x;
        this.y = y;
    }
}
