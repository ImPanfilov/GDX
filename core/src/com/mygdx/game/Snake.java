package com.mygdx.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Snake{
    String Id;
    String type="name";
    String name="user";
    int Score=0;
    final ArrayDeque<Pos> Bodysnake= new ArrayDeque<>();//тело змея


    public Snake(String id,String name)
    {   this.name=name;
        this.Id=id;
    }

    public native String getName() /*-{
        return this.name;
    }-*/;

    public native void setName(String name) /*-{
        this.name = name;
    }-*/;


    public native String getType() /*-{
        return this.type;
    }-*/;

    public native void setType(String type) /*-{
        this.type = type;
    }-*/;


    public void clear(Desk desk) {
        for(Pos pos : Bodysnake){
            desk.clearPos(pos);
        }
        Bodysnake.clear();
    }

    public void updateSnake(Pos pos) {
        Bodysnake.add(pos);
    }

    public Pos getHead() {
        return Bodysnake.peekLast();
    }


     public int getSize() {
        return Bodysnake.size();
    }
}