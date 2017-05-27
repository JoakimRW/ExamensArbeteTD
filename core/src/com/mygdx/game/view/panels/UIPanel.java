package com.mygdx.game.view.panels;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.view.sections.LeftSection;
import com.mygdx.game.view.sections.MidSection;
import com.mygdx.game.view.sections.RightSection;

public class UIPanel extends Table {
	private LeftSection _leftSection;
	private MidSection _midSection;
	private RightSection _rightSection;

	public UIPanel(Skin skin) {
		this.setSkin(skin);
		this.setBackground("ui-bg2");
        _leftSection = new LeftSection(skin);
        _midSection = new MidSection(skin);
        _rightSection = new RightSection(skin);
		this.add(_leftSection).size(328,175).expand().align(Align.bottomRight).pad(3);
		this.add(_midSection).fill().align(Align.center).pad(3,3,3,3);
		this.add(_rightSection).size(355,175).expand().align(Align.bottomLeft).pad(3);
	}

	public LeftSection getLeftSection() {
		return _leftSection;
	}

	public MidSection getMidSection() {
		return _midSection;
	}

	public RightSection getRightSection() {
		return _rightSection;
	}
}
