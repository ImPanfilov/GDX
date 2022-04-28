package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.*;
import java.lang.String;


public class MyGdxGame extends ApplicationAdapter {
	private static final int FRAME_COLS = 8;
	private static final int FRAME_ROWS = 9;
	private Animation<TextureRegion>[] walkAnimation;
	private Texture walkSheet;
	private static Music music;
	private SpriteBatch spriteBatch;
	private TextureRegion[] currentFrame;
	private Texture imgBack;
	private BitmapFont font;

	private float stateTime;
	private  String meId;
	private int viewSize=15;
	public ObjectMap<String,Snake> snakes=new ObjectMap<>();
	private final Keyboard inputProcessor;
	private  static Desk desk=new Desk();
	private static Desk renderBody=new Desk();
	private MessageSender messageSender;
	private Snake me=new Snake();
	private Array<String> bestScore=new Array<>(5);

	public MyGdxGame(InputState inputState) {
		this.inputProcessor = new Keyboard(inputState);
	}

	public static void play() {
		music.play();
	}

	@Override
	public void create () {
 		font = new BitmapFont(
				Gdx.files.internal("font.fnt"),
				Gdx.files.internal("font.png"), false);
				font.setColor(Color.WHITE);
		walkSheet = new Texture(Gdx.files.internal("Heroes.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("ost.mp3"));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
		walkAnimation= new Animation[FRAME_ROWS];
		currentFrame=new TextureRegion[FRAME_ROWS];
		for (int i = 0; i < FRAME_ROWS; i++) {
			walkAnimation[i]= new Animation(0.1f,(Object[]) tmp[i]);
		}
		renderBody.setSizeDesk(viewSize);renderBody.initDesk();
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
		Gdx.input.setInputProcessor(inputProcessor);
		imgBack = new Texture("0.png");
		snakes.put(meId,me);
		MyTextInputListener listener = new MyTextInputListener();
		Gdx.input.getTextInput(listener, "Input Name", "Name", "Hint Value");
		music.setVolume(0.5f);
		music.setLooping(true);

		}

	@Override
	public void render () {
		ScreenUtils.clear(100, 100, 100, 1);
		if ((music.isPlaying())&&(music.getPosition()>=72))music.setPosition(4);
		stateTime += Gdx.graphics.getDeltaTime();
		for (int j = 0; j < FRAME_ROWS; j++) {currentFrame[j]= walkAnimation[j].getKeyFrame(stateTime, true);}
		spriteBatch.begin();
		for (int i = 0; i < viewSize; i++)
			for (int j = 0; j < viewSize; j++) {spriteBatch.draw(imgBack,i * 48,j * 48);}
		for (int i = 0; i < viewSize; i++)
			for (int j = 0; j < viewSize; j++) {
				switch (renderBody.getState(i,j) ) {
					case 1:
						switch(renderBody.getDirection(i,j)){//0-none,1-left,2-right,3-up,4-down,13-leftToUp,14-leftToDown,23-rightToUp,24-rightToDown,31-upToLeft,32-upToRight,41-downToLeft,42downToRight
							case 0:spriteBatch.draw(currentFrame[2],-24 + i * 48,-2 + j * 48);break;
							case 1:spriteBatch.draw(currentFrame[1],-24 + i * 48,-2 + j * 48);break;
							case 2:spriteBatch.draw(currentFrame[0],-24 + i * 48,-2 + j * 48);break;
							case 3:spriteBatch.draw(currentFrame[3],-24 + i * 48,-2 + j * 48);break;
							case 4:spriteBatch.draw(currentFrame[2],-24 + i * 48,-2 + j * 48);break;
							case 13:case 31:spriteBatch.draw(currentFrame[8],-24 + i * 48,-2 + j * 48);break;
							case 14:case 41:spriteBatch.draw(currentFrame[6],-24 + i * 48,-2 + j * 48);break;
							case 23:case 32:spriteBatch.draw(currentFrame[7],-24 + i * 48,-2 + j * 48);break;
							case 24:case 42:spriteBatch.draw(currentFrame[5],-24 + i * 48,-2 + j * 48);break;
							default:break;
						}
						break;
					case 2:
						spriteBatch.draw(currentFrame[4],-24 + i * 48,-6 + j * 48);
						break;
					default:
						break;
				}
			}
		if (snakes.size>0){drawScore();}
		spriteBatch.end();
	}

	public void drawScore(){
		String score="Current score:\n";
		for(ObjectMap.Entry<String,Snake> snakeEntry:snakes)
			score = score + snakeEntry.value.getName() + " : " + snakeEntry.value.getScore() + "\n";
		String scoreBest="\n\n\nBest score:";
		for(String nameBest:bestScore){scoreBest=(scoreBest+nameBest);}
		font.draw(spriteBatch,score+scoreBest, 48*viewSize-120,  48*viewSize,120,Align.left,false);
	}


	@Override
	public void dispose () {
		spriteBatch.dispose();
		imgBack.dispose();
		font.dispose();
		music.dispose();
	}

	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void handleTimer() {
		if (inputProcessor!=null){
			InputState playerState= inputProcessor.updateAndGetInputState();
			messageSender.sendMessage(playerState);
		}
	}

	public void setMeId(String meId) {
		this.meId=meId;
	}

	public void evict(String idToEvict) {
		snakes.get(idToEvict).clear(desk);
		snakes.remove(idToEvict);
	}

	public void clearSnake(Snake tmpSnake) {
		tmpSnake.clear(desk);
	}

	public void updateSnake(String id, int x, int y, int state, boolean changed, int direction) {
		Pos tmpPos=desk.setPos(x,y, state, changed, direction);
		snakes.get(id).updateSnake(tmpPos);
	}

	public void setUpScene(){
		if ((desk.getSizeDesk()>0)&&(snakes.get(meId).getSize()>0)) {
			for (int i = 0; i < viewSize; i++)
				for (int j = 0; j < viewSize; j++) {
					int x=Check(snakes.get(meId).getHead().getXpos() - 7+ i);
					int y=Check(snakes.get(meId).getHead().getYpos() - 7+ j);
					renderBody.updateDesk(i, j, desk.getState(x,y), true, desk.getDirection(x,y));
				}
		}
	}

	public int Check(int X) {
		X=(X <0) ? (X+desk.getSizeDesk()) : X;
		X=(X>(desk.getSizeDesk()-1)) ? (X-desk.getSizeDesk()) : X;
		return X;
	}

	public void setBestScore(int id, String string) {
		if(bestScore.size<=id)bestScore.add(string);
		bestScore.set(id,string);
	}

	public void setStateTime(float v) {
		stateTime=v;
	}

	public void setSizeDesk(int size) {
		desk.setSizeDesk(size);
	}

	public void initDesk() {
		desk.initDesk();
	}

	public void updateDesk(int x, int y, int state, boolean changed, int direction) {
		desk.updateDesk(x, y, state,changed,direction);
	}


	public class MyTextInputListener implements Input.TextInputListener {
		@Override
		public void input (String text) {
			if (text=="")text="Undefined";
			me.setName(text);me.setType("name");
			messageSender.sendMessage(me);
		}

		@Override
		public void canceled () {
		}
	}


}