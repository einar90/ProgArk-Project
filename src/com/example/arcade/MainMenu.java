package com.example.arcade;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import com.example.arcade.utilities.Constants;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 12:03
 */
public class MainMenu extends State {

    private static Resources resources;
    private static Point displaySize;
    private Sprite playButton;
    private Sprite highscoresButton;
    private Sprite settingsButton;


    /**
     * Constructor
     */
    public MainMenu(Resources resources) {
        Constants.initConstants();
        this.resources = resources;
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        displaySize = new Point((int) Constants.WINDOW_WIDTH, (int) Constants.WINDOW_HEIGHT);

        initButtons();


    }

    private void initButtons() {
        Image playButtonImage = GraphicsHelper.getScaledImage(resources, R.drawable.play_button);
        Image highscoresButtonImage = GraphicsHelper.getScaledImage(resources, R.drawable.highscore_button);
        Image settingsButtonImage = GraphicsHelper.getScaledImage(resources, R.drawable.settings_button);

        playButton = new Sprite(playButtonImage);
        highscoresButton = new Sprite(highscoresButtonImage);
        settingsButton = new Sprite(settingsButtonImage);

        playButton.setPosition(displaySize.x / 2, displaySize.y / 4);
        highscoresButton.setPosition(displaySize.x / 2, displaySize.y / 2);
        settingsButton.setPosition(displaySize.x / 2, displaySize.y / 4 * 3);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (playButton != null) {
            playButton.update(dt);
            highscoresButton.update(dt);
            settingsButton.update(dt);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawPaint(new Paint(Color.BLACK));
            playButton.draw(canvas);
            highscoresButton.draw(canvas);
            settingsButton.draw(canvas);
        }
    }


    @Override
    public boolean onTouchUp(MotionEvent event) {
        if (playButton.getBoundingBox().contains(event.getX(), event.getY())) {
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
