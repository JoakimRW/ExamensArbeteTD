package com.mygdx.game.entites.entitiycomponents.tower;

import com.badlogic.ashley.core.Component;

public class FireRateComponent implements Component {
    public double _fireRate;
    public FireRateComponent(double fireRate){
        _fireRate = fireRate;
    }
}
