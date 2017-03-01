package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.managers.GameStateManager;

public class UiStage extends Stage {
	private Skin skin;
	public UiStage(GameStateManager gsm) {
		super();
		initUi(gsm);
	}
	private void initUi(GameStateManager gsm){
		skin = new Skin(Gdx.files.internal("MainMenuSkin.json"));
		final TextButton testUIButton = new TextButton("UITEST", skin, "default");
		this.addActor(testUIButton);
	}
	
	

}
