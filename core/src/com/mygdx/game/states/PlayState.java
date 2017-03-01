package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

public class PlayState extends GameState {

	private GameStateManager gsm;
	private OrthogonalTiledMapRenderer renderer;
	private ShapeRenderer shapeRenderer;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;
		LevelManager.loadLevel("maps/simple-map.tmx");
		renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
        gsm.game().getCamera().update();
        shapeRenderer.setProjectionMatrix(gsm.game().getCamera().combined);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(gsm.game().getCamera());
        renderer.render();
        // drawTiles();
	}

	/** for debugging **/
	private void drawTiles(){
        for (int row = 0; row < LevelManager.tiles.length; row++) {
            for (int col = 0; col < LevelManager.tiles[0].length; col++) {
                Tile tile = LevelManager.getTile(row , col);

                shapeRenderer.begin();
                // ritar tile grid , och fyller med vit färg där fiender inte kan gå
                if(tile.getType() == TileType.FLOOR){
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }else {
                    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }
                shapeRenderer.end();
            }
        }
    }

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        renderer.dispose();
        LevelManager.tiledMap.dispose();
	}

}
