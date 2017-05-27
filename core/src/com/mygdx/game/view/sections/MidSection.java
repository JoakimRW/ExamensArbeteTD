package com.mygdx.game.view.sections;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.view.panels.StatPanel;

public class MidSection extends Table {
	private TextButton _nextWaveBtn;
	private Label _next_wave_time_value;
	private Label _next_enemy_value;

	public MidSection(Skin skin) {
		this.setSkin(skin);
        Table waveInfoContainer = new Table(skin);
        Table nextEnemyTable = new StatPanel(skin);
        Table nextWaveTable = new StatPanel(skin);
        Table buttonContainer = new Table(skin);
        // labels
        Label nextEnemyText = new Label("Next Enemy", skin, "statPanelFontWaveInfo", "white");
        _next_enemy_value = new Label("",skin , "statPanelFontWaveInfo","white");

        Label nextWaveTimeText = new Label("In:", skin, "statPanelFontWaveInfo", "white");
        _next_wave_time_value = new Label("",skin , "statPanelFontWaveInfo","white");
        // buttons
        _nextWaveBtn = new TextButton("START",skin);
        // tables
        waveInfoContainer.add(nextEnemyText).align(Align.bottomLeft);
        waveInfoContainer.add(nextWaveTimeText).align(Align.bottomLeft).row();
        nextEnemyTable.add(_next_enemy_value).align(Align.center);
        nextWaveTable.add(_next_wave_time_value).align(Align.bottom).expand().prefWidth(62).align(Align.center);
        waveInfoContainer.add(nextEnemyTable).align(Align.bottomLeft).expand().spaceRight(3f);
        waveInfoContainer.add(nextWaveTable).align(Align.bottomRight).expand().spaceLeft(3f);
        waveInfoContainer.setWidth(150);
        this.add(waveInfoContainer).expand().align(Align.bottom).row();
        buttonContainer.add(_nextWaveBtn).expand().fill().spaceTop(0);
        this.add(buttonContainer).expand().fillX().spaceTop(0).align(Align.bottom).padBottom(2);
	}

	public TextButton getNextWaveBtn() {
		return _nextWaveBtn;
	}

	public Label getNextWaveTimeValue() {
		return _next_wave_time_value;
	}

	public Label getnextEnemyValue() {
		return _next_enemy_value;
	}
}
