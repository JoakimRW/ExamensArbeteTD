package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.utils.Node;

import java.util.ArrayList;

public class PathComponent implements Component {
    public ArrayList<Node> path;
    public boolean isFlying = false;
    public boolean canGoDiag = false;
    // this variable stores the time
    public float pathTimer = 0;
    public PathComponent(boolean isFlying , boolean canGoDiag){
        this.isFlying = isFlying;
        this.canGoDiag = canGoDiag;
    }
}
