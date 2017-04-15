package com.mygdx.game.entites.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class InputHandler implements InputProcessor{
	private InputHandlerIF _inputhandler;
	private Vector2 camDir = new Vector2();
	
	public void registerInputHandlerSystem(InputHandlerIF inputHandler){
		_inputhandler = inputHandler; 
	}
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode ==  Input.Keys.W){
			_inputhandler.moveCam(camDir.set( camDir.y , 1));
		}
		else if(keycode ==  Input.Keys.S){
			_inputhandler.moveCam(camDir.set( camDir.y , -1));
		}else {
			_inputhandler.moveCam(camDir.set( camDir.y , 0));
		}
		
		if(keycode ==  Input.Keys.D){
			_inputhandler.moveCam(camDir.set( 1 , camDir.y));
		}
		else if(keycode ==  Input.Keys.A){
			_inputhandler.moveCam(camDir.set( -1 , camDir.y));
		}else {
			_inputhandler.moveCam(camDir.set( 0 , camDir.y));
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
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

}
