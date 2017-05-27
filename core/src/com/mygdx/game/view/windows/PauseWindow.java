package com.mygdx.game.view.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.utils.Assets;

public class PauseWindow extends Window {

	private TextButton _resumeButton;
	private TextButton _mainMenuButton;

	public PauseWindow(String title, Skin skin) {
		super(title, skin);
        this.setVisible(false);
        Table root = new Table(skin);
        _resumeButton = new TextButton("Resume",Assets.mainMenuSkin);
        _mainMenuButton = new TextButton("Main Menu",Assets.mainMenuSkin);
        root.add(_resumeButton).padBottom(10).row();
        root.add(_mainMenuButton);
        this.add(root);
	}

	public TextButton getResumeButton() {
		return _resumeButton;
	}

	public TextButton getMainMenuButton() {
		return _mainMenuButton;
	}

}
