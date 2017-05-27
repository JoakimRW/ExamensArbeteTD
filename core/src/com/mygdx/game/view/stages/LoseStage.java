package com.mygdx.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.utils.Assets;

public class LoseStage extends Stage {
	
	private TextButton playAgainBtn;
	private TextButton mainMenuBtn;
	
	public TextButton getPlayAgainBtn() {
		return playAgainBtn;
	}
	
	public TextButton getMainMenuBtn() {
		return mainMenuBtn;
	}

	public LoseStage() {
		super();
		initLoseStage();
	}
	
	private void initLoseStage() {
		getCamera().position.set(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2 , 0);
		getCamera().update();
		Label youLoseLbl = new Label("You lost" , Assets._skin);
		playAgainBtn = new TextButton("Play Again", Assets._skin);
		mainMenuBtn = new TextButton("Main Menu", Assets._skin);
		Table container = new Table(Assets._skin);
		youLoseLbl.setColor(Color.RED);
		container.add(youLoseLbl).spaceBottom(100).row();
		container.add(playAgainBtn).align(Align.left).spaceBottom(20).row();
		container.add(mainMenuBtn).align(Align.left);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		addActor(container);
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
