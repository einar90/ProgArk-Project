package com.example.arcade;

import com.example.arcade.utilities.Constants;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.gui.TextButton;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 12:03
 */
public class MainMenu extends State {

    Resources resources;
    TextButton gamesButton;
    TextButton highscoresButton;
    TextButton settingsButton;

    Point displaySize;


    /**
     * Constructor
     */
    public MainMenu(Resources resouces) {
        this.resources = resouces;
        DisplayMetrics displayMetrics = resouces.getDisplayMetrics();
        Constants.WINDOW_HEIGHT = displayMetrics.heightPixels;
        Constants.WINDOW_WIDTH = displayMetrics.widthPixels;
        displaySize = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        gamesButton = new TextButton(getWidthPosition(displaySize), displaySize.y / 5, "Games");
        highscoresButton = new TextButton(getWidthPosition(displaySize), getHeightPosition(1, gamesButton, displaySize), "Highscores");
        settingsButton = new TextButton(getWidthPosition(displaySize), getHeightPosition(2, gamesButton, displaySize), "Settings");


    }

    public static int getHeightPosition(int posistion, TextButton relativeTo, Point displaySize) {
        float[] boxPoints = relativeTo.getBoundingBox().getPoints();
        float yPos = boxPoints[3] - boxPoints[1];
        yPos = 3 * yPos * posistion;
        yPos += displaySize.y / 5;

        return (int) yPos;


    }

    public static int getWidthPosition(Point displaySize) {
        return displaySize.x / 5;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gamesButton.draw(canvas);
        highscoresButton.draw(canvas);
        settingsButton.draw(canvas);
    }


    @Override
    public boolean onTouchDown(MotionEvent event) {
        if (gamesButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Log.d("Tapped", "Games button tapped.");
            getGame().pushState(new GamesMenu(displaySize, resources));
        } else if (highscoresButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Log.d("Tapped", "Highscore button tapped.");
        } else if (settingsButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Log.d("Tapped", "Settings button tapped.");
        }
        return true;
    }
}
