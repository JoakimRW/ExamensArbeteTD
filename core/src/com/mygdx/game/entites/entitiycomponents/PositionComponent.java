package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {

    public Vector2 position;

    public PositionComponent(Vector2 position){

        this.position = position;
    }
}
