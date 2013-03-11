package com.example.arcade;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.Widget;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 12:03
 */
public class MainMenu extends State implements WidgetListener {

    TextButton gamesButton;
    TextButton highscoresButton;
    TextButton settingsButton;


    /**
     * Constructor
     */
    public MainMenu(Resources resouces) {
        DisplayMetrics displayMetrics = resouces.getDisplayMetrics();
        Point displaySize = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        gamesButton = new TextButton(setRelativeMenuWidthPosition(displaySize), displaySize.y / 5, "Games");
        highscoresButton = new TextButton(setRelativeMenuWidthPosition(displaySize), setRelativeMenuHeightPosition(1, gamesButton, displaySize), "Highscores");
        settingsButton = new TextButton(setRelativeMenuWidthPosition(displaySize), setRelativeMenuHeightPosition(2, gamesButton, displaySize), "Settings");

        gamesButton.addWidgetListener(this);
        settingsButton.addWidgetListener(this);
        highscoresButton.addWidgetListener(this);


    }

    private static int setRelativeMenuHeightPosition(int posistion, TextButton relativeTo, Point displaySize) {
        float[] boxPoints = relativeTo.getBoundingBox().getPoints();
        float yPos = boxPoints[3] - boxPoints[1];
        yPos = 3 * yPos * posistion;
        yPos += displaySize.y / 5;

        return (int) yPos;


    }

    private static int setRelativeMenuWidthPosition(Point displaySize) {
        return displaySize.x / 5;
    }

    @Override
    public void update(float dt) {
        super.update(dt);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);    //To change body of overridden methods use File | Settings | File Templates.
        gamesButton.draw(canvas);
        highscoresButton.draw(canvas);
        settingsButton.draw(canvas);
    }

    @Override
    public void actionPerformed(WidgetAction action) {
        Widget dummy = action.getSource();

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onTouchDown(MotionEvent event) {
        if (gamesButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Log.d("Tapped", "Games button tapped.");
        }
        return true;
    }
}
