package com.mygdx.game.entites.entitiycomponents.tower;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.Factory.TowerType;

public class TowerStatComponent implements Component{

    public double _sellValue = 0;
    public double _buyCost = 0;
    public double _towerLevel = 1;
    public double _upgradePrice = 0;
    public String _towerName = "";
    public double sellPenalty = 0.35;
    public TowerType _towerType;

    public TowerStatComponent(double buyCost , String towerName , TowerType towerType){
        _buyCost = buyCost;
        _sellValue = buyCost * sellPenalty;
        _towerType = towerType;
        _upgradePrice = _buyCost + 4;
        _towerName = towerName;
    }

    public void upgrade(){
        _buyCost = _upgradePrice;
        _towerLevel++;
        _upgradePrice = _towerLevel * _buyCost + 10;
        _sellValue = _buyCost * sellPenalty;
    }

    public String getTowerName(){
        return _towerName + " " + _towerLevel;
    }
}
