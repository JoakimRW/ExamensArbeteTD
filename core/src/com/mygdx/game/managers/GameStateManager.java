package com.mygdx.game.managers;

import java.util.Stack;

import com.badlogic.gdx.Application;
import com.mygdx.game.states.GameState;

public class GameStateManager {
	
	// Application Reference
	private final Application app;
	
	private Stack<GameState> states;
	
	private enum State{
		SPLASH,
		MAINMENU
	}
	
	public GameStateManager(final Application app){
		this.app = app;
		this.states = new Stack<GameState>();
		this.setState(State.SPLASH);
	}
	
	public Application application(){
		return app;
	}
	
	public void update(float delta){
		states.peek().update(delta);
	}
	public void render(){
		states.peek().render();
	}
	public void dispose(){
		for(GameState gs : states){
			gs.dispose();
		}
	}
	
	public void resize(){
		states.peek().resize(w,h);
	}
	public void setState(State state){
		states.pop().dispose();
		states.push(getState(state));
	}
	private GameState getState(State state){
		return null;
	}

}
