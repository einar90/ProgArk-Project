package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import com.example.arcade.R;
import com.example.arcade.Scaling;
import sheep.collision.Polygon;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class UserInterface extends State implements MiniGame {

    Point displaySize;

    Map map;
    Tank tanky;

    public UserInterface(Point displaySize, Resources resources) {

        map = new Map();
        tanky = Tank.getTank1();
        Tank.setInitialTankPositions(displaySize);
    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.CYAN);

        map.drawMap(canvas);
        tanky.draw(canvas);
    }

    public void update(float dt) {
        super.update(dt);

        map.updateMap(dt);
        tanky.update(dt);
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
