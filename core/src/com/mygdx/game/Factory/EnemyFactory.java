package com.mygdx.game.Factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.entites.systems.MoveToSystem;
import com.mygdx.game.entites.systems.RenderSystem;
import com.mygdx.game.entites.systems.StateSystem;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.PathFinder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MichaelSjogren on 2017-03-18.
 */
public class EnemyFactory {

    private Engine engine;

    public EnemyFactory(Engine engine) {
      this.engine = engine;
    }

    private void createEnenmy(int health , float speed){
        ArrayList<Node> path =  PathFinder.findPath(new Vector2(LevelManager.tileSpawn.getTileCenter().x / 32 , LevelManager.tileSpawn.getTileCenter().y / 32), new Vector2(LevelManager.tileEnd.getCords().x / 32 , LevelManager.tileEnd.getCords().y / 32) , true);
        if (path == null) return;
        Entity entity = new Entity();
        PathComponent pathComp = new PathComponent();
        pathComp.path = path;
        AnimationComponent animation = new AnimationComponent();
        StateComponent state = new StateComponent();
        state.set(0);
        animation.animations.put(state.get(), Assets.bloodWormAnimation);
        PositionComponent pos = new PositionComponent(path.get(path.size()-1).getCordinates().x * 32 , path.get(path.size()-1).getCordinates().y * 32 );
        entity.add(pos)
                .add(new DirectionComponent())
                .add(state)
                .add(animation)
                .add(new RenderableComponent())
                .add(new DimensionComponent(25 , 25))
                .add(pathComp);

        engine.addEntity(entity);
    }

    public void spawnEnemies(int amount , int health , int speed , long spawnDelay){

            if (amount == 0) return;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                        createEnenmy(health,speed);
                        int c = amount;
                        System.out.println(c);
                        c --;
                        spawnEnemies(c,health,speed,spawnDelay);

                }
            }, spawnDelay);


    }
}
