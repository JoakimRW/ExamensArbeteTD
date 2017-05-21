package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	private GameStateManager gsm;

	private OrthographicCamera camera;
	
	public static final float SCALE = 2f;
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {
		 Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
		 Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT |  GL20.GL_DEPTH_BUFFER_BIT );
		if(!(Gdx.graphics.getDeltaTime() > .1f)){
            gsm.update(Gdx.graphics.getDeltaTime());
            gsm.render();
        }
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
	}
	
	public OrthographicCamera getCamera(){
		return camera;
	}

	@Override
	public void pause(){
		gsm.pause();
	}

	@Override
	public void resume(){
		gsm.resume();
	}
	
	public SpriteBatch getBatch(){
		return batch;
	}
}
