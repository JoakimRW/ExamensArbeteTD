package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.managers.GameStateManager;

public class SplashState extends GameState {

	float acc = 0f;
	Texture tex;
	Sprite sprite;
	public SplashState(GameStateManager gsm) {
		super(gsm);
		tex = new Texture("badlogic.jpg");
		sprite = new Sprite(tex);
	}

	@Override
	public void update(float delta) {
		acc += delta;
		if (acc > 3) {
			gsm.setState(GameStateManager.State.MAINMENU);
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		_batch.setProjectionMatrix(camera.combined);
		_batch.begin();
//		batch.draw(tex, Gdx.graphics.getWidth() / 4 - tex.getWidth(), Gdx.graphics.getHeight() / 4 - tex.getWidth(),
//				0, 0);
		sprite.draw(_batch);
		_batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
