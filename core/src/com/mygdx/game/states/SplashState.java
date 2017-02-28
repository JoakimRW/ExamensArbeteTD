package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.managers.GameStateManager;

public class SplashState extends GameState {

	float acc = 0f;
	Texture tex;

	public SplashState(GameStateManager gsm) {
		super(gsm);
		tex = new Texture("assets/badlogic.jpg");
	}

	@Override
	public void update(float delta) {
		acc += delta;
		if (acc > 3) {
			gsm.setState(GameStateManager.State.PLAY);
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		batch.draw(tex, Gdx.graphics.getWidth() / 4 - tex.getWidth(), Gdx.graphics.getHeight() / 4 - tex.getWidth(),
//				0, 0);
//		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
