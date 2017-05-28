package com.mygdx.game.view.panels;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Game;
import com.mygdx.game.utils.Assets;

public class SettingsPanel extends Table {
	
	private Slider _musicVolSlider;
	private Slider _soundFxSlider;

	public SettingsPanel(Skin skin) {
		super(skin);
		_musicVolSlider = new Slider(0, 1f, 0.01f, false, Assets._skin);
		_soundFxSlider = new Slider(0, 1f, 0.01f, false, Assets._skin);
		_musicVolSlider.setValue(Game.VOLUME_MUSIC);
		_soundFxSlider.setValue(Game.VOLUME_SOUNDFX);
		Label musicLbl = new Label("Music" , Assets._skin , "fontVera20" , "white");
		Label sfxLbl = new Label("Sound Effects" , Assets._skin , "fontVera20" , "white");
		this.add(musicLbl).align(Align.left).row();
		this.add(_musicVolSlider).spaceBottom(20f).row();
		this.add(sfxLbl).align(Align.left).row();
		this.add(_soundFxSlider).spaceBottom(20).row();
	}
	
	public Slider getMusicVolSlider() {
		return _musicVolSlider;
	}

	public Slider getSoundFxSlider() {
		return _soundFxSlider;
	}

}
