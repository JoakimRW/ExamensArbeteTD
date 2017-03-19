package com.mygdx.game.stages;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Assets;

public class GameStage extends Stage implements InputProcessor{


	public static int PlAYER_HEALTH = 30;
	public static boolean GAME_OVER = false;
	public static boolean START_GAME = false;
	
	private final SpriteBatch batch;
	private final EntityManager entityManager;
	private GameStateManager gsm;
	private OrthogonalTiledMapRenderer renderer;
	// TODO make camera code ashley components and system
	private int xDir = 0;
	private int yDir = 0;

	public GameStage(GameStateManager gsm) {
        Assets.load();
        this.gsm = gsm;
        LevelManager.loadLevel("maps/simple-map.tmx");
        renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
        Engine ashleyEngine = new Engine();
        batch = new SpriteBatch();
        entityManager = new EntityManager(ashleyEngine, batch);
        System.out.println("*************** To Start the game , Press Enter! ***************");
    }
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(gsm.game().getCamera());
        renderer.render();
		batch.begin();
		entityManager.update(Gdx.graphics.getDeltaTime());
		batch.setProjectionMatrix(gsm.game().getCamera().combined);
		batch.end();
	}

	
	@Override
	public void act() {
		moveCamera(Gdx.graphics.getDeltaTime());
		if(PlAYER_HEALTH == 0){
			GAME_OVER = true;
		}
		super.act();
	}
	
	@Override
	public void dispose() {
        renderer.dispose();
        LevelManager.tiledMap.dispose();
        batch.dispose();
	}
	
	private void moveCamera(float delta){
        final float cameraSpeed = 10f;
        gsm.game().getCamera().viewportWidth = Gdx.graphics.getWidth() / 3;
        gsm.game().getCamera().viewportHeight = Gdx.graphics.getHeight() / 3;
		float cameraPosX = gsm.game().getCamera().position.x;
		float cameraPosY =  gsm.game().getCamera().position.y;
		gsm.game().getCamera().position.set((int)cameraPosX+ xDir * cameraSpeed , (int) cameraPosY + yDir * cameraSpeed, 0);
		gsm.game().getCamera().update();
		float startX = gsm.game().getCamera().viewportWidth / 2;
		float startY = gsm.game().getCamera().viewportHeight / 2;
		float width = startX *2;
		float height = startY *2;
		setCameraBoundary(gsm.game().getCamera() , startX , startY , LevelManager.mapPixelWidth - width , LevelManager.mapPixelHeight - height);
	}
	
	  @Override
	    public boolean keyDown(int keycode) {
	        switch (keycode){
	            case Input.Keys.W:
	                yDir = 1;
	                break;
	            case Input.Keys.S:
	                yDir = -1;
	                break;
	            case Input.Keys.A:
	                xDir = -1;
	                break;
	            case Input.Keys.D:
	                xDir = 1;
	                break;
	            default: break;
	        }
	        return false;
	    }
	
	  @Override
	    public boolean keyUp(int keycode) {
	        switch (keycode){
	            case Input.Keys.W:
	                yDir = 0;
	                break;
	            case Input.Keys.S:
	                yDir = 0;
	                break;
	            case Input.Keys.A:
	                xDir = 0;
	                break;
	            case Input.Keys.D:
	                xDir = 0;
	                break;
	            case Input.Keys.ENTER:
	            	if(!START_GAME)System.out.println("Game Started , Spawning first Wave");
	                START_GAME = true;
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
