package com.example.arcade.tankwars;

import android.graphics.Canvas;
import android.media.JetPlayer;

/**
 * Created by:
 * User: Einar
 * Date: 21.04.13
 * Time: 19:45
 */
public class TankWarsEndGameGUI extends sheep.game.State {

    String scoreText;
    String winnerText;
    String newGameText = "New game";
    String toMainMenuText = "Back to main menu";

    public TankWarsEndGameGUI(int player1Score, int player2Score) {

        scoreText = "Score: " + player1Score;


    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
