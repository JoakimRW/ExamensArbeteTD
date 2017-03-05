package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

/**
 * Created by MichaelSjogren on 2017-03-05.
 */
public class CollisionComponent implements Component {
    public CollisionComponent(){}
    public boolean isCollidingY = false;
    public boolean isCollidingX = false;
}
