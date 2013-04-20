package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 14:43
 */
@SuppressWarnings("FieldCanBeLocal")
public class Map {

    private static final Resources resources = Game.getInstance().getResources();
    @SuppressWarnings("FieldCanBeLocal")
    private final Point displaySize = new Point(resources.getDisplayMetrics().widthPixels, resources.getDisplayMetrics().heightPixels);

    private static int windVector = 0;

    private static final Image mapGroundImage = GraphicsHelper.getScaledImage(resources, R.drawable.map_bottombox);
    private static final Image mountainLevel1Image = GraphicsHelper.getScaledImage(resources, R.drawable.mountain_level1);
    private static final Image mountainLevel2Image = GraphicsHelper.getScaledImage(resources, R.drawable.mountain_level2);
    private static final Image mountainLevel3Image = GraphicsHelper.getScaledImage(resources, R.drawable.mountain_level3);
    private static final Image mountainLevel4Image = GraphicsHelper.getScaledImage(resources, R.drawable.mountain_level4);

    private static final Sprite mapGround = new Sprite(mapGroundImage);
    private static final Sprite mountainLevel1 = new Sprite(mountainLevel1Image);
    private static final Sprite mountainLevel2 = new Sprite(mountainLevel2Image);
    private static final Sprite mountainLevel3 = new Sprite(mountainLevel3Image);
    private static final Sprite mountainLevel4 = new Sprite(mountainLevel4Image);

    /**
     * Sets the position of the map Sprites so they align correctly.
     */
    public Map() {
        mapGround.setPosition(mapGroundImage.getWidth() / 2, displaySize.y - mapGroundImage.getHeight() / 2);
        mountainLevel1.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2);
        mountainLevel2.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2 * 3);
        mountainLevel3.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2 * 5);
        mountainLevel4.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2 * 7);
    }

    /**
     * Draw Method, calls draw on all map sprites.
     *
     * @param canvas
     */
    public void drawMap(Canvas canvas) {
        canvas.drawColor(Color.CYAN);
        mapGround.draw(canvas);
        mountainLevel1.draw(canvas);
        mountainLevel2.draw(canvas);
        mountainLevel3.draw(canvas);
        mountainLevel4.draw(canvas);
    }

    /**
     * Update method, calls Update on all map sprites.
     *
     * @param dt
     */
    public void updateMap(float dt) {
        mapGround.update(dt);
        mountainLevel1.update(dt);
        mountainLevel2.update(dt);
        mountainLevel3.update(dt);
        mountainLevel4.update(dt);

    }

    /**
     * @return ArrayList containing all the map Sprites
     */
    public static ArrayList<Sprite> getMapSprites() {
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(mapGround);
        sprites.add(mountainLevel1);
        sprites.add(mountainLevel2);
        sprites.add(mountainLevel3);
        sprites.add(mountainLevel4);
        return sprites;
    }


    /**
     * Sets a new value for the wind vector.
     * The value is between -10 and +10.
     * Negative windVector values indicates wind to the left; positive indicates wind to the right.
     */
    public static void changeWindVector() {
        Random numberGenerator = new Random();
        windVector = numberGenerator.nextInt(20) - 10;
        Log.d("Value", "Wind: " + windVector);
    }

    /**
     * @return Returns the Wind Vector
     */
    public static int getWindVector() {
        return windVector;
    }

    /**
     * @return Returns the Windvector as a String
     */
    public static String getWindString() {
        String windString = "Wind: 0";
        if (windVector < 0) {
            windString = "Wind: " + (-windVector) + "\t<--";
        } else if (windVector > 0) {
            windString = "Wind: " + windVector + "\t-->";
        }
        return windString;
    }

    /**
     * @return Returns the height of the green ground.
     */
    public static int getGroundHeight() {
        return (int) mapGroundImage.getHeight();
    }

}
