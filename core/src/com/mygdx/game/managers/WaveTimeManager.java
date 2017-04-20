package com.mygdx.game.managers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.mygdx.game.Factory.EnemyType;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.states.PlayState;

public class WaveTimeManager {



	public static int CURRENT_WAVE_TIME = 0;
	public static int WAVE = 0;
	// keeps track of amount of enemies spawned each wave
	public static int amountSpawned = 0;
	private float time = 0;
	private EntityFactory entityFactory;

	public EnemyType getEnemyType() {
		return enemyType;
	}

	private EnemyType enemyType;

	public WaveTimeManager(EntityFactory entityFactory){
		this.entityFactory = entityFactory;
	}
	
	public void tick(float delta){
		if(!PlayState.START_GAME) CURRENT_WAVE_TIME = 0;
		if(PlayState.START_GAME){			
			time += delta;
			if(time >= 1) {
				CURRENT_WAVE_TIME--;
				time = 0;
				System.out.println(CURRENT_WAVE_TIME);
			}
			if(CURRENT_WAVE_TIME <= 0){
				final int rand = new Random().nextInt(EnemyType.values().length);
				enemyType = EnemyType.values()[rand];
                System.out.println(enemyType);
                spawnEnemies( enemyType , 500 , 10);
				WAVE++;
				int timeBetweenWave = 15;
				CURRENT_WAVE_TIME = timeBetweenWave;
			}
		}
	}
	/**
	 * This method initiates a timer that schedules tasks at a fixed rate and spawns a enemy
	 * @ The time gap in milliseconds between enemy spawns during a wave
	 * **/
	public void spawnEnemies(EnemyType enemyType , long delayInSec , int amount){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Enemy(enemyType , amount ) , delayInSec  , 500 );
	}


	private class Enemy extends TimerTask {
		int amount;
		private EnemyType enemyType;

		private Enemy(EnemyType enemyType , int amount ){
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
