package com.mygdx.game.managers;

import java.util.Random;

import com.mygdx.game.Factory.EnemyFactory;
import com.mygdx.game.Factory.EntityType;
import com.mygdx.game.stages.GameStage;

public class WaveTimeManager {
	
	public static int currentWaveTime = 0;
	public static int wave = 0;
	private float time = 0;
	private int timeBetweenWave = 15;
	
	private EnemyFactory enemySpawner;
	public WaveTimeManager(EnemyFactory enemySpawner){
		this.enemySpawner = enemySpawner;
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
				enemySpawner.spawnEnemies(10 + wave , 100, 75, 500 , enemyType);
				wave++;
				System.out.println("Wave ---------- " + wave);
				currentWaveTime = timeBetweenWave;
			}
		}
	}
}
