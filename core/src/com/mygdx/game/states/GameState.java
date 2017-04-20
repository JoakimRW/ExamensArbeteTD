package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Game;
import com.mygdx.game.managers.GameStateManager;

public abstract class GameState {

	// References
	protected GameStateManager _gsm;
	protected Game game;
	protected SpriteBatch _batch;
	protected OrthographicCamera camera;
	
	protected GameState(GameStateManager gsm) {
		_gsm = gsm;
		this.game = gsm.game();
		_batch = game.getBatch();
		camera = game.getCamera();
	}
	
	public abstract void resize(int w, int h);
	public abstract void update(float delta);
	public abstract void render();
	public abstract void dispose();
	public abstract void pause();
	public abstract void resume();
}
