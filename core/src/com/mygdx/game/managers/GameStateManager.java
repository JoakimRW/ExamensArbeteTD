package com.mygdx.game.managers;

import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.LoseState;
import com.mygdx.game.states.MainMenuState;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.states.SettingsState;
import com.mygdx.game.states.SplashState;
import com.mygdx.game.states.TutorialState;
import com.mygdx.game.states.WinState;

public class GameStateManager {

	// Application Reference
	private final Game game;
	private List<OrthographicCamera> _cameraList;
	private Stack<GameState> states;

	public enum State {
		SPLASH, PLAY, MAINMENU, WIN , LOSE , TUTORIAL, SETTINGS

	}

	public GameStateManager(Game game) {
		this.game = game;
		this.states = new Stack<GameState>();
		this.setState(State.MAINMENU);
	}

	public Game game() {
		return game;
	}

	public void update(float delta) {
		states.peek().update(delta);
	}

	public void render() {
		states.peek().render();
	}

	public void pause() {
		states.peek().pause();
	}

	public void resume() {
		states.peek().resume();
	}

	public void dispose() {
		for (GameState gs : states) {
			gs.dispose();
		}
	}

	public void resize(int w, int h) {
		states.peek().resize(w, h);
	}

	public void setState(State state) {
		if (states.size() >= 1) {
			states.pop().dispose();
		}
		states.push(getState(state));
	}

	private GameState getState(State state) {
		switch (state) {
		case PLAY:
			return new PlayState(this);
		case SPLASH:
			return new SplashState(this);
		case MAINMENU:
			return new MainMenuState(this);
		case LOSE:
			return new LoseState(this);
		case WIN:
			return new WinState(this);
		case TUTORIAL: 
			return new TutorialState(this);
		case SETTINGS: 
			return new SettingsState(this);
		default:
			break;
		}
		return null;
	}

	public List<OrthographicCamera> get_cameraList() {
		return _cameraList;
	}

	public void removeCamera(OrthographicCamera camera) {
		_cameraList.remove(camera);
	}

}
