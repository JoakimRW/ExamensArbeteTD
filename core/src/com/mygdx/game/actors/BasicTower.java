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
	// private Boolean _dragging = false;
	private float _lastX;
	private float _lastY;
	List<DropListener> _listeners = new ArrayList<>();

	public BasicTower(TextureRegion region) {
		_region = region;
		setWidth(_region.getRegionWidth());
		setHeight(_region.getRegionHeight());

		addListener(new InputListener() {
			
			

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (pointer != 0)
					return false;

				_lastX = x;
				_lastY = y;
				return true;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if (pointer != 0)
					return;
				moveBy(x - _lastX, y - _lastY);

				_lastX = x - (x - _lastX);
				_lastY = y - (y - _lastY);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				if (pointer != 0) {
					return;
				}
				
				System.out.println(_listeners);
				for(DropListener listener : _listeners){
					
					listener.drop(getActor());
				}
				
			}

		});

	}
	
	public BasicTower (BasicTower actor){
		this(actor.getTextureRegion());
		_actor = (BasicTower) actor;
	}
	public TextureRegion getTextureRegion() {
		return _region;
	}

	private Actor getActor(){
		return _actor;
	}
	

	public void addDropListener(DropListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1f, 1f, getRotation(),
				true);
	}
	
	

}
