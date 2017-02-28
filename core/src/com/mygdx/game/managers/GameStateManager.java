package com.mygdx.game.managers;

import java.util.Stack;

import com.mygdx.game.Game;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.SplashState;

public class GameStateManager {

	// Application Reference
	private final Game game;

	private Stack<GameState> states;

	public enum State {
		SPLASH, PLAY

	}

	public GameStateManager(Game game) {
		this.game = game;
		this.states = new Stack<GameState>();
		this.setState(State.SPLASH);
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
			return new PlayState(this);
		case SPLASH:
			return new SplashState(this);
		default:
			break;

		}
		return null;
	}

}
