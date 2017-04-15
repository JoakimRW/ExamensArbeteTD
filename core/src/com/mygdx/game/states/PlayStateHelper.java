package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.actors.DropListener;
import com.mygdx.game.actors.SelectListener;
import com.mygdx.game.entites.towers.LaserTurret;
import com.mygdx.game.stages.GameStage;
import com.mygdx.game.stages.UiStage;
import com.mygdx.game.utils.Tile;

public final class PlayStateHelper {

	private GameStage _gameStage;
	private UiStage _uIStage;
	private SpriteBatch _batch;

	public PlayStateHelper(SpriteBatch batch, GameStage gameStage, UiStage uIStage,
			Engine ashleyEngine) {
		_gameStage = gameStage;
		_uIStage = uIStage;
		_batch = batch;
//		createBasicTower();
	}

	private void addManyTargets(Tile[][]... tiles) {

		for (Tile[][] twodtilearray : tiles) {
			for (Tile[] tileArray : twodtilearray) {
				for (Tile actor : tileArray) {
					_uIStage.addActor(actor);
					System.out.println(actor.getCords());
				}
			}
		}

	}

	private void initDrag() {
		Image turretActor = _uIStage.getTurretActor();

		Skin turretSkin = _uIStage.getTurretSkin();
//		_dragAndDrop.addSource(new Source(turretActor) {
//
//			public Payload dragStart(InputEvent event, float x, float y, int pointer) {
//				System.out.println("Drag Started");
//				// _gameStage.addActor(turretActor);
//				event.setStage(_gameStage);
//				Payload payload = new Payload();
//				turretActor.toFront();
//				payload.setObject(turretActor);
//				payload.setInvalidDragActor(turretActor);
//				payload.setValidDragActor(turretActor);
//				payload.setDragActor(new Image(turretSkin.getDrawable("turret1")));
//
//				// Label validLabel = new Label("turret1", skin);
//				// payload.setValidDragActor(validLabel);
//				//
//				// Label invalidLabel = new Label("turret1", skin);
//				// payload.setInvalidDragActor(invalidLabel);
//
//				return payload;
//			}
//
//			@Override
//			public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
//				event.setStage(_gameStage);
//				System.out.println("STOP target = " + target);
//				super.dragStop(event, x, y, pointer, payload, target);
//
//			}
//
//		});
	}

	public void UiStageControl(Table table) {
		// final Table thetable = table;
		SnapshotArray<Actor> actors = table.getChildren();

		for (Actor actor : actors) {
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {

					// createBasicTower();
				};
			});
		}
		;

	}

	private void createBasicTower() {
		TextureRegion turretImage = new TextureRegion(new Texture(Gdx.files.internal("towers/lvl1/turret.png")));
		LaserTurret turret = new LaserTurret(turretImage);
		turret.addDropListener(new DropListener() {
			@Override
			public void drop(Actor actor, float x, float y, InputEvent event) {
				System.out.println("TEST!");
				createNewTurretOnGameStage(event.getStageX(), event.getStageY());
			}
		});

		turret.addSelectListener(new SelectListener() {

			@Override
			public void select(Actor actor, InputEvent event) {
				fireEvent(event);
			}

		});

		_uIStage.addActor(turret);
		// _ashleyEngine.addEntity(turret);
	}
	private void fireEvent(InputEvent event) {
	}

	public void createNewTurretOnGameStage(float x, float y) {
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("towers/lvl1/turret.png")));
		LaserTurret turret = new LaserTurret(region);
		_gameStage.addActor(turret);
		turret.setPosition(x, y);

	}

}
