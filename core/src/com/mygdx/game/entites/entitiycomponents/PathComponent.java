package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.utils.Node;

import java.util.ArrayList;

/**
 * Created by MichaelSjogren on 2017-03-18.
 */
public class PathComponent implements Component {
    public ArrayList<Node> path;
    public int index = 1;
}
