package com.mygdx.game.controllers;

import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.managers.WaveTimeManager;
public class EntityModel {

    private WaveTimeManager waveMngr;
    private EntityFactory factory;

    public EntityModel(WaveTimeManager waveMngr , EntityFactory factory){

        this.waveMngr = waveMngr;
        this.factory = factory;
    }

    public void startNextWave() {
        WaveTimeManager.CURRENT_WAVE_TIME = 1;
    }
    /** when player has pressed a tower icon this method is called **/
    public void beginTowerPlacing(TowerType towerType) {
        System.out.println(towerType);
    }

    public String getNextWave() {
        return waveMngr.getEnemyType().toString();
    }
}
