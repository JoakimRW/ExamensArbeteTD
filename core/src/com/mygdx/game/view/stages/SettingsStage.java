package com.mygdx.game.view.stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.view.panels.SettingsPanel;

public class SettingsStage extends Stage {
	
	private SettingsPanel settingsPanel;
	private TextButton _okButton;
	public SettingsStage() {
		super();
		intiSettingsStage();
	}
	public void intiSettingsStage(){
		getCamera().position.set(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2 , 0);
		getCamera().update();
		settingsPanel = new SettingsPanel(Assets._skin);	
		_okButton = new TextButton("OK" , Assets._skin , "menu");
		settingsPanel.setFillParent(true);
		settingsPanel.add(_okButton);
		addActor(settingsPanel);	
	}
	@Override
	public void draw() {
		super.draw();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}
	public TextButton getOkButton() {
		// TODO Auto-generated method stub
		return _okButton;
	}
	
}
