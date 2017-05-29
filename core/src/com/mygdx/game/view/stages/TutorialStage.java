package com.mygdx.game.view.stages;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.utils.Assets;

public class TutorialStage extends Stage{
	private Table controlsTable;
	private TextButton backBtn;
	
	public TutorialStage() {
		super();
		init();
	}

	private void init() {
		
		setViewport(new ScreenViewport(getCamera()));
		
		getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		getViewport().apply(true);
		
		backBtn = new TextButton("Back to Menu", Assets._skin , "menu");
		controlsTable = createControlsTable();
		
		this.addActor(controlsTable);
		//this.setDebugAll(true);
	}
	
	public Table createControlsTable(){
		Table escTable = new Table(Assets._skin);
		Table keyTable = new Table(Assets._skin);
		
		Table table = new Table(Assets._skin);
		
		Image keyW = new Image(Assets._skin , "keyW");
		Image keyA = new Image(Assets._skin , "keyA");
		Image keyS = new Image(Assets._skin , "keyS");
		Image keyD = new Image(Assets._skin , "keyD");
		Image keyESC = new Image(Assets._skin , "keyESC");
		keyTable.add(new Label("Controls" , Assets._skin , "fontVeraBd24" , "white")).colspan(5).spaceBottom(40f).align(Align.center).row();
		keyTable.add(new Label("[W] Move camera Up" , Assets._skin , "default-font" , "green")).padBottom(10f).padRight(8f).colspan(5).align(Align.center).row();
		keyTable.add(keyW).padRight(8f).colspan(5).spaceBottom(5f).align(Align.center).row();
		keyTable.add(new Label("[A] Move camera Left" , Assets._skin , "default-font" , "green")).padRight(10f);
		keyTable.add(keyA).spaceRight(5f);
		keyTable.add(keyS);
		keyTable.add(keyD).spaceLeft(5f);
		keyTable.add(new Label("[D] Move camera Right" , Assets._skin , "default-font" , "green")).padLeft(10f).row();
		keyTable.add(new Label("[S] Move camera Down" , Assets._skin , "default-font" , "green")).colspan(5).align(Align.center).padTop(10f);
		escTable.add(new Label("[Esc] Pause game", Assets._skin , "default-font" , "green")).padBottom(10f).row();
		escTable.add(keyESC);
		table.add(keyTable);
		table.add(escTable).spaceBottom(20f).row();
		table.add(backBtn).align(Align.bottom).padTop(50f);
		table.setFillParent(true);
		table.center();
		return table;
	}
	
	public Table getControlsTable() {
		return controlsTable;
	}

	public TextButton getBackBtn() {
		return backBtn;
	}
	
	@Override
	public void act() {
		super.act();
	}
	
	@Override
	public void draw() {
		super.draw();
	}
	
}
