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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.managers.EntityManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Assets;

public class GameStage extends Stage{

	public static int PlAYER_HEALTH = 30;
	public static boolean GAME_OVER = false;
	public static boolean START_GAME = false;

	private final SpriteBatch batch;
	private final EntityManager entityManager;
	private GameStateManager gsm;
	private OrthogonalTiledMapRenderer renderer;
	Engine _ashleyEngine;
	// TODO make camera code ashley components and system
	private int xDir = 0;
	private int yDir = 0;
	private ShapeRenderer sr;

	public GameStage(GameStateManager gsm, Engine ashleyEngine) {
		Assets.load();
		this.gsm = gsm;
		LevelManager.loadLevel("maps/simple-map.tmx");
		renderer = new OrthogonalTiledMapRenderer(LevelManager.tiledMap);
		_ashleyEngine = ashleyEngine;
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		batch = new SpriteBatch();
		entityManager = new EntityManager(_ashleyEngine, batch, sr);
		System.out.println("*************** To Start the game , Press Enter! ***************");
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.setView(gsm.game().getCamera());
		renderer.render();
		batch.begin();
		sr.begin();
		entityManager.update(Gdx.graphics.getDeltaTime());
		batch.setProjectionMatrix(gsm.game().getCamera().combined);
		sr.setProjectionMatrix(gsm.game().getCamera().combined);
		sr.end();
		batch.end();
	}

	@Override
	public void act() {
		moveCamera(Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (!START_GAME)
				System.out.println("Game Started , Spawning first Wave");
			START_GAME = true;
		}
		if (PlAYER_HEALTH == 0) {
			GAME_OVER = true;
		}
		super.act();
	}

	@Override
	public void dispose() {
		sr.dispose();
		renderer.dispose();
		LevelManager.tiledMap.dispose();
		batch.dispose();
	}

	private void moveCamera(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			yDir = 1;
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			yDir = -1;
		}else {
			yDir = 0;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			xDir = -1;
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			xDir = 1;
		}else {
			xDir = 0;
		}
		final float cameraSpeed = 10f;
		gsm.game().getCamera().viewportWidth = Gdx.graphics.getWidth() / 3;
		gsm.game().getCamera().viewportHeight = Gdx.graphics.getHeight() / 3;
		float cameraPosX = gsm.game().getCamera().position.x;
		float cameraPosY = gsm.game().getCamera().position.y;
		gsm.game().getCamera().position.set((int) cameraPosX + xDir * cameraSpeed,
				(int) cameraPosY + yDir * cameraSpeed, 0);
		gsm.game().getCamera().update();
		float startX = gsm.game().getCamera().viewportWidth / 2;
		float startY = gsm.game().getCamera().viewportHeight / 2;
		float width = startX * 2;
		float height = startY * 2;
		setCameraBoundary(gsm.game().getCamera(), startX, startY, LevelManager.mapPixelWidth - width,
				LevelManager.mapPixelHeight - height);
	}

	private void setCameraBoundary(Camera camera, float startX, float startY, float width, float height) {
		Vector3 position = camera.position;
		if (position.x < startX) {
			position.x = startX;
		}
		if (position.y < startY) {
			position.y = startY;
		}
		if (position.x > startX + width) {
			position.x = startX + width;
		}
		if (position.y > startY + height) {
			position.y = startY + height;
		}
		camera.position.set(position);
		camera.update();
	}

}
