
package com.example.arcade.coldWarII;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.PaintDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.example.arcade.GamesMenu;
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
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;

public class ColdWarGame extends State implements MiniGame{
	private static Resources res = Game.getInstance().getResources();
	private SnowUnitSpriteContainer update,guiobjects,objects,plOneCon,plTwoCon;
	private float h,w;
	private SnowUnitButton upsl, upsn, att, place;
	private SnowUnitButton plSnowball, plMassiveSnowball, plIcecube, plActive;
	private SnowUnitSprite iceball,ice,icecub,icewal,massive;
	private Sprite back,backfade,lGrid,rGrid,plOneKing,plTwoKing;
	private ColdWarModel model;
	private Image icb = GraphicsHelper.getScaledImage(res, R.drawable.ball);
	private Image bg = GraphicsHelper.getScaledImage(res, R.drawable.winter2);
	private Image bgfade = GraphicsHelper.getScaledImage(res, R.drawable.winterfade2);
	private Image gridA = new Image(R.drawable.wintergrida);
	private Image gridB = new Image(R.drawable.wintergridb);
	private Image massiveSnow = new Image(R.drawable.massivesnowball);
	private Image icecube = new Image(R.drawable.icecube);
	private Image icewall = new Image(R.drawable.icewall);
	private Image king1 = new Image(R.drawable.king);
	private Image king2 = new Image(R.drawable.king2);
	private Image king3 = new Image(R.drawable.king3);
	private Image kingDead = new Image(R.drawable.kingdead);
	private float[] scaling,posScale;
	private Paint white,red;
	private ArrayList<SnowUnitButton> placeButtons;
	private float updTime;
	
	public ColdWarGame(){
		h = Constants.WINDOW_HEIGHT;
		w = bg.getWidth();
		updTime = System.currentTimeMillis();
		
		posScale = new float[]{(510f/1600f),(1300f/1600f),(210/1600),(360/960)};
        scaling = new float[]{w / 1600f, h / 960f};
        update = new SnowUnitSpriteContainer();
        guiobjects = new SnowUnitSpriteContainer();
		objects = new SnowUnitSpriteContainer();
		plOneCon = new SnowUnitSpriteContainer();
		plTwoCon = new SnowUnitSpriteContainer();
		model = new ColdWarModel(plOneCon,plTwoCon);
		
		initButtonPaint();
		
		back = new Sprite(bg);
		back.setPosition(w/2, h/2);
		addToContainer(back, guiobjects);

		backfade = new Sprite(bgfade);
		backfade.setPosition(w/2,h/2);
		addToContainer(backfade, guiobjects);
		
		Log.d("Size", "Size: "+w+", "+h+". Scale: "+scaling[0]+", "+scaling[1]);
		Log.d("Size", "SizeImage: "+bg.getWidth()+", "+bg.getHeight()+"pos: "+back.getX()+", "+back.getY());
		Log.d("Size", "PosScale: "+posScale[0]+", "+posScale[1]+"comp: "+back.getScale().getX()+", "+back.getScale().getY());
		
		
		lGrid = new Sprite(gridA);
		rGrid = new Sprite(gridB);
		rGrid.setScale(scaling[0], scaling[1]);
		lGrid.setScale(scaling[0], scaling[1]);
		float lPos = (300+510)/2+((w-bg.getWidth())/2),rPos = (1090+1300)/2+((w-bg.getWidth())/2);
		
		
		lGrid.setPosition(lPos*scaling[0], h);
		rGrid.setPosition(rPos*scaling[1], h);
		addToContainer(rGrid, guiobjects);
		addToContainer(lGrid, guiobjects);
		ice = new SnowUnitSprite(icb,model.getPlayerOne(),SnowUnitType.SNOWBALL);
		ice.setPosition(w - 30,h - 10);
		addToContainer(ice, plOneCon);
		
		plOneKing = new Sprite(king1);
		addToContainer(plOneKing, guiobjects);
		plOneKing.setPosition(50, h);
		plOneKing.setScale(scaling[0], scaling[1]);
		
		plTwoKing = new Sprite(king1);
		addToContainer(plTwoKing, guiobjects);
		plTwoKing.setPosition(w-50, h);
		plTwoKing.setScale(scaling[0], scaling[1]);
		
		iceball = new SnowUnitSprite(icb,model.getPlayerTwo(),SnowUnitType.SNOWBALL);
		iceball.setPosition(30, h - 10);
		iceball.addCollisionListener(model);
		ice.addCollisionListener(model);
		addToContainer(iceball, plTwoCon);
		
		
		
		initGUI();
	}
	private void initGUI(){
		initMenu();
		initPlaceMenu();
	}
	private void initMenu(){
		upsl = new SnowUnitButton(w / 2, h /2 - 200 , "Upgrade Slingshot",white,false);
		upsn = new SnowUnitButton(w / 2, h /2 - 130, "Upgrade Snow Machine",white,false);
		place = new SnowUnitButton(w / 2, h /2 - 60, "Place Snow",white,false);
		att = new SnowUnitButton(w / 2, h /2 + 10, "Attack",white,false);
		addToContainer(upsl, guiobjects);
		addToContainer(upsn, guiobjects);
		addToContainer(place, guiobjects);
		addToContainer(att, guiobjects);
	}
	private void initPlaceMenu(){
		if(model.isPlayerOne()){
			plIcecube = new SnowUnitButton(150, 30 , "Icecube(2 su)",white,false);
			plMassiveSnowball = new SnowUnitButton(150, 170, "Massive Snowball(4 su)",white,false);
			plSnowball = new SnowUnitButton(150, 100, "Snowball(1 su)",white,false);
			addToContainer(plMassiveSnowball, guiobjects);
			addToContainer(plIcecube, guiobjects);
			addToContainer(plSnowball, guiobjects);
			placeButtons = new ArrayList<SnowUnitButton>();
			placeButtons.add(plIcecube);
			placeButtons.add(plSnowball);
			placeButtons.add(plMassiveSnowball);
			
			massive = new SnowUnitSprite(massiveSnow,model.getPlayerOne(),SnowUnitType.MASSIVE);
			massive.setPosition(150, 170 + 50);
			massive.setScale(scaling[0], scaling[1]);
			addToContainer(massive, guiobjects);
			
			
			icecub = new SnowUnitSprite(icecube,model.getPlayerTwo(),SnowUnitType.ICECUBE);
			icecub.setPosition(150+80, 30);
			icecub.setScale(scaling[0], scaling[1]);
			addToContainer(icecub, guiobjects);
		}
		else{
			plIcecube = new SnowUnitButton(w + 100, h /2 - 120 , "Icecube(2 su)",white,false);
			plMassiveSnowball = new SnowUnitButton(w + 100, h /2 - 90, "Massive Snowball(4 su)",white,false);
			plSnowball = new SnowUnitButton(w + 100, h /2 - 60, "Snowball(1 su)",white,false);			
		}
		
	}
	private void initButtonPaint(){
		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
		white = new Paint(Font.WHITE_SANS_BOLD_16);
		white.setTypeface(tf);
		white.setStyle(Style.FILL);
		white.setTextAlign(Align.CENTER);
		white.setTextSize(11);
		red = new Paint(white);
		red.setTextSize(14);
		red.setColor(Color.RED);
		Log.d("ButtonPaint", "pressed: "+red.getColor()+" idle: "+white.getColor());
	}
	@Override
    public void draw(Canvas canvas) {
    	if(canvas != null){
    		canvas.drawPaint(new Paint(Color.BLACK));
    		if(!model.isMenu()){
    			back.draw(canvas);
    			drawSnowUnits(canvas);
    		}
    		else{
    			drawMenu(canvas);
    			if(model.isPlacing()){
    				drawPlace(canvas);
    			}
    		}
    	}
    }
	private void drawSnowUnits(Canvas c) {
		ArrayList<Sprite> s1 = plOneCon.getSprites();
		ArrayList<Sprite> s2 = plTwoCon.getSprites();
		for(Sprite s:s1){
			s.draw(c);
		}
		for(Sprite s:s2){
			s.draw(c);
		}
	}
	private void drawMenu(Canvas c){
		backfade.draw(c);
		upsl.draw(c);
		upsn.draw(c);
		place.draw(c);
		att.draw(c);
	}
	
    private void drawPlace(Canvas canvas){
    	backfade.draw(canvas);
		if(model.isPlayerOne()){
			rGrid.draw(canvas);					
		}
		else{
			rGrid.draw(canvas);					
		}
		for (int i = 0; i < placeButtons.size(); i++) {
			placeButtons.get(i).draw(canvas);
		}
		massive.draw(canvas);
		icecub.draw(canvas);
		plOneKing.draw(canvas);
		plTwoKing.draw(canvas);
    }
	private void drawAttackMenu(){
		
	}
	@Override
    public void update(float dt) {
		updTime += dt;
		ArrayList<Sprite> list = update.getSprites();
		for (int i = 0; i < list.size(); i++) {
			Sprite s = list.get(i);
			if(s == null)
				update(dt);
			else
				s.update(dt);
		}
				
		checkCollision();
    }

	private void addToContainer(Sprite s,SpriteContainer con){
		con.addSprite(s);
		s.setParent(con);
		update.addSprite(s);
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
	private void checkCollision() {
		for(Sprite s : plOneCon.getSprites()){
			for (Sprite s2 : plTwoCon.getSprites()) {
				if(s.collides(s2))
					Log.d("COllision", "checkCollision()");
			}
		}
	}
	
	
	private void checkMenu(MotionEvent e){
		if(GraphicsHelper.isSpriteTouched(upsn, upsn.getImageWidth(), upsn.getImageHeight(), e)){
			model.reverseMenu();
			Log.d("Ball", "started moving");
			iceball.setSpeed(200, -250);
			iceball.setAcceleration(0, (float) 118.1);
			ice.setSpeed(-200, -250);
			ice.setAcceleration(0, (float) 118.1);
		}
		if(GraphicsHelper.isSpriteTouched(place, place.getImageWidth(), place.getImageHeight(), e)){
			model.reversePlacing();
			Log.d("Scale", "grids:"+lGrid.getPosition().getX()+", "+lGrid.getPosition().getY());
			Log.d("Scale", "grids:"+rGrid.getPosition().getX()+", "+rGrid.getPosition().getY());
		}
		if(GraphicsHelper.isSpriteTouched(att, att.getImageWidth(), att.getImageHeight(), e)){
			model.reverseMenu();
		}
		if(GraphicsHelper.isSpriteTouched(upsl, upsl.getImageWidth(), upsl.getImageHeight(), e)){
			model.reverseMenu();
		}
	}
	private void checkPlacingBox(MotionEvent e){
		if(GraphicsHelper.isSpriteTouched(rGrid, gridB.getWidth(), gridB.getHeight(), e) && model.isPlacing()){
			placeActiveSnowUnit();
			model.reversePlacing();
			model.reverseMenu();
		}if(GraphicsHelper.isSpriteTouched(lGrid, gridA.getWidth(), gridA.getHeight(), e) && model.isPlacing()){
			model.reversePlacing();
			model.reverseMenu();
		}
	}
	private void placeActiveSnowUnit() {
		SnowUnitSprite s;
		if(plActive == null)
			return;
		if(plActive.equals(plMassiveSnowball))
			s = massive;
	}
	private void checkPlacingButtons(MotionEvent event) {
		SnowUnitButton pressed = null;
		if(GraphicsHelper.isSpriteTouched(plSnowball, plSnowball.getImageWidth(), plSnowball.getImageHeight(), event)){
			pressed = plSnowball;
		}
		else if(GraphicsHelper.isSpriteTouched(plIcecube, plIcecube.getImageWidth(), plIcecube.getImageHeight(), event)){
			pressed = plIcecube;
		}
		else if(GraphicsHelper.isSpriteTouched(plMassiveSnowball, plMassiveSnowball.getImageWidth(), plMassiveSnowball.getImageHeight(), event)){
			pressed = plMassiveSnowball;
		}
		try {
			if(plActive == null){
				createNewPlaceButton(pressed, true);
				plActive = pressed;
				Log.d("SnowUnitButton", "Activate Button: "+pressed.getLabel()+" "+pressed.toString()+"active: "+pressed.isActive());								
				return;
			}
			if(plActive.getLabel().equals(pressed.getLabel())){
				Log.d("SnowUnitButton", "Pushed activated Button: "+pressed.getLabel()+" "+pressed.toString()+"active: "+plActive.getLabel());
				return;
			}
			Log.d("SnowUnitButton", "DeActivate Button: "+plActive.getLabel()+"activate: "+pressed.getLabel());
			createNewPlaceButton(plActive, false);
			createNewPlaceButton(pressed, true);
			plActive = pressed;
			
		} catch (NullPointerException e) {
			return;
		}
		
	}
	private void createNewPlaceButton(SnowUnitButton b,boolean flag){
		placeButtons.remove(b);
		update.removeSprite(b);
		guiobjects.removeSprite(b);
		Log.d("SnowUnitButton", "Position of Button1: "+b.getX()+", "+b.getY());
		if(flag)
			b = new SnowUnitButton(b.getX(), b.getY(), 
				b.getLabel(),red,true);
		else
			b = new SnowUnitButton(b.getX(), b.getY(), 
					b.getLabel(),white,false);
		placeButtons.add(b);
		update.addSprite(b);
		guiobjects.addSprite(b);
		b.setPosition(b.getX(), b.getY());
		update(updTime);
		Log.d("SnowUnitButton", "Position of Button2: "+b.getX()+", "+b.getY());
		
	}
	@Override
	public boolean onTouchUp(MotionEvent event) {
		checkPlacingBox(event);
		if(model.isMenu()){
			checkMenu(event);
			if(model.isPlacing()){
				checkPlacingButtons(event);
			}
		}else{	
			model.reverseMenu();
		}
		return false;
	}
	
}
