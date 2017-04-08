package com.mygdx.game.managers;

import java.util.List;
import java.util.Stack;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.MainMenuState;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.states.SplashState;

public class GameStateManager {

	// Application Reference
	private final Game game;
	Engine ashleyEngine;
	private List<OrthographicCamera> _cameraList;

	private Stack<GameState> states;

	public enum State {
		SPLASH, PLAY, MAINMENU

	}

	public GameStateManager(Game game) {
		this.game = game;
		this.states = new Stack<GameState>();
		this.setState(State.MAINMENU);
		ashleyEngine = new Engine();
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
			return new PlayState(this, ashleyEngine);
		case SPLASH:
			return new SplashState(this);
		case MAINMENU:
			return new MainMenuState(this);
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
