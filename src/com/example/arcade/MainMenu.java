package com.example.arcade;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import sheep.game.State;
import sheep.gui.TextButton;

/**
 * Created with IntelliJ IDEA.
 * User: Einar
 * Date: 11.03.13
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
public class MainMenu extends State {

    TextButton gamesButton;
    TextButton highscoresButton;
    TextButton settingsButton;

    /**
     * Constructor
     */
    public MainMenu(Resources resouces) {
        DisplayMetrics displayMetrics = resouces.getDisplayMetrics();
        Point displaySize = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        gamesButton = new TextButton(displaySize.x / 2, displaySize.y / 5, "Games");

    }

    @Override
    public void update(float dt) {
        super.update(dt);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);    //To change body of overridden methods use File | Settings | File Templates.
        gamesButton.draw(canvas);
    }
}
