package com.mygdx.game.entites.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.controllers.UIStageController;
import com.mygdx.game.entites.entitiycomponents.*;


/** updates player stats to ui via controller */
public class PlayerStatSystem extends IteratingSystem {
    private UIStageController uiController;

    public PlayerStatSystem(UIStageController uiController) {
        super(Families.PLAYER);
        this.uiController = uiController;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent h = Mappers.HEALTH_M.get(entity);
        MoneyComponent m = Mappers.MONEY_M.get(entity);
        uiController.setPlayerMoney(m.money);
        uiController.updateHealth(h.health);
    }
}
