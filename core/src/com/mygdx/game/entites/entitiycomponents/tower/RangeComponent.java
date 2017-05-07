package com.mygdx.game.entites.entitiycomponents.tower;

import com.badlogic.ashley.core.Component;

public class RangeComponent implements Component {
    public double _range = 0;
    public RangeComponent(double range){
        _range = range;
    }
}
