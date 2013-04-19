
package com.example.arcade.coldWarII;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	private SnowUnitButton plSnowball, plMassiveSnowball, plIcecube, plIcewall, plActive;
	private SnowUnitSprite sUSIceball,ice,icecub,sUSIcewall,sUSMassive;
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

		sUSIceball = new SnowUnitSprite(icb,model.getPlayerTwo(),SnowUnitType.SNOWBALL);
		sUSIceball.setPosition(30, h - 10);
		sUSIceball.addCollisionListener(model);
		ice.addCollisionListener(model);
		addToContainer(sUSIceball, plTwoCon);
		
		initGUI();
	}
	private void initGUI(){
		initMenu();
		initPlaceMenu();
	}
	private void initMenu(){
		upsl = initSnowUnitButton("Upgrade Slingshot",white);
		upsl.setPosition(w / 2, h /2 - 200);
		upsn = initSnowUnitButton("Upgrade Snow Machine",white);
		upsn.setPosition(w / 2, h /2 - 130);
		place = initSnowUnitButton("Place Snow",white);
		place.setPosition(w / 2, h /2 - 60);
		att = initSnowUnitButton("Attack",white);
		att.setPosition(w / 2, h /2 + 10);
		addToContainer(upsl, guiobjects);
		addToContainer(upsn, guiobjects);
		addToContainer(place, guiobjects);
		addToContainer(att, guiobjects);
	}
	private void initPlaceMenu(){
		plIcecube = initSnowUnitButton("Icecube(2 su)",white);
		plMassiveSnowball = initSnowUnitButton("Massive Snowball(4 su)",white);
		plSnowball = initSnowUnitButton("Snowball(1 su)",white);
		plIcewall = initSnowUnitButton("Icewall(1 su)",white);
		if(model.isPlayerOne()){
			plIcecube.setPosition(150, 30);
			plSnowball.setPosition(150, 100);
			plMassiveSnowball.setPosition(150, 170);
			plIcewall.setPosition(150, 240);
		}
		else{
			plIcecube.setPosition(w - 100, 30);
			plSnowball.setPosition(w - 100, 100);
			plMassiveSnowball.setPosition(w - 100, 170);
			plIcewall.setPosition(w - 100, 240 );
		}
		addToContainer(plMassiveSnowball, guiobjects);
		addToContainer(plIcecube, guiobjects);
		addToContainer(plSnowball, guiobjects);
		addToContainer(plIcewall, guiobjects);
		placeButtons = new ArrayList<SnowUnitButton>();
		placeButtons.add(plIcecube);
		placeButtons.add(plSnowball);
		placeButtons.add(plMassiveSnowball);
		placeButtons.add(plIcewall);
		
		sUSMassive = new SnowUnitSprite(massiveSnow,model.getPlayerOne(),SnowUnitType.MASSIVE);
		sUSMassive.setPosition(150, 170 + 100);
		sUSMassive.setScale(scaling[0], scaling[1]);
		addToContainer(sUSMassive, guiobjects);
		
		
		icecub = new SnowUnitSprite(icecube,model.getPlayerTwo(),SnowUnitType.ICECUBE);
		icecub.setPosition(150+150, 30);
		icecub.setScale(scaling[0], scaling[1]);
		addToContainer(icecub, guiobjects);
		

	}
	private void initButtonPaint(){
		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
		white = new Paint(Font.WHITE_SANS_BOLD_16);
		red = new Paint(white);
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
		sUSMassive.draw(canvas);
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
			sUSIceball.setSpeed(200, -250);
			sUSIceball.setAcceleration(0, (float) 118.1);
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
			s = sUSMassive;
	}
	private void checkPlacingButtons(MotionEvent event) {
		if(GraphicsHelper.isSpriteTouched(plSnowball, plSnowball.getImageWidth(), plSnowball.getImageHeight(), event)){
			Log.d("SnowUnitButton", "plsnowball");
			setSnowUnitButton(plSnowball);
		}
		else if(GraphicsHelper.isSpriteTouched(plIcecube, plIcecube.getImageWidth(), plIcecube.getImageHeight(), event)){
			setSnowUnitButton(plIcecube);
		}
		else if(GraphicsHelper.isSpriteTouched(plMassiveSnowball, plMassiveSnowball.getImageWidth(), plMassiveSnowball.getImageHeight(), event)){
			setSnowUnitButton(plMassiveSnowball);
		}
		else if(GraphicsHelper.isSpriteTouched(plIcewall, plIcewall.getImageWidth(), plIcewall.getImageHeight(), event)){
			setSnowUnitButton(plIcewall);
		}
	}

	private void setSnowUnitButton(SnowUnitButton b){
		if(plActive == null){
			//			createNewPlaceButton(pressed, true);
			b.changePaint(Color.RED);
			plActive = b;
			Log.d("SnowUnitButton", "Activate Button: "+b.getLabel()+" "+b.toString());								
			return;
		}
		if(plActive.getLabel().equals(b.getLabel())){
			Log.d("SnowUnitButton", "Pushed activated Button: "+b.getLabel()+" "+b.toString()+"active: "+plActive.getLabel());
			return;
		}
		Log.d("SnowUnitButton", "DeActivate Button: "+plActive.getLabel()+"activate: "+b.getLabel());
		plActive.changePaint(Color.WHITE);
		b.changePaint(Color.RED);
		plActive = b;

		return;
	}

	//	private void createNewPlaceButton(SnowUnitButton b,boolean flag){
	//		placeButtons.remove(b);
	//		update.removeSprite(b);
	//		guiobjects.removeSprite(b);
	//		Log.d("SnowUnitButton", "Position of Button1: "+b.getX()+", "+b.getY());
	//		if(flag)
	//			b = new SnowUnitButton(b.getX(), b.getY(), 
	//				b.getLabel(),red,true);
	//		else
	//			b = new SnowUnitButton(b.getX(), b.getY(), 
	//					b.getLabel(),white,false);
	//		placeButtons.add(b);
	//		update.addSprite(b);
	//		guiobjects.addSprite(b);
	//		b.setPosition(b.getX(), b.getY());
	//		update(updTime);
	//		Log.d("SnowUnitButton", "Position of Button2: "+b.getX()+", "+b.getY());
	//		
	//	}

	private SnowUnitButton initSnowUnitButton(String text,Paint p){
		Bitmap b = BitmapFactory.decodeResource(res, R.drawable.snowbutton)
				.copy(Bitmap.Config.ARGB_8888, true);

		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

		p.setTypeface(tf);
		p.setStyle(Style.FILL);
		p.setTextAlign(Align.CENTER);
		p.setTextSize(convertToPixels(11));

		Canvas canvas = new Canvas(b);

		Rect textRect = new Rect();
		p.getTextBounds(text, 0, text.length(), textRect);

		//If the text is bigger than the canvas , reduce the font size
		if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
			p.setTextSize(convertToPixels( 7));        //Scaling needs to be used for different dpi's

		//Calculate the positions
		int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

		//"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
		int yPos = (int) ((canvas.getHeight() / 2) - ((p.descent() + p.ascent()) / 2)) ; 


		canvas.drawText(text, xPos, yPos, p);
		return new SnowUnitButton(text, b, canvas,xPos,yPos,p) ;
	}
	private static int convertToPixels(int nDP){
		final float conversionScale = res.getDisplayMetrics().density;
		return (int) ((nDP * conversionScale) + 0.5f) ;
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
