package com.mygdx.game.stages;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.PathFinder;

import java.util.ArrayList;

public class GameStage extends Stage implements InputProcessor{


	private final SpriteBatch _batch;
	private final EntityManager _entityManager;
	private GameStateManager _gsm;
	private OrthogonalTiledMapRenderer _renderer;
	private int _xDir = 0;
	private int _yDir = 0;
	private Engine _ashleyEngine;
	public GameStage(GameStateManager gsm , Engine ashleyEngine) {
		this._gsm = gsm;
		LevelManager.loadLevel("maps/simple-map.tmx");
		_renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
		_ashleyEngine = ashleyEngine;
		_batch = new SpriteBatch();
        _entityManager = new EntityManager(ashleyEngine, _batch);
		Assets.load();
    }
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _renderer.setView(_gsm.game().getCamera());
        _renderer.render();
		_batch.begin();
		_entityManager.update(Gdx.graphics.getDeltaTime());
		_batch.setProjectionMatrix(_gsm.game().getCamera().combined);
		_batch.end();
	}

	
	@Override
	public void act() {
		moveCamera(Gdx.graphics.getDeltaTime());
		super.act();
	}
	
	@Override
	public void dispose() {
        _renderer.dispose();
        LevelManager.tiledMap.dispose();
        _batch.dispose();
	}
	
	private void moveCamera(float delta){
        final float cameraSpeed = 10f;
        _gsm.game().getCamera().viewportWidth = Gdx.graphics.getWidth() / 3;
        _gsm.game().getCamera().viewportHeight = Gdx.graphics.getHeight() / 3;
		float cameraPosX = _gsm.game().getCamera().position.x;
		float cameraPosY =  _gsm.game().getCamera().position.y;
		_gsm.game().getCamera().position.set((int)cameraPosX+ _xDir * cameraSpeed , (int) cameraPosY + _yDir * cameraSpeed, 0);
		_gsm.game().getCamera().update();
		float startX = _gsm.game().getCamera().viewportWidth / 2;
		float startY = _gsm.game().getCamera().viewportHeight / 2;
		float width = startX *2;
		float height = startY *2;
		setCameraBoundary(_gsm.game().getCamera() , startX , startY , LevelManager.mapPixelWidth - width , LevelManager.mapPixelHeight - height);
	}
	
	  @Override
	    public boolean keyDown(int keycode) {
	        switch (keycode){
	            case Input.Keys.W:
	                _yDir = 1;
	                break;
	            case Input.Keys.S:
	                _yDir = -1;
	                break;
	            case Input.Keys.A:
	                _xDir = -1;
	                break;
	            case Input.Keys.D:
	                _xDir = 1;
	                break;
	            default: break;
	        }
	        return false;
	    }
	
	  @Override
	    public boolean keyUp(int keycode) {
	        switch (keycode){
	            case Input.Keys.W:
	                _yDir = 0;
	                break;
	            case Input.Keys.S:
	                _yDir = 0;
	                break;
	            case Input.Keys.A:
	                _xDir = 0;
	                break;
	            case Input.Keys.D:
	                _xDir = 0;
	                break;
	            default: break;
	        }
	        return false;
	    }

	  
	  @Override
	    public boolean keyTyped(char character) {
	        return false;
	    }

	    @Override
	    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	        return false;
	    }

	    @Override
	    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	        return false;
	    }

	    @Override
	    public boolean touchDragged(int screenX, int screenY, int pointer) {
	        return false;
	    }

	    @Override
	    public boolean mouseMoved(int screenX, int screenY) {

	         /*
	        if(screenX > Gdx.graphics.getWidth() - 15f){
		        xDir = 1;
	        }else if(screenX < 15f){
	            xDir = -1;
	        }else {
	            xDir = 0;
	        }
	        if (screenY > Gdx.graphics.getHeight() - 15f){
	            yDir = -1;
	        }else if(screenY < 15f){
	            yDir = 1;
	        }else {
	            yDir = 0;
	        }
	        */
	        return false;
	    }

	    @Override
	    public boolean scrolled(int amount) {
	        return false;
	    }

	    private void setCameraBoundary(Camera camera , float startX , float startY , float width , float height){
            Vector3 position = camera.position;
            if(position.x < startX ){
                position.x = startX;
            }
            if(position.y < startY){
                position.y = startY;
            }
            if(position.x > startX + width){
                position.x = startX + width;
            }
            if(position.y > startY + height){
                position.y = startY + height;
			}
            camera.position.set(position);
            camera.update();
        }
	
}
