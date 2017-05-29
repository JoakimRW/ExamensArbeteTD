package com.mygdx.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
		
		setViewport(new ScreenViewport(getCamera()));
		getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		getViewport().apply(true);
		
		Label youLoseLbl = new Label("You lost" , Assets._skin , "fontVeraBd24" ,"white");
		playAgainBtn = new TextButton("Play Again", Assets._skin , "menu");
		mainMenuBtn = new TextButton("Main Menu", Assets._skin ,  "menu");
		Table container = new Table(Assets._skin);
		youLoseLbl.setColor(Color.RED);
		container.add(youLoseLbl).spaceBottom(100).row();
		container.add(playAgainBtn).align(Align.left).spaceBottom(20).row();
		container.add(mainMenuBtn).align(Align.left);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setFillParent(true);
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
