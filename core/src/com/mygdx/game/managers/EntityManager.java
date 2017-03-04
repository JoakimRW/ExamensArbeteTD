package com.mygdx.game.managers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.utils.Timer;
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
    private ArrayList<Node> path;

    public EntityManager(Engine ashleyEngine , SpriteBatch batch , ArrayList<Node> path){
        this.ashleyEngine = ashleyEngine;
        MoveToSystem cs = new MoveToSystem(path);
        StateSystem stateSystem = new StateSystem();
        ashleyEngine.addSystem(stateSystem);
        ashleyEngine.addSystem(cs);
        this.batch = batch;
        RenderSystem rs = new RenderSystem(batch);
        ashleyEngine.addSystem(rs);
        Assets.load();
        Entity entity = new Entity();
        AnimationComponent animation = new AnimationComponent();
        StateComponent state = new StateComponent();
        state.set(0);
        animation.animations.put(state.get(),Assets.bloodWormAnimation);
        entity.add(new PositionComponent(300,300))
                .add(new DirectionComponent( 32f))
                .add(state)
                .add(animation)
                .add(new RenderableComponent());
        ashleyEngine.addEntity(entity);
    }

    public void setPath(ArrayList<Node> path){
        this.path = path;
    }

    public void update(float deltaTime){
        ashleyEngine.update(deltaTime);
    }
}
