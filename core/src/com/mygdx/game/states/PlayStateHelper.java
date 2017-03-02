package com.mygdx.game.states;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public final class PlayStateHelper {
	

	public static void UiStageControl(Table table) {
		final Table thetable = table;
		SnapshotArray<Actor> actors = table.getChildren();

		for (Actor actor : actors) {
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					System.out.println("hidden");
					thetable.setVisible(false);
					Timer.schedule(new Task(){
	
						@Override
						public void run() {
							thetable.setVisible(true);
							System.out.println("visible");
						}
						
					}, 5f);
				}
			});
			System.out.println("Content: " + actor);
		}

	}

}
