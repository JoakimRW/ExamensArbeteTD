package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;

public class MoneyComponent implements Component {
    public int money = 0;
    public MoneyComponent(int startMoney){
        this.money = startMoney;
    }
}
