package com.mygdx.game.client.ws;

import com.google.gwt.json.client.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Snake;


public class MessageProcessor {
    private final MyGdxGame myGdxGame;

    public MessageProcessor(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    public void processEvent(WsEvent event) {
        String data=event.getData();
        if(data!=null){
            JSONValue parsed= JSONParser.parseStrict(data);
            JSONArray array=parsed.isArray();
            JSONObject object=parsed.isObject();

            if(array !=null){
                processArray(array);
            }else if (object !=null){
                processObject(object);
            }
                
        }
    }

    private void processObject(JSONObject object) {
        int x ;
        int y ;int i;
        int state;int direction;
        boolean changed;Snake tmpSnake;
        JSONValue type=object.get("class");
        if (type!=null){
            switch (type.isString().stringValue()){

                case "sessionKey":
                    String meId=object.get("id").isString().stringValue();
                    myGdxGame.setMeId(meId);
                    int size= (int) object.get("size").isNumber().doubleValue();
                    myGdxGame.setSizeDesk(size);
                    myGdxGame.initDesk();
                    myGdxGame.snakes.put(meId,new Snake());
                    break;

                case "evict":
                    String idToEvict=object.get("id").isString().stringValue();
                    myGdxGame.evict(idToEvict);
                    break;

                case "snake":
                    String Id=object.get("id").isString().stringValue();
                    String name=object.get("name").isString().stringValue();
                    int score=(int)object.get("score").isNumber().doubleValue();

                    if (myGdxGame.snakes.containsKey(Id)){tmpSnake=myGdxGame.snakes.get(Id);
                        myGdxGame.clearSnake(tmpSnake);}
                    else{
                        tmpSnake=new Snake(Id,name);
                        myGdxGame.snakes.put(Id,tmpSnake);
                        }
                    tmpSnake.setScore(score);
                    tmpSnake.setName(name);
                    i =object.get("pos").isArray().size();
                    for (int j = 0; j < i; j++) {
                        JSONValue qwe=object.get("pos").isArray().get(j);
                        x=(int)qwe.isObject().get("x").isNumber().doubleValue();
                        y=(int)qwe.isObject().get("y").isNumber().doubleValue();
                        state = (int) qwe.isObject().get("state").isNumber().doubleValue();
                        changed = qwe.isObject().get("changed").isBoolean().booleanValue();
                        direction = (int) qwe.isObject().get("direction").isNumber().doubleValue();
                        myGdxGame.updateSnake(Id,x, y, state,changed,direction);
                    }
                    myGdxGame.setStateTime(0f);
                    break;

                case "food":
                    i =object.get("pos").isArray().size();
                    for (int j = 0; j < i; j++) {
                        JSONValue qwe=object.get("pos").isArray().get(j);
                        x=(int)qwe.isObject().get("x").isNumber().doubleValue();
                        y=(int)qwe.isObject().get("y").isNumber().doubleValue();
                        state = (int) qwe.isObject().get("state").isNumber().doubleValue();
                        changed = qwe.isObject().get("changed").isBoolean().booleanValue();
                        direction = (int) qwe.isObject().get("direction").isNumber().doubleValue();
                        myGdxGame.updateDesk(x, y, state,changed,direction);
                    }myGdxGame.setUpScene();
                    break;

                case "best":
                    int id=(int)object.get("id").isNumber().doubleValue();
                    String nameBest=object.get("name").isString().stringValue();
                    int scoreBest=(int)object.get("score").isNumber().doubleValue();
                    nameBest="\n"+nameBest+" : "+scoreBest;
                    myGdxGame.setBestScore(id,nameBest);
                    break;
                default:
                    throw  new RuntimeException("Unknown Message"+type);
            }
        }
    }

    private void processArray(JSONArray array) {
        for (int i=0;i<array.size();i++){
            JSONValue jsonValue=array.get(i);
            JSONObject object= jsonValue.isObject();
            if (object!=null){
                processObject(object);
            }
        }
    }
}
