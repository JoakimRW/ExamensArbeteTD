package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.entites.systems.CollisionSystem;
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
    private ShapeRenderer shapeRenderer;
    private Engine ashleyEngine;
    private SpriteBatch batch;
    private ArrayList<Node> path;

    public EntityManager(Engine ashleyEngine , SpriteBatch batch , ShapeRenderer renderer, ArrayList<Node> path){
        this.ashleyEngine = ashleyEngine;

        this.batch = batch;
        Assets.load();
        // creating one entity
        MoveToSystem cs = new MoveToSystem(path);
        CollisionSystem colSysm = new CollisionSystem();
        StateSystem stateSystem = new StateSystem();
        ashleyEngine.addSystem(stateSystem);
        ashleyEngine.addSystem(cs);
        ashleyEngine.addSystem(colSysm);
        RenderSystem rs = new RenderSystem(batch , renderer);
        ashleyEngine.addSystem(rs);
        Entity entity = new Entity();
        AnimationComponent animation = new AnimationComponent();
        StateComponent state = new StateComponent();
        state.set(0);
        animation.animations.put(state.get(),Assets.bloodWormAnimation);
        entity.add(new PositionComponent(LevelManager.tileSpawn.getCords().x , LevelManager.tileSpawn.getCords().y))
                .add(new DirectionComponent())
                .add(state)
                .add(animation)
                .add(new RenderableComponent())
                .add(new DimensionComponent(25 , 25))
                .add(new CollisionComponent());
        ashleyEngine.addEntity(entity);
        //
    }

    public void setPath(ArrayList<Node> path){
        this.path = path;
    }

    public void update(float deltaTime){
        ashleyEngine.update(deltaTime);
    }
}
