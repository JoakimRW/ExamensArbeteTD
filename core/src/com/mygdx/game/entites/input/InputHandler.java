package com.mygdx.game.entites.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.entites.input.InputHandlerIF;
import com.mygdx.game.states.PlayState;

public class InputHandler implements InputProcessor{
	private InputHandlerIF _inputHandler;
	private int xAxis = 0;
	private int yAxis = 0;

	public void registerInputHandlerSystem(InputHandlerIF inputHandler){
        _inputHandler = inputHandler;
	}
	/** kallas i update **/
	public void pullInput(){
        setCameraDirection();
    }

    public void setCameraDirection(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            yAxis = 1;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            yAxis = -1;
        }else {
            yAxis = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            xAxis = 1;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            xAxis = -1;
        }else {
            xAxis = 0;
        }
        _inputHandler.moveCam(xAxis , yAxis);
    }
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.ESCAPE){
            PlayState.PAUSE = !PlayState.PAUSE;
            System.out.println(PlayState.PAUSE);
        }
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
