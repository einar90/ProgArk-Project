
package com.example.arcade.coldWarII;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.example.arcade.GraphicsHelper;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import com.example.arcade.R;
import com.example.arcade.utilities.Constants;

import sheep.collision.CollisionListener;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.SpriteContainer;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;

public class ColdWarGame extends State implements MiniGame{
	private static Resources res = Game.getInstance().getResources();
	private SnowUnitSpriteContainer con,plOneCon,plTwoCon;
	private int h,w;
	private TextButton upsl, upsn, att, place;
	private SnowUnitSprite iceball,ice;
	private Sprite back,backfade,lGrid,rGrid;
	private ColdWarModel model;
	private Image icb = GraphicsHelper.getScaledImage(res, R.drawable.ball);
	private Image bg = GraphicsHelper.getScaledImage(res, R.drawable.winter2);
	private Image bgfade = GraphicsHelper.getScaledImage(res, R.drawable.winterfade2);
	private Image gridA = new Image(R.drawable.wintergrida);
	private Image gridB = new Image(R.drawable.wintergridb);
	private float[] scaling;
	private long eventTime;
	
	public ColdWarGame(){
		h = Constants.WINDOW_HEIGHT;
		w = Constants.WINDOW_WIDTH;
		
        scaling = new float[]{w / 1600f, h / 960f};
		con = new SnowUnitSpriteContainer();
		plOneCon = new SnowUnitSpriteContainer();
		plTwoCon = new SnowUnitSpriteContainer();
		model = new ColdWarModel(plOneCon,plTwoCon);
		
		back = new Sprite(bg);
		back.setPosition(w/2, h/2);
		addToContainer(back, con);

		backfade = new Sprite(bgfade);
		backfade.setPosition(w/2,h/2);
		
		lGrid = new Sprite(gridA);
		rGrid = new Sprite(gridB);
		rGrid.setScale(scaling[0], scaling[1]);
		lGrid.setScale(scaling[0], scaling[1]);
		lGrid.setPosition(490*scaling[0], h);
		rGrid.setPosition(1280*scaling[0], h);
		
		ice = new SnowUnitSprite(icb);
		ice.setPosition(w - 30,h - 10);
		addToContainer(ice, con);
		
		iceball = new SnowUnitSprite(icb);
		iceball.setPosition(30, h - 10);
		iceball.addCollisionListener(model);
		ice.addCollisionListener(model);
		addToContainer(iceball, con);
		initMenu();
		Log.d("Size", "Size: "+w+", "+h+". Scale: "+scaling[0]+", "+scaling[1]);
	}
	@Override
    public void update(float dt) {
		if(!model.isMenu() && !model.isPlacing()){
			ArrayList<Sprite> sprites = con.getSprites();
			for(Sprite s:sprites){
				s.update(dt);
			}
			
			
			
		}
		else{
			backfade.update(dt);
			if(model.isPlacing()){
				if(model.isPlayerOne()){
					rGrid.update(dt);					
				}
				else{
					rGrid.update(dt);					
				}
			}
		}
    }

    @Override
    public void draw(Canvas canvas) {
    	if(canvas != null){
    		if(!model.isMenu()){
    			ArrayList<Sprite> sprites = con.getSprites();
    			for(Sprite s:sprites){
    				s.draw(canvas);
    			}
    			
    		}
    		else{
    			drawMenu(canvas);
    			if(model.isPlacing()){
    				backfade.draw(canvas);
    				if(model.isPlayerOne()){
    					rGrid.draw(canvas);					
    				}
    				else{
    					rGrid.draw(canvas);					
    				}
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
		checkPlacingBox(event, time);
		if(!model.isMenu() && (time - eventTime) > 500){
			iceball.setSpeed(200, -250);
			iceball.setAcceleration(0, (float) 118.1);
			ice.setSpeed(-200, -250);
			ice.setAcceleration(0, (float) 118.1);	
			model.reverseMenu();
			eventTime = time;
		}else{
			checkMenu(event, time);
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
	private void checkMenu(MotionEvent e,long time){
		if(upsn.getBoundingBox().contains(e.getX(), e.getY())){
			model.reverseMenu();
			eventTime = time;
		}
		if(place.getBoundingBox().contains(e.getX(), e.getY())){
			model.reversePlacing();
			Log.d("Scale", "grids:"+lGrid.getPosition().getX()+", "+lGrid.getPosition().getY());
			Log.d("Scale", "grids:"+rGrid.getPosition().getX()+", "+rGrid.getPosition().getY());
			eventTime = time;
		}
		if(att.getBoundingBox().contains(e.getX(), e.getY())){
			model.reverseMenu();
			eventTime = time;
		}
		if(upsl.getBoundingBox().contains(e.getX(), e.getY())){
			model.reverseMenu();
			eventTime = time;
		}
	}
	private void checkPlacingBox(MotionEvent e,long time){
		if(rGrid.getBoundingBox().contains(e.getX(), e.getY()) && model.isPlacing()){
			model.reversePlacing();
			model.reverseMenu();
			eventTime = time;
		}if(lGrid.getBoundingBox().contains(e.getX(), e.getY()) && model.isPlacing()){
			model.reversePlacing();
			model.reverseMenu();
			eventTime = time;
		}
	}
}
