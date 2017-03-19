package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.entites.towers.LaserTurret;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.stages.GameStage;
import com.mygdx.game.stages.UiStage;

public final class PlayStateHelper {

	DragAndDrop _dragAndDrop;
	Engine _ashleyEngine;
	UiStage _uIStage;
	GameStage _gameStage;

	public PlayStateHelper(UiStage uIStage, GameStage gameStage, DragAndDrop dragAndDrop, Engine ashleyEngine) {
		_uIStage = uIStage;
		_gameStage = gameStage;
		_dragAndDrop = dragAndDrop;
		_ashleyEngine = ashleyEngine;
	}

	public void UiStageControl(Table table) {
		// final Table thetable = table;
		SnapshotArray<Actor> actors = table.getChildren();

		for (Actor actor : actors) {
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

					createBasicTower();
				};
			});
		}
		;
	}

	private void createBasicTower() {

		final Skin skin = new Skin();

		skin.add("default", new LabelStyle(new BitmapFont(), Color.WHITE));

		skin.add("turret", ((LaserTurret) _ashleyEngine.getEntities().first()).getTurretTexture());
		Image dragImage = new Image(skin, "turret");
		_uIStage.addActor(dragImage);
		
		dragImage.setPosition(Gdx.input.getX(), Gdx.input.getY());
		_dragAndDrop.addSource(new Source(dragImage) {
			public Payload dragStart(InputEvent event, float x, float y, int pointer) {

				Payload payload = new Payload();
				payload.setObject("turret");

				payload.setDragActor(new Image(skin.getDrawable("turret")));

				Label validLabel = new Label("turret", skin);
				payload.setValidDragActor(validLabel);

				Label invalidLabel = new Label("turret", skin);
				payload.setInvalidDragActor(invalidLabel);

				return payload;
			}
		});
		
		_dragAndDrop.addTarget(new Target(LevelManager.getTile(Gdx.input.getX()/32, Gdx.input.getY()/32)){
			@Override
			public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.GREEN);
				return true;
			}
			@Override
			public void drop(Source source, Payload payload, float x, float y, int pointer) {
				getActor().getStage().addActor(source.getActor());
				source.getActor().setPosition(x, y);
				System.out.println("Dropped!");
			}
			@Override
			public void reset(Source source, Payload payload) {
				// TODO Auto-generated method stub
				super.reset(source, payload);
			}
		});

		_dragAndDrop.addTarget(new Target(dragImage) {
			public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
				
				
				getActor().setColor(Color.GREEN);
				return true;
			}

			public void reset(Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop(Source source, Payload payload, float x, float y, int pointer) {

				Texture turretTexture = ((LaserTurret) (_ashleyEngine.getEntities().first())).getTurretTexture();
				Image turretImage = new Image(turretTexture);
				_gameStage.addActor(turretImage);
				turretImage.setPosition(x, y);
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
			}
		});

		_dragAndDrop.addTarget(new Target(dragImage) {
			public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.RED);
				return false;
			}

			public void reset(Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop(Source source, Payload payload, float x, float y, int pointer) {
				Texture turretTexture = ((LaserTurret) (_ashleyEngine.getEntities().first())).getTurretTexture();
				Image turretImage = new Image(turretTexture);
				_gameStage.addActor(turretImage);
				turretImage.setPosition(x, y);
			}
		});
		
		
	}

}
