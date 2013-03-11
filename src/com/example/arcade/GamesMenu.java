package com.example.arcade;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import com.example.arcade.tankwars.UserInterface;
import sheep.game.State;
import sheep.gui.TextButton;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 13:41
 */
public class GamesMenu extends State {

    TextButton tankWarsButton;
    TextButton wormsButton;
    TextButton coldWarIIButton;
    TextButton battleShipButton;
    Point displaySize;

    public GamesMenu(Point displaySize) {
        this.displaySize = displaySize;
        tankWarsButton = new TextButton(MainMenu.getWidthPosition(displaySize), displaySize.y / 5, "Tank Wars");
        wormsButton = new TextButton(MainMenu.getWidthPosition(displaySize), MainMenu.getHeightPosition(1, tankWarsButton, displaySize), "Worms");
        coldWarIIButton = new TextButton(MainMenu.getWidthPosition(displaySize), MainMenu.getHeightPosition(2, tankWarsButton, displaySize), "Cold War II");
        battleShipButton = new TextButton(MainMenu.getWidthPosition(displaySize), MainMenu.getHeightPosition(3, tankWarsButton, displaySize), "Battleship");


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawPaint(new Paint(Color.BLACK));
        tankWarsButton.draw(canvas);
        wormsButton.draw(canvas);
        coldWarIIButton.draw(canvas);
        battleShipButton.draw(canvas);

    }

    @Override
    public boolean onTouchDown(MotionEvent event) {
        if (tankWarsButton.getBoundingBox().contains(event.getX(), event.getY())) {
            getGame().pushState(new UserInterface(displaySize));
            Log.d("Tapped", "Tank Wars button tapped.");
        } else if (wormsButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Log.d("Tapped", "Worms button tapped.");
        } else if (coldWarIIButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Log.d("Tapped", "Cold War II button tapped.");
        } else if (battleShipButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Log.d("Tapped", "Battleship button tapped.");
        }
        return true;
    }
}
