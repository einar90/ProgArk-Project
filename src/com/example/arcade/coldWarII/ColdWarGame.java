
package com.example.arcade.coldWarII;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

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
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.MiniGame;
import com.example.arcade.R;
import com.example.arcade.utilities.Constants;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.SpriteContainer;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;

public class ColdWarGame extends State implements MiniGame,PropertyChangeListener{
	private static Resources res = Game.getInstance().getResources();
	private SnowUnitSpriteContainer update, guiobjects, objects, playerOneContainer, playerTwoContainer;
	private float height, width;
	private SnowUnitButton btnUpgrade, btnAttack, btnDefence;
	private SnowUnitButton btnUpgradeSlingshot, btnUpgradeSnowProduction;
	private SnowUnitButton btnPlaceSnowball, btnPlaceMassiveSnowball, btnPlaceIceCube, btnPlaceIceWall, btnPlaceSelected;
	private SnowUnitButton btnAttackSnowball, btnAttackMassiveSnowball, btnAttackIcecube, btnAttackSelected;
	private Sprite background, ground, playerOneKing, playerTwoKing, spriteSnowflake, playerOneArrow, playerTwoArrow;
	private ColdWarModel model;
	private Image backgroundImage = new Image(R.drawable.coldwarii_background);
	private Image groundImage = new Image(R.drawable.coldwarii_ground);
	private Image snowflakeImage = new Image( R.drawable.coldwarii_snowflake);
	private Image snowballImage = new Image( R.drawable.coldwarii_snowball);
	private Image massiveSnow = new Image(R.drawable.coldwarii_massivesnowball);
	private Image icecubeImage = new Image(R.drawable.coldwarii_icecube);
	private Image icewallImage = new Image(R.drawable.coldwarii_icewall);
	private Image king1Image = new Image(R.drawable.coldwarii_king);
	private Image king2Image = new Image(R.drawable.coldwarii_king2);
	private Image king3Image = new Image(R.drawable.coldwarii_king3);
	private Image kingDeadImage = new Image(R.drawable.coldwarii_kingdead);
	private Image arrowImage = new Image(R.drawable.coldwarii_arrow);
	private Paint white = new Paint(Font.WHITE_SANS_BOLD_16);
	private float[] scaling;
	private ArrayList<SnowUnitButton> defenceButtons, attackButtons, upgradeButtons;
	private float updTime;
	private TextButton snowAmount;
	private boolean isDefenceMenuSelected = false;
	private boolean isAttackMenuSelected = false;
	private boolean isUpgradeMenuSelected = false;
	
	public ColdWarGame(){
		height = Constants.WINDOW_HEIGHT;
		width = Constants.WINDOW_WIDTH;
		updTime = System.currentTimeMillis();

		scaling = new float[]{width / 1600f, height / 960f};
//		backScale = new float[]{w/1600f,h/1549f};
		update = new SnowUnitSpriteContainer();
		guiobjects = new SnowUnitSpriteContainer();
		objects = new SnowUnitSpriteContainer();
		playerOneContainer = new SnowUnitSpriteContainer();
		playerTwoContainer = new SnowUnitSpriteContainer();
		model = new ColdWarModel(playerOneContainer,playerTwoContainer);

//		initButtonPaint();

		background = new Sprite(backgroundImage);
		background.setPosition(width/2, height/2);
		addToContainer(background, guiobjects);
		
		snowAmount = new TextButton(width-50, (snowflakeImage.getHeight()/2), ""+model.getSnowAmount()+"("+model.getSnowProduction()+")");
		Log.d("Arcade", "snowamount: "+model.getSnowAmount());
		
		spriteSnowflake = new Sprite(snowflakeImage);
		spriteSnowflake.setPosition(width-60, 10+(snowflakeImage.getHeight()/2));
		spriteSnowflake.setScale(scaling[0], scaling[1]);
		addToContainer(spriteSnowflake, guiobjects);

		ground = new Sprite(groundImage);
		ground.setPosition(width/2, height+15);
		addToContainer(ground, guiobjects);

//		Log.d("Size", "Size: "+w+", "+h+". Scale: "+scaling[0]+", "+scaling[1]);
//		Log.d("Size", "SizeImage: "+bg.getWidth()+", "+bg.getHeight()+"pos: "+back.getX()+", "+back.getY());
//		Log.d("Size", "SizeSprite: "+backfade.getScale()+", offset: "+backfade.getOffset());

//		lGrid = new Sprite(grid);
//		rGrid = new Sprite(grid);
//		rGrid.setScale(scaling[0], scaling[1]);
//		lGrid.setScale(scaling[0], scaling[1]);
//
//		lGrid.setPosition(w/6, h);
//		rGrid.setPosition(w-w/6, h);
//		addToContainer(rGrid, guiobjects);
//		addToContainer(lGrid, guiobjects);

		playerOneKing = new Sprite(king1Image);
		addToContainer(playerOneKing, guiobjects);
		playerOneKing.setPosition(50, height-200);
		playerOneKing.setScale(scaling[0], scaling[1]);
		playerOneKing.setSpeed(0, 200);

		playerTwoKing = new Sprite(king1Image);
		addToContainer(playerTwoKing, guiobjects);
		playerTwoKing.setPosition(width-50, height-200);
		playerTwoKing.setScale(scaling[0], scaling[1]);
		playerTwoKing.setSpeed(0, 200);

		playerOneArrow = new Sprite(arrowImage);
		playerOneArrow.setPosition(playerOneKing.getX(), height-10);
		playerOneArrow.setScale(scaling[0], scaling[1]);
		playerTwoArrow = new Sprite(arrowImage);
		playerTwoArrow.setPosition(playerTwoKing.getX(), height-10);
		playerTwoArrow.setScale(scaling[0], scaling[1]);
		
		initWall(5);
		initGUI();
	}
	private void initWall(int wallHeight){
		for (int i = 0; i < wallHeight; i++) {
			SnowUnitSprite sb = new SnowUnitSprite(snowballImage,model.getPlayerOne(),SnowUnitType.SNOWBALL);
			sb.setPosition((int) (Constants.WINDOW_WIDTH - (Constants.WINDOW_WIDTH/6)), height - ((i+1)*100));
			sb.setScale(scaling[0], scaling[1]);
			sb.setSpeed(0, 200);
			sb.addCollisionListener(model);
			addToContainer(sb, playerOneContainer);
		}
		for (int i = 0; i < wallHeight; i++) {
			SnowUnitSprite sb = new SnowUnitSprite(snowballImage,model.getPlayerTwo(),SnowUnitType.SNOWBALL);
			sb.setPosition((int) (Constants.WINDOW_WIDTH/6), height - ((i+1)*100));
			sb.setScale(scaling[0], scaling[1]);
			sb.setSpeed(0, 200);
			sb.addCollisionListener(model);
			addToContainer(sb, playerTwoContainer);
		}
	}
	private void initGUI(){
		initMenu();
		initDefenceMenu();
		initAttackMenu();
		initUpgradeMenu();
	}
	private void initMenu(){
		btnDefence = initSnowUnitButton("Defence", white, null);
		btnAttack = initSnowUnitButton("Attack", white, null);
		btnUpgrade = initSnowUnitButton("Upgrade", white, null);
		btnDefence.setPosition(1*(width / 7), btnDefence.getImageHeight()/2);
		btnAttack.setPosition((float)2.5*(width / 7), btnDefence.getImageHeight()/2);
		btnUpgrade.setPosition(4*(width / 7), btnDefence.getImageHeight()/2);
		addToContainer(btnDefence, guiobjects);
		addToContainer(btnAttack, guiobjects);
		addToContainer(btnUpgrade, guiobjects);
	}
	private void initDefenceMenu(){
		btnPlaceSnowball = initSnowUnitButton("Snowball(1)", white, SnowUnitType.SNOWBALL);
		btnPlaceIceCube = initSnowUnitButton("Icecube(2)", white, SnowUnitType.ICECUBE);
		btnPlaceMassiveSnowball = initSnowUnitButton("Massive Snowball(4)", white, SnowUnitType.MASSIVE);
		btnPlaceIceWall = initSnowUnitButton("Icewall(4)", white, SnowUnitType.ICEWALL);
		float x = btnDefence.getX();
		float y = btnDefence.getY();
		float h = btnDefence.getImageHeight();
		btnPlaceSnowball.setPosition(x, y+(h));
		btnPlaceIceCube.setPosition(x, y+(2*h));
		btnPlaceMassiveSnowball.setPosition(x, y+(3*h));
		btnPlaceIceWall.setPosition(x, y+(4*h));
		addToContainer(btnPlaceSnowball, guiobjects);
		addToContainer(btnPlaceIceCube, guiobjects);
		addToContainer(btnPlaceMassiveSnowball, guiobjects);
		addToContainer(btnPlaceIceWall, guiobjects);
		defenceButtons = new ArrayList<SnowUnitButton>();
		defenceButtons.add(btnPlaceSnowball);
		defenceButtons.add(btnPlaceIceCube);
		defenceButtons.add(btnPlaceMassiveSnowball);
		defenceButtons.add(btnPlaceIceWall);
	}
	private void initUpgradeMenu(){
		btnUpgradeSlingshot = initSnowUnitButton("Slingshot", white, null);
		btnUpgradeSnowProduction = initSnowUnitButton("Snow Production", white, null);
		float x = btnUpgrade.getX();
		float y = btnUpgrade.getY();
		float h = btnUpgrade.getImageHeight();
		btnUpgradeSlingshot.setPosition(x, y+(h));
		btnUpgradeSnowProduction.setPosition(x, y+(2*h));
		addToContainer(btnUpgradeSlingshot, guiobjects);
		addToContainer(btnUpgradeSnowProduction, guiobjects);
		upgradeButtons = new ArrayList<SnowUnitButton>();
		upgradeButtons.add(btnUpgradeSlingshot);
		upgradeButtons.add(btnUpgradeSnowProduction);
	}
	private void initAttackMenu(){
		btnAttackSnowball = initSnowUnitButton("Snowball(1)", white, SnowUnitType.SNOWBALL);
		btnAttackIcecube = initSnowUnitButton("Icecube(2)", white, SnowUnitType.ICECUBE);
		btnAttackMassiveSnowball = initSnowUnitButton("Massive Snowball(4)", white, SnowUnitType.MASSIVE);
		float x = btnAttack.getX();
		float y = btnAttack.getY();
		float h = btnAttack.getImageHeight();
		btnAttackSnowball.setPosition(x, y+(h));
		btnAttackIcecube.setPosition(x, y+(2*h));
		btnAttackMassiveSnowball.setPosition(x, y+(3*h));
		addToContainer(btnAttackSnowball, guiobjects);
		addToContainer(btnAttackIcecube, guiobjects);
		addToContainer(btnAttackMassiveSnowball, guiobjects);
		attackButtons = new ArrayList<SnowUnitButton>();
		attackButtons.add(btnAttackSnowball);
		attackButtons.add(btnAttackIcecube);
		attackButtons.add(btnAttackMassiveSnowball);
	}
//	private void initButtonPaint(){
//		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
//		white = new Paint(Font.WHITE_SANS_BOLD_16);
//		red = new Paint(white);
//		red.setColor(Color.RED);
//		Log.d("ButtonPaint", "pressed: "+red.getColor()+" idle: "+white.getColor());
//	}
	@Override
	public void draw(Canvas canvas) {
		if(canvas != null){
			drawBackground(canvas);
			drawSnowUnits(canvas);
			drawTopMenu(canvas);
			if(isDefenceMenuSelected){
				drawDefenceMenu(canvas);
			}else if(isAttackMenuSelected){
				drawAttackMenu(canvas);
			}else if(isUpgradeMenuSelected){
				drawUpgradeMenu(canvas);
			}
		}
	}
	private synchronized void drawSnowUnits(Canvas canvas) {
		ArrayList<Sprite> list1 = playerOneContainer.getSprites();
		ArrayList<Sprite> list2 = playerTwoContainer.getSprites();
		for (int i = 0; i < list1.size(); i++) {
			list1.get(i).draw(canvas);
		}
		for (int i = 0; i < list2.size(); i++) {
			list2.get(i).draw(canvas);
		}
	}
	private synchronized void drawTopMenu(Canvas canvas){
		btnAttack.draw(canvas);
		btnDefence.draw(canvas);
		btnUpgrade.draw(canvas);
	}

	private synchronized void drawBackground(Canvas canvas){
		background.draw(canvas);
		ground.draw(canvas);
		playerOneKing.draw(canvas);
		playerTwoKing.draw(canvas);
		spriteSnowflake.draw(canvas);
		snowAmount.draw(canvas);
		if(!model.isPlayerOne()){
			playerOneArrow.draw(canvas);
		}else{
			playerTwoArrow.draw(canvas);
		}
	}
	private void drawDefenceMenu(Canvas canvas){
		for (int i = 0; i < defenceButtons.size(); i++) {
			defenceButtons.get(i).draw(canvas);
		}
	}
	private void drawAttackMenu(Canvas canvas){
		for (int i = 0; i < attackButtons.size(); i++) {
			attackButtons.get(i).draw(canvas);
		}
	}
	private void drawUpgradeMenu(Canvas canvas){
		for (int i = 0; i < upgradeButtons.size(); i++) {
			upgradeButtons.get(i).draw(canvas);
		}
	}
	@Override
	public void update(float dt) {
		updTime += dt;
		ArrayList<Sprite> list = update.getSprites();
		for (int i = 0; i < list.size(); i++) {
			Sprite s = list.get(i);
			if(s == null){
				update(dt);
			}else{
				s.update(dt);
				if(ground.collides(s)){
					s.setSpeed(s.getSpeed().getX(), 0);
				}
				ArrayList<Sprite> snowUnitSpites1 = playerOneContainer.getSprites();
				ArrayList<Sprite> snowUnitSpites2 = playerTwoContainer.getSprites();
				for (int j = 0; j < snowUnitSpites1.size(); j++) {
					snowUnitSpites1.get(j).collides(s);
				}
				for (int j = 0; j < snowUnitSpites2.size(); j++) {
					snowUnitSpites2.get(j).collides(s);
				}
			}
		}
		playerOneArrow.setPosition(playerOneKing.getX(), playerOneKing.getY()+(king1Image.getHeight()/2)+5);
		playerTwoArrow.setPosition(playerTwoKing.getX(), playerTwoKing.getY()+(king1Image.getHeight()/2)+5);
		playerOneArrow.update(dt);
		playerTwoArrow.update(dt);
	}	

	private void addToContainer(Sprite s,SpriteContainer con){
		con.addSprite(s);
		s.setParent(con);
		update.addSprite(s);
	}

	private void checkTopMenu(MotionEvent e){
		if(GraphicsHelper.isSpriteTouched(btnDefence, btnDefence.getImageWidth(), btnDefence.getImageHeight(), e)){
			isDefenceMenuSelected = true; 
			isAttackMenuSelected = false; 
			isUpgradeMenuSelected = false;
		} else if(GraphicsHelper.isSpriteTouched(btnAttack, btnAttack.getImageWidth(), btnAttack.getImageHeight(), e)){
			isDefenceMenuSelected = false; 
			isAttackMenuSelected = true; 
			isUpgradeMenuSelected = false;
		} else if(GraphicsHelper.isSpriteTouched(btnUpgrade, btnUpgrade.getImageWidth(), btnUpgrade.getImageHeight(), e)){
			isDefenceMenuSelected = false; 
			isAttackMenuSelected = false; 
			isUpgradeMenuSelected = true;
		}
	}
	private void checkDefenceMenu(MotionEvent event) {
		if(GraphicsHelper.isSpriteTouched(btnPlaceSnowball, btnPlaceSnowball.getImageWidth(), btnPlaceSnowball.getImageHeight(), event)){
			setSnowUnitButton(btnPlaceSnowball, "defence");
		}else if(GraphicsHelper.isSpriteTouched(btnPlaceIceCube, btnPlaceIceCube.getImageWidth(), btnPlaceIceCube.getImageHeight(), event)){
			setSnowUnitButton(btnPlaceIceCube, "defence");
		}else if(GraphicsHelper.isSpriteTouched(btnPlaceMassiveSnowball, btnPlaceMassiveSnowball.getImageWidth(), btnPlaceMassiveSnowball.getImageHeight(), event)){
			setSnowUnitButton(btnPlaceMassiveSnowball, "defence");
		}else if(GraphicsHelper.isSpriteTouched(btnPlaceIceWall, btnPlaceIceWall.getImageWidth(), btnPlaceIceWall.getImageHeight(), event)){
			setSnowUnitButton(btnPlaceIceWall, "defence");
		}
	}
	private void checkAttackMenu(MotionEvent event) {
		if(GraphicsHelper.isSpriteTouched(btnAttackSnowball, btnAttackSnowball.getImageWidth(), btnAttackSnowball.getImageHeight(), event)){
			setSnowUnitButton(btnAttackSnowball, "attack");
		}else if(GraphicsHelper.isSpriteTouched(btnAttackIcecube, btnAttackIcecube.getImageWidth(), btnAttackIcecube.getImageHeight(), event)){
			setSnowUnitButton(btnAttackIcecube, "attack");
		}else if(GraphicsHelper.isSpriteTouched(btnAttackMassiveSnowball, btnAttackMassiveSnowball.getImageWidth(), btnAttackMassiveSnowball.getImageHeight(), event)){
			setSnowUnitButton(btnAttackMassiveSnowball, "attack");
		}
	}
	private void checkUpgradeMenu(MotionEvent event) {
		if(GraphicsHelper.isSpriteTouched(btnUpgradeSlingshot, btnUpgradeSlingshot.getImageWidth(), btnUpgradeSlingshot.getImageHeight(), event)){
			setSnowUnitButton(btnUpgradeSlingshot, "upgrade");
		}else if(GraphicsHelper.isSpriteTouched(btnUpgradeSnowProduction, btnUpgradeSnowProduction.getImageWidth(), btnUpgradeSnowProduction.getImageHeight(), event)){
			setSnowUnitButton(btnUpgradeSnowProduction, "upgrade");
		}
		
	}
	private void checkPlaceSnow(MotionEvent e){
		if(model.isPlayerOne() && e.getX() > width/2){
			if(e.getX() < width - (width/7)){
				placeActiveSnowUnit(e.getX(),e.getY());
				btnPlaceSelected = null;
			}
		}else if (!model.isPlayerOne() && e.getX() < width/2){
			if(e.getX() > width/7){
				placeActiveSnowUnit(e.getX(),e.getY());
				btnPlaceSelected = null;
			}
		}
	}
	
	private int[] calculateSpritePosition(float x,float y,SnowUnitType t){
		int colScale = (int) (70*scaling[0]);
		int startW;
		if(model.isPlayerOne()){
			if(x > Constants.WINDOW_WIDTH/2){
				startW = ((int)(x/colScale))*colScale;
				if(t == SnowUnitType.MASSIVE) return new int[]{startW+(colScale/2), (int)y};
				return new int[]{startW, (int)y};
			}
		}else{
			if(x < Constants.WINDOW_WIDTH/2){ 
				startW = ((int)(x/colScale))*colScale;
				return new int[]{startW, (int)y};
			} 
		}
		return null;
	}
	
	private void placeActiveSnowUnit(float x,float y) {
		if(btnPlaceSelected == null)
			return;
		else{
			int[] pos = calculateSpritePosition(x, y, btnPlaceSelected.getType());
			if(pos == null)
				return;
			switch (btnPlaceSelected.getType()) {
			case MASSIVE:
				drawSpriteOnGrid(massiveSnow, SnowUnitType.MASSIVE,pos[0],pos[1]);
				break;
			case ICECUBE:
				drawSpriteOnGrid(icecubeImage, SnowUnitType.ICECUBE,pos[0],pos[1]);
				break;
			case ICEWALL:
				drawSpriteOnGrid(icewallImage, SnowUnitType.ICEWALL,pos[0],pos[1]);
				break;
			case SNOWBALL:
				drawSpriteOnGrid(snowballImage, SnowUnitType.SNOWBALL,pos[0],pos[1]);
				break;
			default:
				break;
			}
		}
		model.changePlayer();
	}

	private void drawSpriteOnGrid(Image i,SnowUnitType t,int x,int y){
		SnowUnitSprite s = new SnowUnitSprite(i, model.getActivePlayer(), t);
		s.setPosition(x, 0);
		s.setScale(scaling[0], scaling[1]);
		s.setSpeed(0, 400);
		s.addCollisionListener(model);
		if(model.isPlayerOne()){
			addToContainer(s, playerOneContainer);
		}else{
			addToContainer(s, playerTwoContainer);
		}
	}



//	private Image getScaledImage(int id){
//		Bitmap unscaledBitmap = BitmapFactory.decodeResource(res, id);
//		Bitmap scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, (int) (unscaledBitmap.getWidth() * scaling[0]), (int) (unscaledBitmap.getHeight() *scaling[1]), true);
//		BitmapDrawable scaledDrawable = new BitmapDrawable(res, scaledBitmap);
//		scaledDrawable.setTargetDensity(res.getDisplayMetrics().densityDpi);
//		return new Image(scaledDrawable);
//	}
	private void setSnowUnitButton(SnowUnitButton b, String type){
		isDefenceMenuSelected = false; 
		isAttackMenuSelected = false; 
		isUpgradeMenuSelected = false;
		btnPlaceSelected = null;
		btnAttackSelected = null;
		if(type.equals("defence")){
			btnPlaceSelected = b;
		}else if(type.equals("attack")){
			btnAttackSelected = b;
		}else if(type.equals("upgrade")){
			if(b.getLabel().contains("Production")){
//				model.upgradeSnowProduction();
			}else{
//				model.upgradeSlingshot();
			}
		}
//		if(placing && plActive == null || !placing && attActive == null){
//			b.changePaint(Color.RED);
//			if(placing)
//				plActive = b;
//			else
//				attActive = b;
//			return;
//		}
//		if(placing && plActive.getLabel().equals(b.getLabel()) || !placing && attActive.getLabel().equals(b.getLabel())){
//			return;
//		}
//		b.changePaint(Color.RED);
//
//		if(placing){
//			plActive.changePaint(Color.WHITE);
//			plActive = b;
//		}
//		else{
//			attActive.changePaint(Color.WHITE);
//			attActive = b;
//		}
	}

	private SnowUnitButton initSnowUnitButton(String text, Paint paint, SnowUnitType t){
		Bitmap b = BitmapFactory.decodeResource(res, R.drawable.coldwarii_snowbutton)
				.copy(Bitmap.Config.ARGB_8888, true);

		Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

		paint.setTypeface(tf);
		paint.setStyle(Style.FILL);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(convertToPixels(11));

		Canvas canvas = new Canvas(b);

		Rect textRect = new Rect();
		paint.getTextBounds(text, 0, text.length(), textRect);

		//If the text is bigger than the canvas , reduce the font size
		if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
			paint.setTextSize(convertToPixels( 7));        //Scaling needs to be used for different dpi's

		//Calculate the positions
		int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

		//"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
		int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ; 


		canvas.drawText(text, xPos, yPos, paint);
		return new SnowUnitButton(text, b, canvas, xPos, yPos, paint, t) ;
	}
	private static int convertToPixels(int nDP){
		final float conversionScale = res.getDisplayMetrics().density;
		return (int) ((nDP * conversionScale) + 0.5f) ;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		checkTopMenu(event);
		if(isDefenceMenuSelected){
			checkDefenceMenu(event);
		}else if(isAttackMenuSelected){
			checkAttackMenu(event);
		}else if(isUpgradeMenuSelected){
			checkUpgradeMenu(event);
		}else if(btnPlaceSelected != null){
			checkPlaceSnow(event);
		}else if(btnAttackSelected != null){
			
		}
		return false;
	}
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName() == ColdWarModel.SNOW_AMOUNT){
			snowAmount.setLabel(""+model.getSnowAmount()+" ("+model.getSnowProduction()+")");
		}
		else if(event.getPropertyName() == ColdWarModel.SNOW_PRODUCTION){
			snowAmount.setLabel(""+model.getSnowAmount()+" ("+model.getSnowProduction()+")");
		}
	}

}
