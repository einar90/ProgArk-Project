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
	private Sprite back;
	private boolean menu;
	private Image icb = new Image(R.drawable.ironball);
	private Image bg = new Image(R.drawable.winter);
	private float[] scaling;
	
	public ColdWarGame(){
		h = Constants.WINDOW_HEIGHT;
		w = Constants.WINDOW_WIDTH;
		menu = true;
        scaling = new float[]{w / bg.getWidth(), h / bg.getHeight()};
		con = new SnowUnitSpriteContainer();
		plOneCon = new SnowUnitSpriteContainer();
		plTwoCon = new SnowUnitSpriteContainer();
		back = new Sprite(bg);
		Log.d("ColdWarGame", "Start ColdWar, scale: "+scaling[0]+", "+scaling[1]+". Size: "+w+", "+h);
		back.setPosition((h * scaling[0]) / 2, (w * scaling[0]) / 2);
		addToContainer(back, con);
		
		ice = new SnowUnitSprite(icb);
		ice.setPosition(w - 30,h - 10);
		addToContainer(ice, con);
		
		iceball = new SnowUnitSprite(icb);
		iceball.setPosition(30, h - 10);
		addToContainer(iceball, con);
	}
	@Override
    public void update(float dt) {
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

    @Override
    public void draw(Canvas canvas) {
    	if(canvas != null){
    		ArrayList<Sprite> sprites = con.getSprites();
    		for(Sprite s:sprites){
    			s.draw(canvas);
    		}
    	}
    }
    private void addToContainer(Sprite s,SpriteContainer con){
    	con.addSprite(s);
    	s.setParent(con);
    }
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(upsn.getBoundingBox().contains(event.getX(), event.getY())){
			
		}
		if(!menu){
			iceball.setSpeed(200, -250);
			iceball.setAcceleration(0, (float) 98.1);
			ice.setSpeed(-200, -250);
			ice.setAcceleration(0, (float) 98.1);			
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
	private void displayMenu(){
		upsn = new TextButton(w / 2, h /2 , "Upgrade Snow Machine");
		place = new TextButton(w / 2, h /2 , "Place Snow");
		
	}
}
