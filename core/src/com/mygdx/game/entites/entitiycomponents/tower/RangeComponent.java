package com.mygdx.game.entites.entitiycomponents.tower;

import com.badlogic.ashley.core.Component;

public class RangeComponent implements Component {
    private double _range = 0;
    public RangeComponent(double range){
        setRange(range);
    }
	public double getRange() {
		return _range;
	}
	public void setRange(double range) {
		_range = range;
	}
}
