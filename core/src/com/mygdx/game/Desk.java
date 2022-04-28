package com.mygdx.game;

public class Desk {

    private Pos[][] DeskBody;
    private int SizeDesk=0;

    public  Desk(){}

    public int getState(int XPos, int YPos)
        {
        return this.DeskBody[XPos][YPos].getState();
        }

    public int getDirection(int XPos, int YPos)
        {
        return this.DeskBody[XPos][YPos].getDirection();
        }

    public void setSizeDesk(int sizeDesk) {
        this.SizeDesk=sizeDesk;
    }

    public int getSizeDesk() { return SizeDesk;}

    public void initDesk() {
        DeskBody = new Pos[SizeDesk][SizeDesk];
        for (int i = 0; i < SizeDesk; i++)
            for (int j = 0; j < SizeDesk; j++) {
                DeskBody[i][j]=new Pos(i,j,0,true,0);
            }
    }

    public void updateDesk(int x, int y, int state,boolean changed,int direction) {
        DeskBody[x][y].Set(x,y,state,changed,direction);
    }


    public void clearPos(Pos pos) {
        pos.clearState();
    }

    public Pos setPos(int x, int y, int state, boolean changed, int direction) {
        DeskBody[x][y].setState(state,direction);
        return DeskBody[x][y];
    }


}
