package com.example.arcade;

import java.util.TreeMap;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 13:59
 */
public abstract class HighscoreList {

    private TreeMap<String, Integer> highscoreList = new TreeMap<String, Integer>();

    public void addScore(String player, int score) {
        highscoreList.put(player, score);
    }

    public String getTopTen() {
        int highest = 0;
        for (int i = 0; i < highscoreList.size() - 1; i++) {

        }
        return "LOLLLLL";
    }

}
