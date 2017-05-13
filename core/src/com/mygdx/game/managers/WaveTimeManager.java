package com.mygdx.game.managers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.mygdx.game.Factory.EnemyName;
import com.mygdx.game.Factory.EnemyType;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.states.PlayState;

public class WaveTimeManager {



	public static int CURRENT_WAVE_TIME = 0;
	public static int WAVE = 0;
	// keeps track of amount of enemies spawned each wave
	public static int amountSpawned = 0;
	public static double CURRENT_WAVE_TIME_MILLIS = 0;
	private float time = 0;
	private float timeMs = 0;
	private double totalTime = 0;
	private final double baseHp = 100;
	private double currentEnemyHp = 0;
	private EntityFactory entityFactory;

	public EnemyName getEnemyName() {
		return enemyName;
	}

	private EnemyName enemyName;

	public WaveTimeManager(EntityFactory entityFactory){
		this.entityFactory = entityFactory;
	}
	
	public void tick(float delta){
		if(!PlayState.START_GAME) CURRENT_WAVE_TIME = 0;
		if(PlayState.START_GAME){
			totalTime += delta;
			time += delta;
			timeMs += delta;
			CURRENT_WAVE_TIME_MILLIS = timeMs - Math.floor(timeMs);
			if (timeMs >= 1) timeMs = 0;
				if(time >= 1) {
				CURRENT_WAVE_TIME--;
				time = 0;
			}
			if(CURRENT_WAVE_TIME <= 0){
				final int rand = new Random().nextInt(EnemyName.values().length);
				enemyName = EnemyName.values()[rand];
                WAVE++;
                double health = currentEnemyHp * 1.15; // make modular
                if(WaveTimeManager.WAVE == 1) health = baseHp;
                currentEnemyHp = health;
                spawnEnemies( enemyName , 500 , 10);
				CURRENT_WAVE_TIME = 15;
			}
		}
	}
	/**
	 * This method initiates a timer that schedules tasks at a fixed rate and spawns a enemy
	 * @ The time gap in milliseconds between enemy spawns during a wave
	 * **/
	public void spawnEnemies(EnemyName enemyName , long delayInSec , int amount){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Enemy(enemyName , amount ) , delayInSec  , 500 );
	}


	private class Enemy extends TimerTask {
		int amount;
		private EnemyName enemyName;

		private Enemy(EnemyName enemyName , int amount ){
			this.amount = amount;
			this.enemyName = enemyName;
		}

		@Override
		public void run() {
			if (!(WaveTimeManager.amountSpawned >= amount)){
                System.out.println("HEALTH : " + currentEnemyHp);
                entityFactory.createEnemyEntity(enemyName , currentEnemyHp);
				WaveTimeManager.amountSpawned ++;
			} else {
			    WaveTimeManager.amountSpawned = 0;
			    cancel();
            }
		}
	}

}
