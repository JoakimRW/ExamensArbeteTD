package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Factory.EnemyFactory;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.RenderSystem;
import com.mygdx.game.entites.systems.StateSystem;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class EntityManager {
    private final EnemyFactory enemySpawner;
    private Engine ashleyEngine;
	private WaveTimeManager waveManager;

    public EntityManager(Engine ashleyEngine , SpriteBatch batch){
        this.ashleyEngine = ashleyEngine;
        enemySpawner = new EnemyFactory(ashleyEngine);
        MoveToSystem mts = new MoveToSystem();
        StateSystem stateSystem = new StateSystem();
        RenderSystem rs = new RenderSystem(batch);
        ashleyEngine.addSystem(stateSystem);
        ashleyEngine.addSystem(mts);
        ashleyEngine.addSystem(rs);
        waveManager = new WaveTimeManager(enemySpawner);  
    }


    public void update(float deltaTime){
        ashleyEngine.update(deltaTime);
        waveManager.tick(deltaTime);
    }
}
