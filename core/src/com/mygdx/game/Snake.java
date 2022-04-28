package com.mygdx.game;

import java.util.ArrayDeque;

public class Snake{
    private String Id;
    private String type;
    private  String name;
    private int score;
    private final ArrayDeque<Pos> Bodysnake;//тело змея

    public Snake()
        {
        setType("name");
        setName("user");
        score=0;
        Bodysnake = new ArrayDeque<>();
        }

    public Snake(String id,String name)
    {   this.name=name;
        this.Id=id;
        Bodysnake = new ArrayDeque<>();
        score=0;

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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getSize() {
        return Bodysnake.size();
    }
}
