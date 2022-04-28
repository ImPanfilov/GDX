package com.mygdx.game;

public interface InputState {

    boolean isLeftPressed();

    void setLeftPressed(boolean leftPressed);

    boolean isRightPressed();

    void setRightPressed(boolean rightPressed);

    boolean isUpPressed();

    void setUpPressed(boolean upPressed);

    boolean isDownPressed();

    void setDownPressed(boolean downPressed);

    int getStateKey();

    void setStateKey(int stateKey);
}
