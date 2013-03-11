package com.example.arcade;

import java.util.ArrayList;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 13:59
 */
public abstract class HighscoreList {

    private class Score {

        private String name;
        private int score;

        public Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }

    private ArrayList<Score> highscoreList = new ArrayList<Score>();

    public void addScore(String name, int score) {
        Score newScore = new Score(name, score);

        for (int i = 0; i < highscoreList.size(); i++) {
            if (newScore.getScore() > highscoreList.get(i).getScore()) {
                highscoreList.add(i, newScore);
            }
        }

        if (highscoreList.size() > 10) {
            highscoreList.remove(10);
        }

    }


    public String getTopTen() {
        String topTenString = "";

        for (int i = 0; i < highscoreList.size(); i++) {
            topTenString += (i + 1) + "\t" + highscoreList.get(i).getName() + "\t" + highscoreList.get(i).getScore() + "\n";
        }
        return topTenString;
    }

}
