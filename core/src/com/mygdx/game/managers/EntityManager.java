package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Factory.EnemyFactory;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.RenderSystem;
import com.mygdx.game.entites.systems.StateSystem;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Node;

import java.util.ArrayList;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class EntityManager {
    private Engine ashleyEngine;
    private SpriteBatch batch;

    public EntityManager(Engine ashleyEngine , SpriteBatch batch){
        this.ashleyEngine = ashleyEngine;
        this.batch = batch;
        EnemyFactory enemySpawner = new EnemyFactory(ashleyEngine);
        MoveToSystem mts = new MoveToSystem();
        StateSystem stateSystem = new StateSystem();
        RenderSystem rs = new RenderSystem(batch);
        ashleyEngine.addSystem(stateSystem);
        ashleyEngine.addSystem(mts);
        ashleyEngine.addSystem(rs);
        enemySpawner.spawnEnemies(10,100,60,1000);
    }


    public void update(float deltaTime){
        ashleyEngine.update(deltaTime);
    }
}
