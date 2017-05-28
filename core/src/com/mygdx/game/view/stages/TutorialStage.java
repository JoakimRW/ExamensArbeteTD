package com.mygdx.game.view.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.utils.Assets;

public class TutorialStage extends Stage{
	
	public TutorialStage() {
		super();
		init();
	}

	private void init() {
		Table table = new Table(Assets._skin);
		Image keyW = new Image(Assets._skin , "keyW");
		table.add(keyW);
		this.addActor(keyW);
	}
	
	@Override
	public void act() {
		super.act();
	}
	
	@Override
	public void draw() {
		super.draw();
	}
	
}
