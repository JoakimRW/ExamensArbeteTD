package com.mygdx.game.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.MoneyComponent;
import com.mygdx.game.entites.entitiycomponents.MouseImageComponent;
import com.mygdx.game.entites.entitiycomponents.OffsetComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.projectile.DestinationComponent;
import com.mygdx.game.entites.entitiycomponents.tower.TowerStatComponent;
import com.mygdx.game.entites.systems.TowerPlacementSystem;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.PathFinder;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

public class InputHandler implements InputProcessor {
	private InputHandlerIF _inputHandler;
	private int xAxis = 0;
	private int yAxis = 0;
	private static boolean _isPlacementMode;
	private static TowerType _towerType;
	private static GameStateManager _gsm;
	private static EntityFactory _ef;
	private static OrthographicCamera _gameCamera;
	private static Engine _ashleyEngine;
	private Family _towerFamily = Family.all(MouseImageComponent.class).get();;

	public InputHandler(OrthographicCamera gameCamera, EntityFactory entityFactory, GameStateManager gameStateManager,
			Engine engine) {
		_gameCamera = gameCamera;
		_ef = entityFactory;
		_gsm = gameStateManager;
		_ashleyEngine = engine;
	}

	public void registerInputHandlerSystem(InputHandlerIF inputHandler) {
		_inputHandler = inputHandler;
	}

	/** kallas i update **/
	public void pullInput() {
		setCameraDirection();
	}

	public void setCameraDirection() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			yAxis = 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			yAxis = -1;
		} else {
			yAxis = 0;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			xAxis = 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			xAxis = -1;
		} else {
			xAxis = 0;
		}
		_inputHandler.setCamDir(xAxis, yAxis);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE)
			PlayState.PAUSE = !PlayState.PAUSE;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		debug();
		return false;
	}

	public static void debug() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			ImmutableArray<Entity> projectiles = getAshleyEngine().getEntitiesFor(Families.PROJECTILE);
			if (projectiles == null) {
				return;
			}
			for (int i = 0; i < projectiles.size(); i++) {
				System.out.println(projectiles.get(i).getComponent(DestinationComponent.class));
			}
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			_inputHandler.setAsSelectedTower(getTowerEntityFromTile());
		}
		if (isRightButtonClicked(button)) {
			return false;
		}
		if (_isPlacementMode && isLegalPlacement()) {
			placeTowers(button);
		}

		return false;
	}

	private static Entity getTowerEntityFromTile() {
		if (!_isPlacementMode) {
			Tile tile = getTileAtMouse();
			if (tile != null)
				return tile.getEntity();
		}
		return null;
	}

	private boolean isRightButtonClicked(int button) {

		if (_isPlacementMode) {

			if (button == Input.Buttons.RIGHT) {
				ImmutableArray<Entity> towerEntitys = getAshleyEngine().getEntitiesFor(_towerFamily);
				Entity first = towerEntitys.first();
				getAshleyEngine().removeEntity(first);
				InputHandler.setPlacementMode(false);
				getAshleyEngine().getSystem(TowerPlacementSystem.class).tintTile(null);
				return true;
			}
		}
		return false;
	}

	private static Tile getTileAtMouse() {
		Vector3 mousePos = _gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Tile tile = LevelManager.getTile((int) mousePos.x >> 5, (int) mousePos.y >> 5);
		if (tile != null)
			return tile;
		return null;
	}

	private static boolean isLegalPlacement() {
		Tile tile = getTileAtMouse();
		if (tile != null) {
			if (!isTowerBlockingPath(tile)) {
				return tile.getType() == TileType.FLOOR && tile.isPlaceable();
			}
		}
		return false;
	}

	private static boolean isTowerBlockingPath(Tile tile) {
		if (tile.getType() == TileType.FLOOR) {
			tile.setType(TileType.WALL);
			if (PathFinder.findPath(
					new Vector2(LevelManager.tileSpawn.getCords().x / 32, LevelManager.tileSpawn.getCords().y / 32),
					new Vector2(LevelManager.tileEnd.getCords().x / 32, LevelManager.tileEnd.getCords().y / 32), false,
					false) == null) {
				tile.setType(TileType.FLOOR);
				return true;
			}
			tile.setType(TileType.FLOOR);
		}
		return false;
	}

	private void placeTowers(int button) {
		if (_isPlacementMode) {
			Tile tile = getTileAtMouse();
			ImmutableArray<Entity> towerEntitys = getAshleyEngine().getEntitiesFor(_towerFamily);
			Entity first = towerEntitys.first();
			first.remove(MouseImageComponent.class);
			first.getComponent(PositionComponent.class).position.x = tile.getCords().x;
			first.getComponent(PositionComponent.class).position.y = tile.getCords().y;
			first.add(new OffsetComponent(16, 16));
			tile.setType(TileType.WALL);
			tile.setEntity(first);
			getAshleyEngine().getSystem(TowerPlacementSystem.class).tintTile(null);
			InputHandler.setPlacementMode(false);
			_ef.getPlayer().getComponent(MoneyComponent.class).money -= first.getComponent(TowerStatComponent.class)._buyCost;
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		if (_isPlacementMode) {
			mouseOverTintTiles();
			ImmutableArray<Entity> towerEntitys = getAshleyEngine().getEntitiesFor(_towerFamily);
			Entity first = towerEntitys.first();
			Vector3 mousePos = _gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			PositionComponent component = first.getComponent(PositionComponent.class);
			component.position.x = mousePos.x;
			component.position.y = mousePos.y;
		}

		return false;
	}

	private static void mouseOverTintTiles() {

		Tile tile = getTileAtMouse();
		if (tile != null) {
			getAshleyEngine().getSystem(TowerPlacementSystem.class).tintTile(tile);
		}

	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isPlacementMode() {
		return _isPlacementMode;
	}

	public static void setPlacementMode(boolean isPlacementMode) {
		_isPlacementMode = isPlacementMode;
	}

	public static TowerType getTowerType() {
		return _towerType;
	}

	public static void setTowerType(TowerType towerType) {
		_towerType = towerType;
	}

	public static GameStateManager getGsm() {
		return _gsm;
	}

	public static void setGsm(GameStateManager gsm) {
		_gsm = gsm;
	}

	public static EntityFactory getEf() {
		return _ef;
	}

	public static void setEf(EntityFactory ef) {
		_ef = ef;
	}

	public static void setTowerInfoForPlacement(boolean isPlacementMode, TowerType towerType) {
		setPlacementMode(isPlacementMode);
		setTowerType(towerType);
	}

	public static OrthographicCamera getGameCamera() {
		return _gameCamera;
	}

	public static void setGameCamera(OrthographicCamera gameCamera) {
		_gameCamera = gameCamera;
	}

	public static Engine getAshleyEngine() {
		return _ashleyEngine;
	}

	public static void setAshleyEngine(Engine ashleyEngine) {
		_ashleyEngine = ashleyEngine;
	}

}
