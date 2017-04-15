package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.managers.EntityManager;

public class UiStage extends Stage {
	private Skin _skin;
	private Skin _turretsSkin;
	Table _table;
	private Image _turret1;
	EntityManager _entityManager;
	private OrthographicCamera _uiCamera;

	public UiStage(EntityManager entityManager, SpriteBatch _batch, OrthographicCamera _uiCamera) {
		super();
		_entityManager = entityManager;
		this._uiCamera = _uiCamera;
		initUi();

	}

	public OrthographicCamera getCamera() {
		return _uiCamera;
	}

	private void initUi() {
		getViewport().setCamera(_uiCamera);
		_skin = new Skin(Gdx.files.internal("MainMenuSkin.json"));
		_turretsSkin = new Skin();
		_turretsSkin.add("turret1", new Texture(Gdx.files.internal("towers/lvl1/turret.png")));
		TextButton testUIButton = new TextButton("UITEST", _skin, "default");
		
		final Label moneyLabel = new Label("Money : 1000", _skin, "default");
		final Label honeyLabel = new Label("honey", _skin, "default");
		final Label koneyLabel = new Label("koney", _skin, "default");

		_turret1 = new Image(_turretsSkin, "turret1");
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
		_table.add(moneyLabel, honeyLabel, _turret1);

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

	public Skin getTurretSkin() {
		return _turretsSkin;
	}

	public Image getTurretActor() {
		return _turret1;
	}

	@Override
	public void draw() {
		this.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.draw();
	}

}
