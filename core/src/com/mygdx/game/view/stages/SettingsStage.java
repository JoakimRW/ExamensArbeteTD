package com.mygdx.game.view.stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
		
		setViewport(new ScreenViewport(getCamera()));
		getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		getViewport().apply(true);
		
		settingsPanel = new SettingsPanel(Assets._skin);	
		_okButton = new TextButton("OK" , Assets._skin , "menu");
		settingsPanel.setFillParent(true);
		settingsPanel.add(_okButton);
		settingsPanel.setFillParent(true);
		
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
