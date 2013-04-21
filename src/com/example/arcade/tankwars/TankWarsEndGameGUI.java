package com.example.arcade.tankwars;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.JetPlayer;
import android.view.MotionEvent;
import com.example.arcade.GraphicsHelper;
import sheep.graphics.Font;

/**
 * Created by:
 * User: Einar
 * Date: 21.04.13
 * Time: 19:45
 */
public class TankWarsEndGameGUI extends sheep.game.State {

    private String scoreText;
    private String winnerText;
    private String newGameText = "New game";
    private String toGameMenuText = "Back to game menu";
    private static final Font font = new Font(64, 64, 64, 50, Typeface.SANS_SERIF, Typeface.NORMAL);
    private int xPos = GraphicsHelper.getDisplaySize().x / 6;
    private int yInterval = GraphicsHelper.getDisplaySize().y / 5;

    public TankWarsEndGameGUI(int player1Score, int player2Score) {

        scoreText = "Score: " + player1Score + " - " + player2Score;

        if (player1Score > player2Score) {
            winnerText = "Player 1 won!";
        } else winnerText = "Player 2 won!";

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.BLACK); // Drawing a black background color

        canvas.drawText(scoreText, xPos, yInterval, font);
        canvas.drawText(winnerText, xPos, yInterval * 2, font);
        canvas.drawText(newGameText, xPos, yInterval * 3, font);
        canvas.drawText(toGameMenuText, xPos, yInterval * 4, font);
    }

    @Override
    public boolean onTouchUp(MotionEvent event) {

        int touchYPos = (int) event.getY();

        if (touchYPos > yInterval * 2 && touchYPos < yInterval * 3) {
            getGame().popState(); // Popping end game state
            getGame().popState(); // Popping tank wars state
            getGame().pushState(new TankWarsUserInterface(GraphicsHelper.getDisplaySize()));
        } else if (touchYPos > yInterval * 3) {
            getGame().popState(); // Popping end game state
            getGame().popState(); // Popping tank wars state
        }

        return true;
    }
}
