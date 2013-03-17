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

import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.game.SpriteContainer;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;

public class ColdWarGame extends State implements MiniGame,CollisionListener{
	private SnowUnitSpriteContainer con,plOneCon,plTwoCon;
	private int h,w;
	private TextButton upsl, upsn, att, place;
	private SnowUnitSprite iceball,ice;
	private Sprite back,backfade,lGrid,rGrid;
	private boolean menu,placing;
	private Image icb = new Image(R.drawable.ball);
	private Image bg = new Image(R.drawable.winter);
	private Image bgfade = new Image(R.drawable.winterfade);
	private Image gridA = new Image(R.drawable.wintergrida);
	private Image gridB = new Image(R.drawable.wintergridb);
	private float[] scaling;
	private long eventTime;
	
	public ColdWarGame(){
		h = Constants.WINDOW_HEIGHT;
		w = Constants.WINDOW_WIDTH;
		
        scaling = new float[]{w / bg.getWidth(), h / bg.getHeight()};
		con = new SnowUnitSpriteContainer();
		plOneCon = new SnowUnitSpriteContainer();
		plTwoCon = new SnowUnitSpriteContainer();
		
		back = new Sprite(bg);
		back.setPosition(w, h);
		back.setScale(scaling[0], scaling[1]);
		addToContainer(back, con);

		backfade = new Sprite(bgfade);
		backfade.setPosition(w,h);
		backfade.setScale(scaling[0], scaling[1]);
		
		lGrid = new Sprite(gridA);
		rGrid = new Sprite(gridB);
		lGrid.setPosition(510*scaling[0], h);
		rGrid.setPosition(1300*scaling[0], h);
		rGrid.setScale(scaling[0], scaling[1]);
		lGrid.setScale(scaling[0], scaling[1]);
		
		ice = new SnowUnitSprite(icb);
		ice.setPosition(w - 30,h - 10);
		addToContainer(ice, con);
		
		iceball = new SnowUnitSprite(icb);
		iceball.setPosition(30, h - 10);
		iceball.addCollisionListener(this);
		ice.addCollisionListener(this);
		addToContainer(iceball, con);
		menu = true;
		placing = false;
		initMenu();
	}
	@Override
    public void update(float dt) {
		if(!menu && !placing){
			ArrayList<Sprite> sprites = con.getSprites();
			for(Sprite s:sprites){
				s.update(dt);
			}
			
			
			
		}
		else{
			backfade.update(dt);
			if(placing){
				rGrid.update(dt);
				lGrid.update(dt);
			}
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
    			
    		}
    		else{
    			drawMenu(canvas);
    			if(placing){
    				backfade.draw(canvas);
    				rGrid.draw(canvas);
    				lGrid.draw(canvas);
    			}
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
		if(rGrid.getBoundingBox().contains(event.getX(), event.getY()) && placing){
			placing = false;
			menu = false;
			eventTime = time;
		}if(lGrid.getBoundingBox().contains(event.getX(), event.getY()) && placing){
			placing = false;
			menu = false;
			eventTime = time;
		}
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
				placing = true;
				Log.d("Scale", "grids:"+lGrid.getPosition().getX()+", "+lGrid.getPosition().getY());
				Log.d("Scale", "grids:"+rGrid.getPosition().getX()+", "+rGrid.getPosition().getY());
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
		upsl.draw(c);
		upsn.draw(c);
		place.draw(c);
		att.draw(c);
	}
	private void drawAttackMenu(){
		
	}
	@Override
	public void collided(Sprite a, Sprite b) {
		Log.d("Collision!", ""+a.toString()+" collided with "+b.toString());
		if(a == iceball || b == iceball){
			if(a == ice || b == ice){
				ice.die();
				iceball.die();
				
			}
		}
	}
}
