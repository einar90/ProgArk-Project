package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import com.example.arcade.Game;
import com.example.arcade.R;
import com.example.arcade.Scaling;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 14:43
 */
public class Map {

    private static Resources resources = Game.getInstance().getResources();
    private Point displaySize = new Point(resources.getDisplayMetrics().widthPixels, resources.getDisplayMetrics().heightPixels);

    private static final Image mapGroundImage = Scaling.getScaledImage(resources, R.drawable.map_bottombox);
    private static final Image mountainLevel1Image = Scaling.getScaledImage(resources, R.drawable.mountain_level1);
    private static final Image mountainLevel2Image = Scaling.getScaledImage(resources, R.drawable.mountain_level2);
    private static final Image mountainLevel3Image = Scaling.getScaledImage(resources, R.drawable.mountain_level3);
    private static final Image mountainLevel4Image = Scaling.getScaledImage(resources, R.drawable.mountain_level4);

    private static final Sprite mapGround = new Sprite(mapGroundImage);
    private static final Sprite mountainLevel1 = new Sprite(mountainLevel1Image);
    private static final Sprite mountainLevel2 = new Sprite(mountainLevel2Image);
    private static final Sprite mountainLevel3 = new Sprite(mountainLevel3Image);
    private static final Sprite mountainLevel4 = new Sprite(mountainLevel4Image);


    public Map() {
        mapGround.setPosition(mapGroundImage.getWidth() / 2, displaySize.y - mapGroundImage.getHeight() / 2);
        mountainLevel1.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2);
        mountainLevel2.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2 * 3);
        mountainLevel3.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2 * 5);
        mountainLevel4.setPosition(displaySize.x / 2, displaySize.y - mapGroundImage.getHeight() - mountainLevel1Image.getHeight() / 2 * 7);
    }


    public void drawMap(Canvas canvas) {
        canvas.drawColor(Color.CYAN);
        mapGround.draw(canvas);
        mountainLevel1.draw(canvas);
        mountainLevel2.draw(canvas);
        mountainLevel3.draw(canvas);
        mountainLevel4.draw(canvas);
    }


    public void updateMap(float dt) {
        mapGround.update(dt);
        mountainLevel1.update(dt);
        mountainLevel2.update(dt);
        mountainLevel3.update(dt);
        mountainLevel4.update(dt);

    }


    public static int getGroundHeight() {
        return (int) mapGroundImage.getHeight();
    }

}
