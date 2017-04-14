package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.entites.systems.HealthSystem;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.RenderSystem;

public class EntityManager {
    private final EntityFactory _entityFactory;
    private Engine _ashleyEngine;
	private WaveTimeManager _waveManager;

    public EntityManager(Engine ashleyEngine , SpriteBatch batch){
        this._ashleyEngine = ashleyEngine;
        _entityFactory = new EntityFactory(ashleyEngine);
        MoveToSystem moveToSystem = new MoveToSystem();
        RenderSystem renderSystem = new RenderSystem(batch);
        HealthSystem healthSystem = new HealthSystem(batch);
        ashleyEngine.addSystem(moveToSystem);
        ashleyEngine.addSystem(renderSystem);
        ashleyEngine.addSystem(healthSystem);
        _waveManager = new WaveTimeManager(_entityFactory);
    }

    public void update(float deltaTime){
        _ashleyEngine.update(deltaTime);
        _waveManager.tick(deltaTime);
    }
    public EntityFactory getEntityFactory(){
    	return _entityFactory;
    }
}
