package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.utils.Assets;

public class MainMenuStage extends Stage {


	public MainMenuStage(final GameStateManager gsm) {
		initMainMenu(gsm);
	}

	private void initMainMenu(final GameStateManager gsm) {
		Table table = new Table();
		final TextButton button = new TextButton("New Game", Assets.mainMenuSkin, "default");
		final TextButton exitButton = new TextButton("Quit Game", Assets.mainMenuSkin, "default");
		button.setSize(200f, 50f);

		button.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				gsm.dispose();
				gsm.setState(GameStateManager.State.PLAY);
			}

		});

		exitButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}

		});

		table.setWidth(this.getWidth());
		table.align(Align.center | Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());
		table.padTop(30);
		table.add(button).padBottom(30f);
		table.row();
		table.add(exitButton);

		this.addActor(table);
	}

	@Override
	public void act() {
		super.act();
	}

	@Override
	public void draw() {
		this.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.draw();
	}

}
