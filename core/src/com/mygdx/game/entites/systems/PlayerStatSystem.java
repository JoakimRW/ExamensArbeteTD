package com.mygdx.game.entites.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.controllers.UIStageController;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.entites.entitiycomponents.projectile.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.tower.*;


/** updates player and tower selection info stats to ui via controller */
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
            TowerStatComponent stats = Mappers.TOWER_STATS_M.get(d.getDestinationEntity());
            DamageComponent dmg = Mappers.DAMAGE_M.get(d.getDestinationEntity());
            FireRateComponent f = Mappers.FIRE_RATE_M.get(d.getDestinationEntity());
            RangeComponent r = Mappers.RANGE_M.get(d.getDestinationEntity());
            SpecialTowerComponent s = Mappers.SPECIAL_M.get(entity);
            uiController.setTowerSelectionInfo(stats._towerName, stats._sellValue , stats._upgradePrice , f._fireRate  , dmg.getDamage()  , r.getRange() , "None");
        }else{
            uiController.hideTowerSelectionPanel();
        }
        // Player should have integer values , enemies do not
        uiController.updateHealth((int)h.health);
    }
}
