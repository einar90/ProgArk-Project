package com.example.arcade;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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

    public GamesMenu(Point displaySize) {
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
}
