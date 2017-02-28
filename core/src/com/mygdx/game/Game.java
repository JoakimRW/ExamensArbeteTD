package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private GameStateManager gsm;
	
	private OrthographicCamera camera;
	
	public static final float SCALE = 2.0f;
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		
//		
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
	
	
	@Override
	public void resize(int width, int height) {
		
		gsm.resize((int) (width /SCALE), (int) (height / SCALE));
		
		super.resize(width, height);
	}

	@Override
	public void dispose () {
		gsm.dispose();
		batch.dispose();
		img.dispose();
	}
	
	public OrthographicCamera getCamera(){
		return camera;
	}
	
	public SpriteBatch getBatch(){
		return batch;
	}
}
