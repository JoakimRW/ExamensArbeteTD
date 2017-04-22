package com.mygdx.game.entites.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Tile;

public class InputHandler implements InputProcessor {
	private InputHandlerIF _inputHandler;
	private int xAxis = 0;
	private int yAxis = 0;
	private static boolean _isPlacementMode;
	private static TowerType _towerType;
	private static GameStateManager _gsm;
	private static EntityFactory _ef;
	private static OrthographicCamera _gameCamera;

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
		_inputHandler.moveCam(xAxis, yAxis);
	}

	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (_isPlacementMode) {
			
			if (button == Input.Buttons.RIGHT) {
				System.out.println("DISABLING PLACEMENT MODE");
				InputHandler.setPlacementMode(false);
				return false;
			}
			
			Vector3 mousePos = _gameCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			Tile tile = LevelManager.getTile((int) mousePos.x >> 5, (int) mousePos.y >> 5);
			System.out.println("MOUSEPOS = " + mousePos);
			System.out.println("TILE = " + tile);
			System.out.println("Tile Coordinates = " + tile.getCords());
			_ef.createTowerEntity(_towerType, tile.getCords().x, tile.getCords().y);
			InputHandler.setPlacementMode(false);
		}

		return false;
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
		// TODO Auto-generated method stub
		return false;
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
		InputHandler._isPlacementMode = isPlacementMode;
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

	public static void setTowerInfoForPlacement(boolean isPlacementMode, GameStateManager gsm, EntityFactory ef,
			TowerType towerType, OrthographicCamera gameCamera) {
		InputHandler.setGameCamera(gameCamera);
		InputHandler.setEf(ef);
		InputHandler.setGsm(gsm);
		InputHandler.setPlacementMode(isPlacementMode);
		InputHandler.setTowerType(towerType);
	}

	public static OrthographicCamera getGameCamera() {
		return _gameCamera;
	}

	public static void setGameCamera(OrthographicCamera gameCamera) {
		_gameCamera = gameCamera;
	}

}
