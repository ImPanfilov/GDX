package com.mygdx.game;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pos {
    int xPos;
    int yPos;
    int State;
    boolean Changed;
    int Direction;//0-none,1-left,2-right,3-up,4-down

    public void Set(int xpos, int ypos,int state,boolean changed,int direction) {
        xPos = xpos;
        yPos = ypos;
        State=state;
        Changed=changed;
        Direction=direction;
    }

    public void clearState() {
        this.State=0;
        this.Direction=0;
    }

    public void setState(int state, int direction) {
        this.State=state;
        this.Direction=direction;
    }
}