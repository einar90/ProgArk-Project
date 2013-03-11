package com.example.arcade.tankwars;

import android.graphics.Canvas;
import android.graphics.Point;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import sheep.collision.Polygon;
import sheep.game.State;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class UserInterface extends State implements MiniGame {

    Point displaySize;
    Polygon map;

    public UserInterface(Point displaySize) {
        displaySize = displaySize;
        map = (new Map(displaySize)).getMapPolygon();

    }


    public void draw(Canvas canvas) {


    }

    public void update(float dt) {

    }

    @Override
    public void launchGame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void exitGame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HighscoreList getHighscoreList() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
