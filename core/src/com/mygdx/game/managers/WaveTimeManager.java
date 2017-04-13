package com.mygdx.game.managers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.EntityType;
import com.mygdx.game.stages.GameStage;

public class WaveTimeManager {
	
	private int currentWaveTime = 0;
	private int wave = 0;
	// keeps track of amount of enemies spawned each wave
	public static int amountSpawned = 0;

	private float time = 0;

	private EntityFactory entityFactory;
	public WaveTimeManager(EntityFactory entityFactory){
		this.entityFactory = entityFactory;
	}
	
	public void tick(float delta){
		if(!GameStage.START_GAME) currentWaveTime = 0;
		if(GameStage.START_GAME){			
			time += delta;
			if(time >= 1) {
				currentWaveTime--;
				time = 0;
				System.out.println(currentWaveTime);
			}
			if(currentWaveTime <= 0){
				final int rand = new Random().nextInt(EntityType.values().length);
				EntityType enemyType = EntityType.values()[rand];
                System.out.println(enemyType);
                spawnEnemies( enemyType , 500 , 10);
				wave++;
				System.out.println("Wave ---------- " + wave);
				int timeBetweenWave = 15;
				currentWaveTime = timeBetweenWave;
			}
		}
	}
	/**
	 * This method initiates a timer that schedules tasks at a fixed rate and spawns a enemy
	 * @ The time gap in milliseconds between enemy spawns during a wave
	 * **/
	public void spawnEnemies(EntityType enemyType , long delayInSec , int amount){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Enemy(enemyType , amount ) , delayInSec  , 500 );
	}

	private class Enemy extends TimerTask {
		int amount;
		private EntityType enemyType;

		private Enemy(EntityType enemyType , int amount ){
			this.amount = amount;
			this.enemyType = enemyType;
		}

		@Override
		public void run() {
			if (!(WaveTimeManager.amountSpawned >= amount)){
                entityFactory.createEnemyEntity(enemyType);
				WaveTimeManager.amountSpawned ++;
			} else {
			    WaveTimeManager.amountSpawned = 0;
			    cancel();
            }
		}
	}

}
