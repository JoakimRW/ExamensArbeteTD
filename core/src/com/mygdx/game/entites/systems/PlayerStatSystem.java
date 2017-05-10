package com.mygdx.game.entites.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.controllers.EntityModel;
import com.mygdx.game.controllers.UIStageController;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.entites.entitiycomponents.projectile.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.tower.*;


/** updates player and tower selection info stats to ui via controller */
public class PlayerStatSystem extends IteratingSystem {
    private UIStageController uiController;
    private EntityModel _entityModel;

    public PlayerStatSystem(UIStageController uiController, EntityModel _entityModel) {
        super(Families.PLAYER);
        this.uiController = uiController;
        this._entityModel = _entityModel;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent h = Mappers.HEALTH_M.get(entity);
        MoneyComponent m = Mappers.MONEY_M.get(entity);
        DestinationComponent d = Mappers.DESTINATION_M.get(entity);
        uiController.setPlayerMoney(m.money);
        // if target isnt null set selected tower in model to the selected entity
        _entityModel.setSelectedTower(d.getDestinationEntity());

        // TODO make function / separate system for some of this
        // show the panel if a tower is selected
        if (_entityModel.getSelectedTower() != null) {
            TowerStatComponent stats = Mappers.TOWER_STATS_M.get(_entityModel.getSelectedTower());
            DamageComponent dmg = Mappers.DAMAGE_M.get(_entityModel.getSelectedTower());
            FireRateComponent f = Mappers.FIRE_RATE_M.get(_entityModel.getSelectedTower());
            RangeComponent r = Mappers.RANGE_M.get(_entityModel.getSelectedTower());
            SpecialTowerComponent s = Mappers.SPECIAL_M.get(_entityModel.getSelectedTower());

            double fireRate;
            double damage;
            double range;
            if (stats != null && dmg != null && f != null && r != null && s != null) {
                    uiController.updateUpgradeInfo();
                if (uiController.isOverUpgradeBtn()) {
                    fireRate = f._fireRate + (f._fireRate * f.percentageIncrease);
                    damage = dmg.getDamage() + dmg.dmgIncrease;
                    range = r.getRange() + (r.getRange() * r.percentageIncrease);
                    _entityModel.setisfireRateGreenText(fireRate > f._fireRate);
                    _entityModel.setisDamageGreenText(damage > dmg.getDamage());
                    _entityModel.setisRangeGreenText(range > r.getRange());
                } else {
                    fireRate = f._fireRate;
                    damage = dmg.getDamage();
                    range = r.getRange();
                }
                uiController.setTowerSelectionInfo(stats.getTowerName(), stats._sellValue, stats._upgradePrice, fireRate, damage, range, "None");
            } else {
                uiController.hideTowerSelectionPanel();
            }
        } else {
            uiController.hideTowerSelectionPanel();
        }
        
        // Player should have integer values , enemies do not
        uiController.updateHealth((int) h.health);
    }
}

