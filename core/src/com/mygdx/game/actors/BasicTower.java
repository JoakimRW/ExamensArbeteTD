package com.mygdx.game.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class BasicTower extends Actor {

	private BasicTower _actor = this;
	private TextureRegion _region;
	List<DropListener> _dropListeners = new ArrayList<>();
	private List<SelectListener> _selectListeners;

	public BasicTower(TextureRegion region) {
		_region = region;
		setWidth(_region.getRegionWidth());
		setHeight(_region.getRegionHeight());

		addListener(new InputListener() {
			private boolean _inPlacementMode = false;

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (pointer != 0)
					return false;

				if (_inPlacementMode) {
					for (DropListener listener : _dropListeners) {
						listener.drop(getActor(), x, y, event);
					}
					_inPlacementMode = false;
					return true;
				}
				if (!_inPlacementMode) {

					for (SelectListener selectListener : _selectListeners) {
						selectListener.select(getActor(),event);
					}

					_inPlacementMode = true;
					return true;
				}
				return false;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (pointer != 0) {
					return;
				}
				if (_inPlacementMode) {

				}
			}

		});

	}

	public BasicTower(BasicTower actor) {
		this(actor.getTextureRegion());
		_actor = (BasicTower) actor;
	}

	public TextureRegion getTextureRegion() {
		return _region;
	}

	private Actor getActor() {
		return _actor;
	}

	public void addDropListener(DropListener listener) {
		_dropListeners.add(listener);
	}

	public void addSelectListener(SelectListener listener) {
		_selectListeners.add(listener);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1f, 1f, getRotation(),
				true);
	}

	@Override
	public void act(float delta) {

		super.act(delta);
	}

}
