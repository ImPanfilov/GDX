package com.mygdx.game;

public class Pos
{
    private int Xpos;
    private int Ypos;
    private int State;
    private boolean Changed;
    private int Direction;//0-none,1-left,2-right,3-up,4-down

    public Pos()
    {
        this.Xpos = 0;
        this.Ypos = 0;
        this.State = 0;
        this.Changed = true;
        this.Direction=0;
    }

    public Pos(int xpos, int ypos,int State,boolean changed,int direction)
    {
        this.Xpos = xpos;
        this.Ypos = ypos;
        this.State = State;
        this.Changed = changed;
        this.Direction=direction;

    }
    public void Set(int xpos, int ypos,int State,boolean Changed,int direction)
    {

        this.Xpos = xpos;
        this.Ypos = ypos;
        this.State=State;
        this.Changed=Changed;
        this.Direction=direction;

    }

    public int getDirection() {return this.Direction;}

    public void clearState() {
        this.State=0;
        this.Direction=0;
    }

    public int getState() {
        return this.State;
    }

    public void setState(int state, int direction) {
        this.State=state;
        this.Direction=direction;
    }

    public int getXpos() {
        return Xpos;
    }

    public void setXpos(int xpos) {
        Xpos = xpos;
    }

    public int getYpos() {
        return Ypos;
    }

    public void setYpos(int ypos) {
        Ypos = ypos;
    }

    public void setState(int state) {
        State = state;
    }

    public void setDirection(int direction) {
        Direction = direction;
    }
}
