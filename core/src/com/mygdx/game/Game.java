package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.utils.Assets;

public class Game extends ApplicationAdapter {
	public static final String TITLE = "Mars Tower Defense";
	SpriteBatch batch;
	public static float VOLUME_MUSIC = .5f;
	public static float VOLUME_SOUNDFX = .5f;
	public static boolean isMuted;
	public static boolean isFullscreen;
	private GameStateManager gsm;

	private OrthographicCamera camera;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		Assets.createSkin();
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
		
		gsm.resize(width, height);
		
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
