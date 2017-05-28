package com.mygdx.game.view.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.view.panels.SettingsPanel;

public class PauseWindow extends Window {

	private TextButton _resumeButton;
	private TextButton _mainMenuButton;
	private Table primaryMenu;
	private SettingsPanel settingsPanel;
	private TextButton _settingsButton;
	private TextButton _okButton;
	

	public PauseWindow(String title, Skin skin) {
		super(title, skin);
        this.setVisible(false);
        primaryMenu = new Table(skin);
        settingsPanel = new SettingsPanel(skin);
        _okButton  = new TextButton("OK" , skin , "menu");
        settingsPanel.add(_okButton);
        _resumeButton = new TextButton("Resume",Assets._skin , "menu");
        _mainMenuButton = new TextButton("Main Menu",Assets._skin , "menu");
        _settingsButton = new TextButton("Settings",Assets._skin , "menu");
        
        primaryMenu.add(_resumeButton).padBottom(20f).row();
        primaryMenu.add(_settingsButton).padBottom(20f).row();
        primaryMenu.add(_mainMenuButton);
        this.add(primaryMenu).align(Align.center).fill();
	}
	
	public void showSettings(){
		this.removeActor(primaryMenu);
		this.add(settingsPanel);
	}
	
	public void showMenu(){
		this.removeActor(settingsPanel);
		this.add(primaryMenu);
	}

	public TextButton getResumeButton() {
		return _resumeButton;
	}

	public TextButton getMainMenuButton() {
		return _mainMenuButton;
	}

	public TextButton getSettingsButton() {
		// TODO Auto-generated method stub
		return _settingsButton;
	}
	
	public SettingsPanel getSettingsPanel(){
		return settingsPanel;
	}

	public TextButton getOkButton() {
		return _okButton;
	}

}
