package com.mygdx.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.utils.Assets;

public class WinStage extends Stage {
	
	private TextButton playAgainBtn;
	private TextButton mainMenuBtn;
	
	public WinStage(){
		super();
		initWinStage();
	}

	private void initWinStage() {
		getCamera().position.set(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2 , 0);
		getCamera().update();
		Label youWinLabel = new Label("You Won!" , Assets._skin , "fontVera24" , "white");
		playAgainBtn = new TextButton("Play Again", Assets._skin , "menu");
		mainMenuBtn = new TextButton("Main Menu", Assets._skin , "menu");
		Table container = new Table(Assets._skin);
		youWinLabel.setColor(Color.GREEN);
		container.add(youWinLabel).spaceBottom(100).row();
		container.add(playAgainBtn).align(Align.left).spaceBottom(20).row();
		container.add(mainMenuBtn).align(Align.left);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		addActor(container);
	}

	public TextButton getPlayAgainBtn() {
		// TODO Auto-generated method stub
		return playAgainBtn;
	}
	
	public TextButton getMainMenuBtn(){
		return mainMenuBtn;
	}
}
