package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class Keyboard extends InputAdapter {

    private final InputState inputState;
    private boolean playMusic =false;

    public Keyboard(InputState inputState) {
        this.inputState = inputState;
    }

    @Override
    public boolean keyDown(int keycode) {//{none,leftPressed,rightPressed,upPressed,downPressed}
        if(!playMusic) {MyGdxGame.play();playMusic =true;};
        switch(keycode){
            case Input.Keys.LEFT: inputState.setStateKey(1);break;
            case Input.Keys.RIGHT:inputState.setStateKey(2);break;
            case Input.Keys.UP: inputState.setStateKey(3);break;
            case Input.Keys.DOWN: inputState.setStateKey(4);break;
        }
        return false;
    }


    public InputState updateAndGetInputState() {
        return inputState;
    }
    public InputState getInputState() {
        return inputState;
    }

}
