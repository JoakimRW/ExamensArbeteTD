package com.mygdx.game.managers;

import java.util.Stack;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Game;
import com.mygdx.game.states.GameState;

public class GameStateManager {
	
	// Application Reference
	private final Game game;
	
	private Stack<GameState> states;
	
	private enum State{
		SPLASH,
		MAINMENU
	}
	
	public GameStateManager(Game game){
		this.game = game;
		this.states = new Stack<GameState>();
		this.setState(State.SPLASH);
	}
	
	public Game game(){
		return game;
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
	
	public void resize(int w , int h){
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
