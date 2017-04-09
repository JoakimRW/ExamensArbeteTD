package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class DirectionComponent implements Component {
    public float dirAngle = 0;
    public Vector2 direction = new Vector2();
    public int xAxis = 0;
    public int yAxis = 0;
    public float spriteAngle;
}
