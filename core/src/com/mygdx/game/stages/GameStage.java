package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.PathFinder;

import java.util.ArrayList;

public class GameStage extends Stage implements InputProcessor{
	private ArrayList<Node> path = new ArrayList<>();
	private GameStateManager gsm;
	private OrthogonalTiledMapRenderer renderer;
	private ShapeRenderer shapeRenderer;
	private int xDir = 0;
	private int yDir = 0;
	public GameStage(GameStateManager gsm) {
		this.gsm = gsm;
		LevelManager.loadLevel("maps/simple-map.tmx");
		renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
        path =  PathFinder.findPath(new Vector2(LevelManager.tileSpawn.getTileCenter().x / 32 , LevelManager.tileSpawn.getTileCenter().y / 32), new Vector2(LevelManager.tileEnd.getCords().x / 32 , LevelManager.tileEnd.getCords().y / 32) , true);
	}
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(gsm.game().getCamera());
        renderer.render();
        shapeRenderer.setProjectionMatrix(gsm.game().getCamera().combined);

        if(path !=null){
            for (Node node: path
                    ) {
                shapeRenderer.begin();
                shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1f , 0 , 1f , 1f);
                shapeRenderer.rectLine(node.getCordinates().x * 32 + 16 ,node.getCordinates().y * 32 + 16  , node.getParent().getCordinates().x * 32 + 16 , node.getParent().getCordinates().y* 32 + 16,2f);
                shapeRenderer.end();
            }
        }
	}

	
	@Override
	public void act() {
		moveCamera(Gdx.graphics.getDeltaTime());
		super.act();
	}
	
	@Override
	public void dispose() {
        renderer.dispose();
        LevelManager.tiledMap.dispose();
	}
	
	private void moveCamera(float delta){
        final float cameraSpeed = 10f;
        gsm.game().getCamera().viewportWidth = Gdx.graphics.getWidth() / 3;
        gsm.game().getCamera().viewportHeight = Gdx.graphics.getHeight() / 3;
		float cameraPosX = gsm.game().getCamera().position.x;
		float cameraPosY =  gsm.game().getCamera().position.y;
		gsm.game().getCamera().position.set((int)cameraPosX+ xDir * cameraSpeed , (int) cameraPosY + yDir * cameraSpeed , 0);
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
