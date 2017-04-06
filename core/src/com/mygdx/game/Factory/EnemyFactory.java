package com.mygdx.game.Factory;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.*;

import com.mygdx.game.managers.LevelManager;

import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.PathFinder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class EnemyFactory{

    private Engine engine;

    public EnemyFactory(Engine engine) {
      this.engine = engine;
    }

    /**
     * This method initiates a timer that schedules tasks at a fixed rate and spawns a enemy
     * @param amount The amount of enemies you want to spawn
     * @param health The health of the enemy
     * @param speed Enemy movement speed
     * @param timeBetweenSpawns The time gap in milliseconds between enemy spawns during a wave
     * **/
    public void spawnEnemies(int amount , int health , int speed , int timeBetweenSpawns , EntityType enemyType){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Enemy(health , speed , amount , enemyType) , 0  , timeBetweenSpawns );
    }

    private class Enemy extends TimerTask{

        private final int health;
        private final float speed;
        private final int amount;
        private int currentAmount;
		private EntityType enemyType;
        	
        public Enemy(int health , float speed , int amount , EntityType enemyType){
            this.health = health;
            this.speed = speed;
            this.amount = amount;
            this.enemyType = enemyType;
        }
        
        private void createEnenmy(){
            
            Entity entity = new Entity();
            // components
            PathComponent pathComp = new PathComponent();
            StateComponent state = null;
            SkeletonComponent skeletonComp = null;
            ArrayList<Node> path = null;
            switch(enemyType){
            	case BLOODWORM:
                    state = new StateComponent();
            		path =  PathFinder.findPath(
            		        new Vector2(LevelManager.tileSpawn.getTileCenter().x / 32, LevelManager.tileSpawn.getTileCenter().y / 32)
                            , new Vector2(LevelManager.tileEnd.getCords().x / 32, LevelManager.tileEnd.getCords().y / 32)
                            , false
                            , false);
            		skeletonComp = new SkeletonComponent(Assets.bloodWormSkeleton);
            		state.animationState.setData(Assets.bloodWormAnimationState.getData());
            		skeletonComp.skeleton.setPosition(LevelManager.tileSpawn.getTileCenter().x,LevelManager.tileSpawn.getTileCenter().y);
                    state.animationState.setAnimation(0 ,"MOVING",true);
                    pathComp.path = path;
            		if (path == null) return;
            		break;
            	case BIRD:
                    state = new StateComponent();
                    path =  PathFinder.findPath(
                            new Vector2(LevelManager.tileSpawn.getTileCenter().x / 32, LevelManager.tileSpawn.getTileCenter().y / 32)
                            , new Vector2(LevelManager.tileEnd.getCords().x / 32, LevelManager.tileEnd.getCords().y / 32)
                            , true
                            , true);
                    skeletonComp = new SkeletonComponent(Assets.birdSkeleton);
                    state.animationState.setData(Assets.birdAnimationState.getData());
                    skeletonComp.skeleton.setPosition(LevelManager.tileSpawn.getTileCenter().x,LevelManager.tileSpawn.getTileCenter().y);
                    state.animationState.setAnimation(0 ,"MOVING",true);
                    pathComp.path = path;
                    if (path == null) return;
            		break;
            	default:
            	    break;
            }
            PositionComponent pos = new PositionComponent(path.get(path.size()-1).getCordinates().x * 32 , path.get(path.size()-1).getCordinates().y * 32 );
            // add components
            entity.add(pos)
            	.add(new VelocityComponent(speed))
            	.add(skeletonComp)
            	.add(new HealthComponent(health))
	            .add(new DirectionComponent())
	            .add(state)
	            .add(new RenderableComponent())
	            .add(pathComp);
            engine.addEntity(entity);
        }

        @Override
        public void run() {
            currentAmount ++;
            if (!(currentAmount > amount)) createEnenmy(); else cancel();
        }
    }
}
