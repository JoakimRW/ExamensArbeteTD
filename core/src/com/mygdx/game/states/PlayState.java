package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

public class PlayState extends GameState implements InputProcessor{

	private GameStateManager gsm;
	private OrthogonalTiledMapRenderer renderer;
	private ShapeRenderer shapeRenderer;
	private int xDir = 0;
	private int yDir = 0;
	public static float cameraSpeed = 200f;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;
		LevelManager.loadLevel("maps/simple-map.tmx");
		renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void update(float delta) {
        shapeRenderer.setProjectionMatrix(gsm.game().getCamera().combined);
        moveCamera(delta);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(gsm.game().getCamera());
        renderer.render();
	}

	/** for debugging **/
	private void drawTiles(){
        for (int row = 0; row < LevelManager.tiles.length; row++) {
            for (int col = 0; col < LevelManager.tiles[0].length; col++) {
                Tile tile = LevelManager.getTile(row , col);

                shapeRenderer.begin();
                // ritar tile grid , och fyller med vit färg där fiender inte kan gå
                if(tile.getType() == TileType.FLOOR){
                    shapeRenderer.setColor(1,1,1,.2f);
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }else {
                    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(.8f,0,0,.2f);
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }
                shapeRenderer.end();
            }
        }
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
    }

    @Override
	public void dispose() {
		// TODO Auto-generated method stub
        renderer.dispose();
        LevelManager.tiledMap.dispose();
	}

	public void moveCamera(float delta){
        gsm.game().getCamera().viewportWidth = Gdx.graphics.getWidth() / 4;
        gsm.game().getCamera().viewportHeight = Gdx.graphics.getHeight() / 4;
        float cameraPosX = gsm.game().getCamera().position.x;
        float cameraPosY =  gsm.game().getCamera().position.y;
        gsm.game().getCamera().position.set(cameraPosX + (xDir * cameraSpeed) * delta , cameraPosY + (yDir * cameraSpeed) * delta, 0);

        gsm.game().getCamera().update();
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
        System.out.println(screenX );
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

}
