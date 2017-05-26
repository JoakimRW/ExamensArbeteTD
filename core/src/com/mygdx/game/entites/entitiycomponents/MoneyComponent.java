package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class MoneyComponent implements Component {
    public double money = 0;
    public MoneyComponent(double startMoney){
        this.money = startMoney;
    }
}
