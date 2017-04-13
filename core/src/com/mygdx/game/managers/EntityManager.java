package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.entites.systems.HealthSystem;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.RenderSystem;

public class EntityManager {
    private final EntityFactory entityFactory;
    private Engine ashleyEngine;
	private WaveTimeManager waveManager;

    public EntityManager(Engine ashleyEngine , SpriteBatch batch){
        this.ashleyEngine = ashleyEngine;
        entityFactory = new EntityFactory(ashleyEngine);
        MoveToSystem moveToSystem = new MoveToSystem();
        RenderSystem renderSystem = new RenderSystem(batch);
        HealthSystem healthSystem = new HealthSystem(batch);
        ashleyEngine.addSystem(moveToSystem);
        ashleyEngine.addSystem(renderSystem);
        ashleyEngine.addSystem(healthSystem);
        waveManager = new WaveTimeManager(entityFactory);
    }

    public void update(float deltaTime){
        ashleyEngine.update(deltaTime);
        waveManager.tick(deltaTime);
    }
}
