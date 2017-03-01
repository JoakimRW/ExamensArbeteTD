package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.managers.GameStateManager;

public class MainMenuStage extends Stage {

	private Skin skin;

	public MainMenuStage(final GameStateManager gsm) {
		skin = new Skin(Gdx.files.internal("MainMenuSkin.json"));

		final TextButton button = new TextButton("New Game", skin, "default");
		final TextButton exitButton = new TextButton("Quit Game", skin, "default");
		button.setSize(200f, 50f);


		button.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				gsm.setState(GameStateManager.State.PLAY);
			}

		});
		
		exitButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
			
		});
		addActor(exitButton);
		addActor(button);
		
		button.setPosition(getWidth() / 2 - button.getWidth() / 2, getHeight() / 2 + 100);
		exitButton.setPosition(getWidth() / 2 - exitButton.getWidth() / 2, getHeight() / 2 - 100);
	}

}
