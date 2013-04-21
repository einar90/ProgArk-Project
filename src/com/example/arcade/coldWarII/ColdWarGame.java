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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

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

public class ColdWarGame extends State implements MiniGame {
    private static Resources res = Game.getInstance().getResources();
    private SnowUnitSpriteContainer update, guiobjects, objects, plOneCon, plTwoCon;
    private float h, w;
    private SnowUnitButton upsl, upsn, att, place;
    private SnowUnitButton plSnowball, plMassiveSnowball, plIcecube, plIcewall, plActive;
    private SnowUnitSprite sUSIcecube, sUSSnowball, ice, icecub, sUSIcewall, sUSMassive;
    private Sprite back, backfade, ground, lGrid, rGrid, plOneKing, plTwoKing, spriteSnowflake;
    private ColdWarModel model;
    private Image bg = new Image(R.drawable.coldwarii_backgound);
    private Image bgfade = new Image(R.drawable.coldwarii_backgound);
    private Image groundImage = new Image(R.drawable.coldwarii_ground);
    private Image snowflake = new Image(R.drawable.coldwarii_snowflake);
    private Image snowball = new Image(R.drawable.coldwar_snowball);
    private Image grid = new Image(R.drawable.wintergrid);
    private Image massiveSnow = new Image(R.drawable.massivesnowball);
    private Image icecube = new Image(R.drawable.icecube);
    private Image icewall = new Image(R.drawable.icewall);
    private Image king1 = new Image(R.drawable.king);
    private Image king2 = new Image(R.drawable.king2);
    private Image king3 = new Image(R.drawable.king3);
    private Image kingDead = new Image(R.drawable.kingdead);
    private float[] scaling, posScale;
    private Paint white, red;
    private ArrayList<SnowUnitButton> placeButtons;
    private SnowUnitSprite placeSprite;
    private float updTime;
    private int[] occupiedGrids;

    public ColdWarGame() {
        h = Constants.WINDOW_HEIGHT;
        w = Constants.WINDOW_WIDTH;
        updTime = System.currentTimeMillis();
        occupiedGrids = new int[]{6, 4, 5, 4};

        scaling = new float[]{w / 1600f, h / 960f};
        update = new SnowUnitSpriteContainer();
        guiobjects = new SnowUnitSpriteContainer();
        objects = new SnowUnitSpriteContainer();
        plOneCon = new SnowUnitSpriteContainer();
        plTwoCon = new SnowUnitSpriteContainer();
        model = new ColdWarModel(plOneCon, plTwoCon);
//		bg = getScaledImage(  R.drawable.winter);
//		bgfade = getScaledImage(  R.drawable.winterfade);

        initButtonPaint();

        back = new Sprite(bg);
        back.setPosition(w / 2, h / 2);
        addToContainer(back, guiobjects);

        backfade = new Sprite(bgfade);
        backfade.setPosition(w / 2, h / 2);
        addToContainer(backfade, guiobjects);

        ground = new Sprite(groundImage);
        ground.setPosition(w / 2, h + 15);
        addToContainer(ground, guiobjects);

        Log.d("Size", "Size: " + w + ", " + h + ". Scale: " + scaling[0] + ", " + scaling[1]);
        Log.d("Size", "SizeImage: " + bg.getWidth() + ", " + bg.getHeight() + "pos: " + back.getX() + ", " + back.getY());
        Log.d("Size", "SizeSprite: " + backfade.getScale() + ", offset: " + backfade.getOffset());

        lGrid = new Sprite(grid);
        rGrid = new Sprite(grid);
        rGrid.setScale(scaling[0], scaling[1]);
        lGrid.setScale(scaling[0], scaling[1]);

        lGrid.setPosition(w / 6, h);
        rGrid.setPosition(w - w / 6, h);
        addToContainer(rGrid, guiobjects);
        addToContainer(lGrid, guiobjects);

        plOneKing = new Sprite(king1);
        addToContainer(plOneKing, guiobjects);
        plOneKing.setPosition(50, h);
        plOneKing.setScale(scaling[0], scaling[1]);

        plTwoKing = new Sprite(king1);
        addToContainer(plTwoKing, guiobjects);
        plTwoKing.setPosition(w - 50, h);
        plTwoKing.setScale(scaling[0], scaling[1]);

        ice = new SnowUnitSprite(snowball, model.getPlayerOne(), SnowUnitType.SNOWBALL);
        ice.setPosition(w - 30, h - 10);
        ice.setScale(scaling[0], scaling[1]);
        addToContainer(ice, plOneCon);
        sUSSnowball = new SnowUnitSprite(snowball, model.getPlayerTwo(), SnowUnitType.SNOWBALL);
        sUSSnowball.setPosition(30 + snowball.getWidth() / 2, h - 10);
        sUSSnowball.setScale(scaling[0], scaling[1]);
        sUSSnowball.addCollisionListener(model);
        ice.addCollisionListener(model);
        addToContainer(sUSSnowball, plTwoCon);

        initGUI();
    }

    private void initGUI() {
        initMenu();
        initPlaceMenu();
    }

    private void initMenu() {
        upsl = initSnowUnitButton("Upgrade Slingshot", white, null);
        upsl.setPosition(w / 2, h / 2 - 200);
        upsn = initSnowUnitButton("Upgrade Snow Machine", white, null);
        upsn.setPosition(w / 2, h / 2 - 130);
        place = initSnowUnitButton("Place Snow", white, null);
        place.setPosition(w / 2, h / 2 - 60);
        att = initSnowUnitButton("Attack", white, null);
        att.setPosition(w / 2, h / 2 + 10);
        addToContainer(upsl, guiobjects);
        addToContainer(upsn, guiobjects);
        addToContainer(place, guiobjects);
        addToContainer(att, guiobjects);
    }

    private void initPlaceMenu() {
        plIcecube = initSnowUnitButton("Icecube(2 su)", white, SnowUnitType.ICECUBE);
        plMassiveSnowball = initSnowUnitButton("Massive Snowball(4 su)", white, SnowUnitType.MASSIVE);
        plSnowball = initSnowUnitButton("Snowball(1 su)", white, SnowUnitType.SNOWBALL);
        plIcewall = initSnowUnitButton("Icewall(4 su)", white, SnowUnitType.ICEWALL);
        drawSpriteOnGrid(icewall, SnowUnitType.ICEWALL, (int) (rGrid.getX()), (int) h);
        placeSpriteOnGrid();
        if (model.isPlayerOne()) {
            plIcecube.setPosition(150, 30);
            plSnowball.setPosition(150, 100);
            plMassiveSnowball.setPosition(150, 170);
            plIcewall.setPosition(150, 240);
        } else {
            plIcecube.setPosition(w - 100, 30);
            plSnowball.setPosition(w - 100, 100);
            plMassiveSnowball.setPosition(w - 100, 170);
            plIcewall.setPosition(w - 100, 240);
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

        sUSMassive = new SnowUnitSprite(snowball, model.getPlayerOne(), SnowUnitType.SNOWBALL);
        sUSMassive.setPosition(100, 100);
        sUSMassive.setScale(scaling[0], scaling[1]);
        addToContainer(sUSMassive, guiobjects);


        icecub = new SnowUnitSprite(icecube, model.getPlayerTwo(), SnowUnitType.ICECUBE);
        icecub.setPosition(400, 480);
        icecub.setScale(scaling[0], scaling[1]);
        addToContainer(icecub, guiobjects);


    }

    private void initButtonPaint() {
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        white = new Paint(Font.WHITE_SANS_BOLD_16);
        red = new Paint(white);
        red.setColor(Color.RED);
        Log.d("ButtonPaint", "pressed: " + red.getColor() + " idle: " + white.getColor());
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawPaint(new Paint(Color.BLACK));
            if (!model.isMenu()) {
                back.draw(canvas);
                ground.draw(canvas);
                drawSnowUnits(canvas);
            } else {
                drawMenu(canvas);
                if (model.isPlacing()) {
                    drawPlace(canvas);
                }
            }
        }
    }

    private void drawSnowUnits(Canvas c) {
        ArrayList<Sprite> s1 = plOneCon.getSprites();
        ArrayList<Sprite> s2 = plTwoCon.getSprites();
        for (Sprite s : s1) {
            s.draw(c);
        }
        for (Sprite s : s2) {
            s.draw(c);
        }
    }

    private void drawMenu(Canvas c) {
        backfade.draw(c);
        upsl.draw(c);
        upsn.draw(c);
        place.draw(c);
        att.draw(c);
    }

    private void drawPlace(Canvas canvas) {
        backfade.draw(canvas);
        ground.draw(canvas);
        if (model.isPlayerOne()) {
            rGrid.draw(canvas);
            for (int i = 0; i < plOneCon.getSprites().size(); i++) {
                plOneCon.getSprites().get(i).draw(canvas);
            }
        } else {
            for (int i = 0; i < plTwoCon.getSprites().size(); i++) {
                plTwoCon.getSprites().get(i).draw(canvas);
            }
            rGrid.draw(canvas);
        }
        for (int i = 0; i < placeButtons.size(); i++) {
            placeButtons.get(i).draw(canvas);
        }
        if (placeSprite != null)
            placeSprite.draw(canvas);
        //		Toast.makeText(Game.getInstance().getContext(), "Select type of SnowUnit(<-), " +
        //				"and then touch where you want it on the grid below", Toast.LENGTH_SHORT).show();
        sUSMassive.draw(canvas);
        icecub.draw(canvas);
        plOneKing.draw(canvas);
        plTwoKing.draw(canvas);
    }

    private void drawAttackMenu() {

    }

    @Override
    public void update(float dt) {
        updTime += dt;
        ArrayList<Sprite> list = update.getSprites();
        for (int i = 0; i < list.size(); i++) {
            Sprite s = list.get(i);
            if (s == null)
                update(dt);
            else
                s.update(dt);
        }

        checkCollision();
    }

    private void addToContainer(Sprite s, SpriteContainer con) {
        con.addSprite(s);
        s.setParent(con);
        update.addSprite(s);
    }


    private void checkCollision() {
        ArrayList<Sprite> s1 = plOneCon.getSprites();
        ArrayList<Sprite> s2 = plTwoCon.getSprites();
        for (int i = 0; i < s1.size(); i++) {
            for (int j = 0; j < s2.size(); j++) {
                if (s1.get(i).collides(s2.get(j)))
                    Log.d("COllision", "checkCollision()");
            }
        }
    }


    private void checkMenu(MotionEvent e) {
        if (GraphicsHelper.isSpriteTouched(upsn, upsn.getImageWidth(), upsn.getImageHeight(), e)) {
            model.reverseMenu();
            Log.d("Ball", "started moving");
            sUSSnowball.setSpeed(200, -250);
            sUSSnowball.setAcceleration(0, (float) 118.1);
            ice.setSpeed(-200, -250);
            ice.setAcceleration(0, (float) 118.1);
        }
        if (GraphicsHelper.isSpriteTouched(place, place.getImageWidth(), place.getImageHeight(), e)) {
            model.reversePlacing();
            Log.d("Scale", "grids:" + lGrid.getPosition().getX() + ", " + lGrid.getPosition().getY());
            Log.d("Scale", "grids:" + rGrid.getPosition().getX() + ", " + rGrid.getPosition().getY());
        }
        if (GraphicsHelper.isSpriteTouched(att, att.getImageWidth(), att.getImageHeight(), e)) {
            model.reverseMenu();
        }
        if (GraphicsHelper.isSpriteTouched(upsl, upsl.getImageWidth(), upsl.getImageHeight(), e)) {
            model.reverseMenu();
        }
    }

    private void checkPlacingBox(MotionEvent e) {
        if (GraphicsHelper.isSpriteTouched(rGrid, grid.getWidth(), grid.getHeight(), e) && model.isPlacing()) {
            placeActiveSnowUnit(e.getX(), e.getY());
            //			model.reversePlacing();
            //			model.reverseMenu();
        }
        if (GraphicsHelper.isSpriteTouched(lGrid, grid.getWidth(), grid.getHeight(), e) && model.isPlacing()) {
            model.reversePlacing();
            model.reverseMenu();
        }
    }

    private int[] calculateSpritePosition(float x, float y, SnowUnitType t) {
        int colScale = (int) (70 * scaling[0]);
        int rowScale = (int) (72 * scaling[1]);

        int startW, startH;
        if (model.isPlayerOne()) {
            startW = (int) (rGrid.getX() - (grid.getWidth() * scaling[0]));
            startH = (int) (rGrid.getY() - (grid.getHeight() * scaling[1]));
        } else {
            startW = (int) (lGrid.getX() - (grid.getWidth() * scaling[0]));
            startH = (int) (lGrid.getY() - (grid.getHeight() * scaling[1]));
        }

        Log.d("PlaceSprite", "X: " + x + " Y: " + y + " sW: " + (startW) + " sH: " + (startH));


        int row, column, minrow = 1, mincol = 1, maxrow = 5, maxcol = 3;
        if (t == SnowUnitType.ICEWALL || t == SnowUnitType.MASSIVE)
            minrow = 2;
        if (t == SnowUnitType.MASSIVE)
            mincol = 2;


        row = (int) (minrow + (y - startH) / rowScale);
        column = (int) (mincol + (x - startW) / colScale);

        if (column > maxcol)
            column = maxcol;
        if (row > maxrow)
            row = maxrow;

        int[] array = new int[]{startW + (colScale * column), startH + (rowScale * row)};
        Log.d("PlaceSprite", "row: " + row + " column: " + column + ", stW-x: " + (startW - x) + " stH-y: " + (startH - y) + ", array: [" + array[0] + "][" + array[1] + "]");

        int bolrow = row, bolcol = column;
        boolean flag = false;
        if (t == SnowUnitType.MASSIVE) {
            if (model.isGridEmpty(bolrow, bolcol))
                if (model.isGridEmpty(bolrow - 1, bolcol))
                    if (model.isGridEmpty(bolrow, bolcol - 1))
                        if (model.isGridEmpty(bolrow - 1, bolcol - 1)) {
                            flag = true;
                            occupiedGrids = new int[]{bolrow, bolcol, bolrow - 1, bolcol, bolrow, bolcol - 1, bolrow - 1, bolcol - 1};
                        }
        } else if (t == SnowUnitType.ICEWALL) {
            if (model.isGridEmpty(bolrow, bolcol))
                if (model.isGridEmpty(bolrow - 1, bolcol)) {
                    occupiedGrids = new int[]{bolrow, bolcol, bolrow - 1, bolcol};
                    flag = true;
                }
        } else {
            if (model.isGridEmpty(bolrow, bolcol)) {
                occupiedGrids = new int[]{bolrow, bolcol};
                flag = true;
            }
        }
        if (!flag)
            return null;

        return array;
    }

    private void placeActiveSnowUnit(float x, float y) {
        if (plActive == null)
            return;
        else {
            int[] pos = calculateSpritePosition(x, y, plActive.getType());
            if (pos == null)
                return;
            switch (plActive.getType()) {
                case MASSIVE:
                    drawSpriteOnGrid(massiveSnow, SnowUnitType.MASSIVE, pos[0], pos[1]);
                    break;
                case ICECUBE:
                    drawSpriteOnGrid(icecube, SnowUnitType.ICECUBE, pos[0], pos[1]);
                    break;
                case ICEWALL:
                    drawSpriteOnGrid(icewall, SnowUnitType.ICEWALL, pos[0], pos[1]);
                    break;
                case SNOWBALL:
                    drawSpriteOnGrid(snowball, SnowUnitType.SNOWBALL, pos[0], pos[1]);
                    break;
                default:
                    break;
            }
        }
    }

    private void drawSpriteOnGrid(Image i, SnowUnitType t, int x, int y) {
        SnowUnitSprite s = new SnowUnitSprite(i, model.getActivePlayer(), t);
        if (placeSprite == null) {
            placeSprite = s;
        }
        s.setPosition(x, y);
        s.setScale(scaling[0], scaling[1]);
        placeSprite = s;
        update.addSprite(placeSprite);
    }

    private void placeSpriteOnGrid() {
        addToContainer(placeSprite, plOneCon);
        model.setGridOccupied(occupiedGrids);
    }

    private void checkPlacingButtons(MotionEvent event) {
        if (GraphicsHelper.isSpriteTouched(plSnowball, plSnowball.getImageWidth(), plSnowball.getImageHeight(), event)) {
            setSnowUnitButton(plSnowball);
        } else if (GraphicsHelper.isSpriteTouched(plIcecube, plIcecube.getImageWidth(), plIcecube.getImageHeight(), event)) {
            setSnowUnitButton(plIcecube);
        } else if (GraphicsHelper.isSpriteTouched(plMassiveSnowball, plMassiveSnowball.getImageWidth(), plMassiveSnowball.getImageHeight(), event)) {
            setSnowUnitButton(plMassiveSnowball);
        } else if (GraphicsHelper.isSpriteTouched(plIcewall, plIcewall.getImageWidth(), plIcewall.getImageHeight(), event)) {
            setSnowUnitButton(plIcewall);
        }
    }

    private Image getScaledImage(int id) {
        Bitmap unscaledBitmap = BitmapFactory.decodeResource(res, id);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, (int) (unscaledBitmap.getWidth() * scaling[0]), (int) (unscaledBitmap.getHeight() * scaling[1]), true);
        BitmapDrawable scaledDrawable = new BitmapDrawable(res, scaledBitmap);
        scaledDrawable.setTargetDensity(res.getDisplayMetrics().densityDpi);
        return new Image(scaledDrawable);
    }

    private void setSnowUnitButton(SnowUnitButton b) {
        if (plActive == null) {
            b.changePaint(Color.RED);
            plActive = b;
            Log.d("SnowUnitButton", "Activate Button: " + b.getLabel() + " " + b.toString());
            return;
        }
        if (plActive.getLabel().equals(b.getLabel())) {
            Log.d("SnowUnitButton", "Pushed activated Button: " + b.getLabel() + " " + b.toString() + "active: " + plActive.getLabel());
            return;
        }
        Log.d("SnowUnitButton", "DeActivate Button: " + plActive.getLabel() + "activate: " + b.getLabel());
        plActive.changePaint(Color.WHITE);
        b.changePaint(Color.RED);
        placeSprite = null;
        plOneCon.removeSprite(placeSprite);
        plActive = b;
    }

    private SnowUnitButton initSnowUnitButton(String text, Paint p, SnowUnitType t) {
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
        if (textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            p.setTextSize(convertToPixels(7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((p.descent() + p.ascent()) / 2));


        canvas.drawText(text, xPos, yPos, p);
        return new SnowUnitButton(text, b, canvas, xPos, yPos, p, t);
    }

    private static int convertToPixels(int nDP) {
        final float conversionScale = res.getDisplayMetrics().density;
        return (int) ((nDP * conversionScale) + 0.5f);
    }

    @Override
    public boolean onTouchUp(MotionEvent event) {
        checkPlacingBox(event);
        if (model.isMenu()) {
            checkMenu(event);
            if (model.isPlacing()) {
                checkPlacingButtons(event);
            }
        } else {
            model.reverseMenu();
        }
        return false;
    }

}
