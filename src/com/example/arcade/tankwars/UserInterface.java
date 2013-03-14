package com.example.arcade.tankwars;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import com.example.arcade.R;
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

    public UserInterface(Point displaySize) {
        this.displaySize = displaySize;
        this.scaling = new float[]{displaySize.x / 1280.0f, displaySize.y / 800.0f};

        mapGroundImage = new Image(R.drawable.map_bottombox);
        mapGround = new Sprite(mapGroundImage);
        float[] mapGroundSize = new float[]{mapGroundImage.getWidth() * scaling[0], mapGroundImage.getHeight() * scaling[1]};
        mapGround.setPosition(mapGroundSize[0] / 2, displaySize.y - mapGroundSize[1] / 2);
        mapGround.setScale(scaling[0], scaling[1]);

        mapPoopImage = new Image(R.drawable.map_poop);
        mapPoop = new Sprite(mapPoopImage);
        float[] mapPoopSize = new float[]{mapPoopImage.getWidth() * scaling[0], mapPoopImage.getHeight() * scaling[1]};
        mapPoop.setPosition(displaySize.x / 2, displaySize.y - mapGroundSize[1]);
        mapPoop.setScale(scaling[0], scaling[1]);

    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.BLUE);
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
