package com.mygdx.game.client.dto;

import com.mygdx.game.InputState;


public class InputStateImpl implements InputState {

    public InputStateImpl(){
        setType("state");
        setLeftPressed(false);
        setRightPressed(false);
        setUpPressed(false);
        setDownPressed(false);
        setStateKey(0);//{none,leftPressed,rightPressed,upPressed,downPressed}
    }

    public InputStateImpl(boolean leftPressed, boolean rightPressed, boolean upPressed, boolean downPressed,int stateKey) {
        this();
        setLeftPressed(leftPressed);
        setRightPressed(rightPressed);
        setUpPressed(upPressed);
        setDownPressed(downPressed);
        setStateKey(stateKey);
    }

    public native String getType() /*-{
        return this.type;
    }-*/;

    public native void setType(String type) /*-{
        this.type = type;
    }-*/;

    @Override
    public native int getStateKey() /*-{
        return this.stateKey;
    }-*/;

    @Override
    public native void setStateKey(int stateKey) /*-{
        this.stateKey = stateKey;
    }-*/;


    @Override
    public native boolean isLeftPressed() /*-{
        return this.leftPressed;
    }-*/;

    @Override
    public native void setLeftPressed(boolean leftPressed) /*-{
        this.leftPressed = leftPressed;
    }-*/;

    @Override
    public native boolean isRightPressed() /*-{
        return this.rightPressed;
    }-*/;


    @Override
    public native void setRightPressed(boolean rightPressed) /*-{
        this.rightPressed = rightPressed;
    }-*/;

    @Override
    public native boolean isUpPressed() /*-{
        return this.upPressed;
    }-*/;

    @Override
    public native void setUpPressed(boolean upPressed) /*-{
        this.upPressed = upPressed;
    }-*/;


    @Override
    public native boolean isDownPressed() /*-{
        return this.downPressed;
    }-*/;

    @Override
    public native void setDownPressed(boolean downPressed) /*-{
        this.downPressed = downPressed;
    }-*/;

}
