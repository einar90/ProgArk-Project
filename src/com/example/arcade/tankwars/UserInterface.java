package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
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
    Polygon map;

    /**
     * Temp stuff
     */
    Image mapGroundImage;
    Image mapPoopImage;
    Sprite mapGround;
    Sprite mapPoop;
    float[] scaling;

    public UserInterface(Point displaySize, Resources resources) {
        this.displaySize = displaySize;
        this.scaling = new float[]{displaySize.x / 1280.0f, displaySize.y / 800.0f};

        mapGroundImage = Scaling.getScaledImage(resources.getDrawable(R.drawable.map_bottombox));
        mapGround = new Sprite(mapGroundImage);
        mapGround.setPosition(300, 300);

        mapPoopImage = Scaling.getScaledImage(resources.getDrawable(R.drawable.mountain_level1));
        mapPoop = new Sprite(mapPoopImage);
        mapPoop.setPosition(displaySize.x / 2, displaySize.y - mapPoopImage.getHeight() / 2);

    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.CYAN);
        mapPoop.draw(canvas);
        mapGround.draw(canvas);


    }

    public void update(float dt) {

        mapGround.update(dt);
        mapPoop.update(dt);

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
