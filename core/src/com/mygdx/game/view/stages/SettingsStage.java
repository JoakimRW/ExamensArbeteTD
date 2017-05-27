package com.mygdx.game.view.stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.utils.Assets;

public class SettingsStage extends Stage {
	
	private Slider _musicVolSlider;
	private Slider _soundFxSlider;
	public SettingsStage() {
		super();
		intiSettingsStage();
	}
	public void intiSettingsStage(){
		getCamera().position.set(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2 , 0);
		getCamera().update();
		_musicVolSlider = new Slider(0, 1f, 0.01f, false, Assets._skin);
		_soundFxSlider = new Slider(0, 1f, 0.01f, false, Assets._skin);
		Label musicLbl = new Label("Music" , Assets._skin);
		Label sfxLbl = new Label("Sound Effects" , Assets._skin);
		Table table = new Table(Assets._skin);
		table.add(musicLbl).row();
		table.add(_musicVolSlider).spaceBottom(20f).row();
		table.add(sfxLbl).row();
		table.add(_soundFxSlider);
		table.setWidth(Gdx.graphics.getWidth());
		table.setHeight(Gdx.graphics.getHeight());
		addActor(table);
	}
	@Override
	public void draw() {
		this.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.draw();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	
}
