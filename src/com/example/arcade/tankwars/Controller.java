package com.example.arcade.tankwars;

import android.graphics.Point;
import android.util.Log;
import sheep.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
public class Controller {


    /**
     * Used to aim the barrel of a tank. Changes the barrelAngle variable for the tank and rotates the sprite.
     *
     * @param tank     The tank being manipulated
     * @param touchPos The point on the screen being touched
     */
    public static void aimBarrel(Tank tank, Point touchPos) {
        Point tankPos = new Point((int) tank.getPosition().getX(), (int) tank.getPosition().getY());
        Log.d("Value", "tankPos: " + tankPos.x + ", " + tankPos.y);
        Vector2 aimVector = new Vector2(Math.abs(touchPos.x - tankPos.x), Math.abs(touchPos.y - tankPos.y));
        Log.d("Value", "aimVector: " + aimVector.getX() + ", " + aimVector.getY());
        Vector2 normalizedAimVector = aimVector.getNormalized();
        Log.d("Value", "normalizedAimVector: " + normalizedAimVector.getX() + ", " + normalizedAimVector.getY());
        Vector2 horizontaVector = new Vector2(Math.abs(touchPos.x - tankPos.y), 0);
        Log.d("Value", "horizontalVector: " + horizontaVector.getX() + ", " + horizontaVector.getY());
        double angle = Math.acos(horizontaVector.getLength() / aimVector.getLength());
        Log.d("Value", "angle: " + angle);
    }


}
