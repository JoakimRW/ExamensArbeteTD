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
        DestinationComponent d = Mappers.DESTINATION_M.get(entity);
        uiController.setPlayerMoney(m.money);
        if (d.getDestinationEntity() != null){
            uiController.setTowerSelectionInfo("Laser Tower",stats.getCost() , stats.getCost() , stats.getFireRate() , stats.getFireRate() , stats.getRange() , "None");
        }else{
            uiController.hideTowerSelectionPanel();
        }
        // Player should have integer values , enemies do not
        uiController.updateHealth((int)h.health);
    }
}
