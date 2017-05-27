package com.mygdx.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Game;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.GameStateManager.State;
import com.mygdx.game.utils.Assets;

public class MainMenuStage extends Stage {


	public MainMenuStage(final GameStateManager gsm) {
		initMainMenu(gsm);
	}

	private void initMainMenu(final GameStateManager gsm) {
		Table table = new Table();
		final Label title = new Label(Game.TITLE , Assets._skin , "font24" , "green");
		final TextButton button = new TextButton("New Game", Assets._skin, "regular");
		final TextButton settingsBtn = new TextButton("Settings", Assets._skin, "regular");
		final TextButton exitButton = new TextButton("Quit Game", Assets._skin, "regular");
		button.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				gsm.dispose();
				gsm.setState(GameStateManager.State.PLAY);
			}

		});
		settingsBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gsm.setState(State.SETTINGS);
			}
		});
		exitButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}

		});
		
		
		table.align(Align.center);
		table.add(title).padBottom(Gdx.graphics.getHeight() / 4).align(Align.top).row();

		table.add(button).align(Align.left).padBottom(30f);
		table.row();
		table.add(settingsBtn).align(Align.left).padBottom(30f);
		table.row();
		table.add(exitButton).align(Align.left);
		table.setFillParent(true);
		this.addActor(table);
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
