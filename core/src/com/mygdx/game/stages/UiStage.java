package com.mygdx.game.stages;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.entites.towers.LaserTurret;
import com.mygdx.game.managers.GameStateManager;

public class UiStage extends Stage {
	private Skin _skin;
	Table _table;
	Engine _engine;
	LaserTurret _turret = new LaserTurret();
	

	public UiStage(GameStateManager gsm, Engine engine) {
		super();
		initUi(gsm);
		_engine = engine;
		_engine.addEntity(_turret);

	}

	private void initUi(GameStateManager gsm) {
		_skin = new Skin(Gdx.files.internal("MainMenuSkin.json"));
		TextButton testUIButton = new TextButton("UITEST", _skin, "default");
		final Label moneyLabel = new Label("Money : 1000", _skin, "default");
		final Label honeyLabel = new Label("honey", _skin, "default");
		final Label koneyLabel = new Label("koney", _skin, "default");
		_table = new Table();

		Skin uISkin = new Skin();
		uISkin.add("background", new Texture(Gdx.files.internal("interface/uibg.png")));

		_table.setSkin(uISkin);
		_table.setBackground(uISkin.newDrawable(uISkin.getDrawable("background")));
		_table.setPosition(0, 0);
		_table.setBounds(_table.getX(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 6);
		_table.setDebug(true);

		_table.top().left();
		_table.toFront();

		_table.add(testUIButton).padBottom(10f).top().left();

		testUIButton.setTransform(true);
		testUIButton.scaleBy(-0.5f);
		testUIButton.align(Align.topLeft);
		// testUIButton.setSize(Gdx.graphics.getWidth()/12,
		// Gdx.graphics.getHeight()/20);
		_table.add(koneyLabel).padLeft(10f);

		_table.row();
		_table.add(moneyLabel);
		_table.add(honeyLabel).padLeft(10f);

		this.addActor(_table);
	}


	@Override
	public void act() {
		super.act();
	}

	public Table getTable() {
		return _table;
	}

	public void setTable(Table table) {
		this._table = table;
	}

	@Override
	public void draw() {
		this.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.draw();
	}

}
