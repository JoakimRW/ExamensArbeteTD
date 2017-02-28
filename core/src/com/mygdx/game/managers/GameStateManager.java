package com.mygdx.game.managers;

import java.util.Stack;

import com.badlogic.gdx.Application;

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
		
	}

}
