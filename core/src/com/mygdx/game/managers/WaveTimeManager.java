package com.mygdx.game.managers;

import com.mygdx.game.Factory.EnemyFactory;
import com.mygdx.game.stages.GameStage;

public class WaveTimeManager {
	
	public static int nextWaveTime = 30;
	public static int wave = 0;
	private float time = 0;
	
	private EnemyFactory enemySpawner;
	public WaveTimeManager(EnemyFactory enemySpawner){
		this.enemySpawner = enemySpawner;
	}
	
	public void tick(float delta){
		if(!GameStage.START_GAME) nextWaveTime = 0;
		if(GameStage.START_GAME){			
			time += delta;
			if(time >= 1) {
				nextWaveTime--;
				time = 0;
				System.out.println(nextWaveTime);
			}
			if(nextWaveTime <= 0){
				enemySpawner.spawnEnemies(10 + wave , 100, 60, 500);
				wave++;
				System.out.println("Wave ---------- " + wave);
				nextWaveTime = 30;
			}
		}
	}
}
