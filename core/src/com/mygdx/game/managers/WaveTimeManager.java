package com.mygdx.game.managers;

import java.util.Timer;
import java.util.TimerTask;

import com.mygdx.game.Factory.EnemyName;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.controllers.UIStageController;
import com.mygdx.game.states.PlayState;

public class WaveTimeManager {



	public static int CURRENT_WAVE_TIME = 0;
	public static int WAVE = 0;
	// keeps track of amount of enemies spawned each wave
	public static int amountSpawned = 0;
	public static double CURRENT_WAVE_TIME_MILLIS = 0;
	private float time = 0;
	private float timeMs = 0;
	private final double baseHp = 100;
	private double currentEnemyHp = 0;
	private EntityFactory entityFactory;
	private UIStageController uiController;

	public WaveTimeManager(EntityFactory entityFactory, UIStageController uiController){
		this.entityFactory = entityFactory;
		this.uiController = uiController;
	}
	
	public void tick(float delta){
		if(!PlayState.START_GAME) CURRENT_WAVE_TIME = 0;
		if(PlayState.START_GAME &&  WAVE < PlayState.MAX_WAVES){
			time += delta;
			timeMs += delta;
			CURRENT_WAVE_TIME_MILLIS = timeMs - Math.floor(timeMs);
			if (timeMs >= 1) timeMs = 0;
				if(time >= 1) {
				CURRENT_WAVE_TIME--;
				time = 0;
			}
			if(CURRENT_WAVE_TIME <= 0){
				if(WAVE +1 < LevelManager.waveList.size())
				uiController.updateNextEnemyText(LevelManager.waveList.get(WAVE +1).toString());          
                double health = currentEnemyHp * 1.15;
                if(WaveTimeManager.WAVE == 1) health = baseHp;
                currentEnemyHp = health;
                spawnEnemies( LevelManager.waveList.get(WAVE) , 50 , 10);
				CURRENT_WAVE_TIME = 15;
				WAVE++;
				// todo update ui 
				 System.out.println("Wave: " + WAVE + " / " + PlayState.MAX_WAVES);
			}
		}
	}
	/**
	 * This method initiates a timer that schedules tasks at a fixed rate and spawns a enemy
	 * The time gap in milliseconds between enemy spawns during a wave
	 * **/
	public void spawnEnemies(EnemyName enemyName , long delayInSec , int amount){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Enemy(enemyName , amount ) , delayInSec  , 400 );
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
                entityFactory.createEnemyEntity(enemyName , currentEnemyHp);
				WaveTimeManager.amountSpawned ++;
			} else {
			    WaveTimeManager.amountSpawned = 0;
			    cancel();
            }
		}
	}

}
