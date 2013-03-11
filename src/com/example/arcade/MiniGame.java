package com.example.arcade;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 13:58
 */
public interface MiniGame {
    public void launchGame();

    public void exitGame();

    public HighscoreList getHighscoreList();
}
