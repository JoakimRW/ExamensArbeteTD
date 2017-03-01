package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;

public class PlayState extends GameState {

	private GameStateManager gsm;
	private OrthogonalTiledMapRenderer renderer;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;
		LevelManager.loadLevel("maps/simple-map.tmx");
		renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
        gsm.game().getCamera().update();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(gsm.game().getCamera());
        renderer.render();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        renderer.dispose();
        LevelManager.tiledMap.dispose();
	}

}
