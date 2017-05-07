package com.mygdx.game.entites.entitiycomponents.tower;

import com.badlogic.ashley.core.Component;

public class TowerStatComponent implements Component{

    public double _sellValue = 0;
    public double _buyCost = 0;
    public int _towerLevel = 1;
    public double _upgradePrice = 0;
    public String _towerName = "";

    public TowerStatComponent(double buyCost , String towerName){
        _buyCost = buyCost;
        _sellValue = buyCost * 0.35;
        _upgradePrice = _buyCost + 40;
        _towerName = towerName + " " + _towerLevel;
    }

    public void upgrade(){
        _buyCost = _upgradePrice;
        _towerLevel++;
        _towerName = _towerName + " " +_towerLevel;
        _upgradePrice = _towerLevel * _buyCost + 100;
        _sellValue = _buyCost * 0.35;
    }
}
