package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.managers.GameStateManager;

public class UiStage extends Stage {
	private Skin skin;
	Table table;

	public UiStage(GameStateManager gsm) {
		super();
		initUi(gsm);
	}

	private void initUi(GameStateManager gsm) {
		skin = new Skin(Gdx.files.internal("MainMenuSkin.json"));
		TextButton testUIButton = new TextButton("UITEST", skin, "default");
		final Label moneyLabel = new Label("Money : 1000", skin, "default");
		final Label honeyLabel = new Label("honey", skin, "default");
		final Label koneyLabel = new Label("koney", skin, "default");
		table = new Table();
		
		Skin uISkin = new Skin();
		uISkin.add("background", new Texture (Gdx.files.internal("interface/uibg.png")));
		
		
		
		table.setSkin(uISkin);
		table.setBackground(uISkin.newDrawable(uISkin.getDrawable("background")));
		table.setPosition(0, 0);
		table.setBounds(table.getX(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 6);
		table.setDebug(true);

		table.top().left();
		table.toFront();

		table.add(testUIButton).padBottom(10f).top().left();

		testUIButton.setTransform(true);
		testUIButton.scaleBy(-0.5f);
		testUIButton.align(Align.topLeft);
		// testUIButton.setSize(Gdx.graphics.getWidth()/12,
		// Gdx.graphics.getHeight()/20);
		table.add(koneyLabel).padLeft(10f);

		table.row();
		table.add(moneyLabel);
		table.add(honeyLabel).padLeft(10f);

		this.addActor(table);
	}

	@Override
	public void act() {
		super.act();
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	@Override
	public void draw() {
		this.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.draw();
	}

}
