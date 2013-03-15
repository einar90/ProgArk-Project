package com.example.arcade.coldWarII;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import com.example.arcade.R;
import com.example.arcade.utilities.Constants;

import sheep.game.Sprite;
import sheep.game.SpriteContainer;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;

public class ColdWarGame extends State implements MiniGame{
	private SnowUnitSpriteContainer con,plOneCon,plTwoCon;
	private int h,w;
	private TextButton upsl, upsn, att, place;
	private SnowUnitSprite iceball,ice;
	private Sprite back,backfade;
	private boolean menu;
	private Image icb = new Image(R.drawable.ball);
	private Image bg = new Image(R.drawable.winter);
	private Image bgfade = new Image(R.drawable.winterfade);
	private float[] scaling;
	private long eventTime;
	
	public ColdWarGame(){
		h = Constants.WINDOW_HEIGHT;
		w = Constants.WINDOW_WIDTH;
		
        scaling = new float[]{w / bg.getWidth(), h / bg.getHeight()};
		con = new SnowUnitSpriteContainer();
		plOneCon = new SnowUnitSpriteContainer();
		plTwoCon = new SnowUnitSpriteContainer();
		
		Log.d("ColdWarGame", "Start ColdWar, scale: "+scaling[0]+", "+scaling[1]+". Size: "+w+", "+h);

		back = new Sprite(bg);
		backfade = new Sprite(bgfade);
		back.setPosition(w, h);
		back.setScale(scaling[0], scaling[1]);
		backfade.setPosition(w,h);
		backfade.setScale(scaling[0], scaling[1]);
		addToContainer(back, con);
		
		ice = new SnowUnitSprite(icb);
		ice.setPosition(w - 30,h - 10);
		addToContainer(ice, con);
		
		iceball = new SnowUnitSprite(icb);
		iceball.setPosition(30, h - 10);
		addToContainer(iceball, con);
		menu = true;
		initMenu();
	}
	@Override
    public void update(float dt) {
		if(!menu){
			ArrayList<Sprite> sprites = con.getSprites();
			for(Sprite s:sprites){
				s.update(dt);
			}
			
			if(ice.collides(iceball) || iceball.collides(ice)){
				Log.d("Collision!", "ice collided with iceball");
				ice.die();
				iceball.die();
			}
			
		}
		else{
			backfade.update(dt);
		}
    }

    @Override
    public void draw(Canvas canvas) {
    	if(canvas != null){
    		if(!menu){
    			ArrayList<Sprite> sprites = con.getSprites();
    			for(Sprite s:sprites){
    				s.draw(canvas);
    			}
    			Log.d("Scale", "BACKGROUND scale:"+back.getPosition().getX()+", "+back.getPosition().getY());
    			
    		}
    		else{
    			drawMenu(canvas);
    		}
    	}
    }
    private void addToContainer(Sprite s,SpriteContainer con){
    	con.addSprite(s);
    	s.setParent(con);
    }
	@Override
	public boolean onTouchDown(MotionEvent event) {
		long time = event.getEventTime();
		
		if(!menu && (time - eventTime) > 500){
			iceball.setSpeed(200, -250);
			iceball.setAcceleration(0, (float) 118.1);
			ice.setSpeed(-200, -250);
			ice.setAcceleration(0, (float) 118.1);	
			menu = true;
			eventTime = time;
		}else{
			if(upsn.getBoundingBox().contains(event.getX(), event.getY())){
				menu = false;
				eventTime = time;
			}
			if(place.getBoundingBox().contains(event.getX(), event.getY())){
				menu = false;
				eventTime = time;
			}
			if(att.getBoundingBox().contains(event.getX(), event.getY())){
				menu = false;
				eventTime = time;
			}
			if(upsl.getBoundingBox().contains(event.getX(), event.getY())){
				menu = false;
				eventTime = time;
			}
		}
		return false;
	}
	@Override
	public void launchGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public HighscoreList getHighscoreList() {
		// TODO Auto-generated method stub
		return null;
	}
	private void initMenu(){
		upsl = new TextButton(w / 2, h /2 - 120 , "Upgrade Snow Machine");
		upsn = new TextButton(w / 2, h /2 - 90, "Upgrade Snow Machine");
		place = new TextButton(w / 2, h /2 - 60, "Place Snow");
		att = new TextButton(w / 2, h /2 - 30, "Attack");
	}
	private void drawMenu(Canvas c){
		backfade.draw(c);
		Log.d("Scale", "backfade scale:"+backfade.getPosition().getX()+", "+backfade.getPosition().getY());
		upsl.draw(c);
		upsn.draw(c);
		place.draw(c);
		att.draw(c);
	}
	private void drawAttackMenu(){
		
	}
}
