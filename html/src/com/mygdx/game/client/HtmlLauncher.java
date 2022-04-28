package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.user.client.Timer;
import com.mygdx.game.MessageSender;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.client.dto.InputStateImpl;
import com.mygdx.game.client.ws.EventListenerCallback;
import com.mygdx.game.client.ws.MessageProcessor;
import com.mygdx.game.client.ws.WebSocket;
import com.mygdx.game.client.ws.WsEvent;


public class HtmlLauncher extends GwtApplication {
        private MessageProcessor messageProcessor;


    @Override
        public GwtApplicationConfiguration getConfig () {
               return new GwtApplicationConfiguration(720, 720);
        }

    private native WebSocket getWebSocket(String url)
                /*-{
                        return new WebSocket(url);
                }-*/
    ;

    private native void log(Object obj)
                /*-{
                        console.log(obj);
                }-*/
    ;
    private native String toJson(Object obj)
                /*-{
                        return JSON.stringify(obj)
                }-*/
    ;

        @Override
        public ApplicationListener createApplicationListener () {
            final WebSocket client= getWebSocket("ws:89.253.218.18:8888/ws");
            final MyGdxGame myGdxGame=new MyGdxGame(new InputStateImpl());
            final MessageProcessor messageProcessor=new MessageProcessor(myGdxGame);

            myGdxGame.setMessageSender(new MessageSender() {
                @Override
                public void sendMessage(Object message) {
                    client.send(HtmlLauncher.this.toJson(message));
                }
            });
              

            final Timer timer =new Timer(){
                @Override
                           public void run(){
                    myGdxGame.handleTimer();
                }
            };


            EventListenerCallback callback =new EventListenerCallback() {
                @Override
                public void callEvent(WsEvent event) {
                messageProcessor.processEvent(event);
                }
            };

                client.addEventListener("open", new EventListenerCallback() {
                    @Override
                    public void callEvent(WsEvent event) {
                        messageProcessor.processEvent(event);
                        timer.scheduleRepeating(200);
                    }
                });
                client.addEventListener("close",callback);
                client.addEventListener("error",callback);
                client.addEventListener("message",callback);

                return myGdxGame;
        }
}
