package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.managers.GameStateManager;

public class UiStage extends Stage {
	private Skin skin;
	Table table;
	public UiStage(GameStateManager gsm) {
		super();
		initUi(gsm);
	}
	private void initUi(GameStateManager gsm){
		skin = new Skin(Gdx.files.internal("MainMenuSkin.json"));
		final TextButton testUIButton = new TextButton("UITEST", skin, "default");
		table = new Table();
		table.setPosition(0, 0);
		table.setBounds(table.getX(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/6);
		table.setDebug(true);
		table.addActor(testUIButton);
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
