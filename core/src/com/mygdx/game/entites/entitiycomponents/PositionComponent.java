package com.mygdx.game.entites.entitiycomponents;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class PositionComponent implements Component {
    public Vector2 position = new Vector2();
    public Vector2 oldPosition = new Vector2();

    public PositionComponent(float x ,float y){
        position.set(x , y);
    }
}
