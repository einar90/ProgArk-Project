package com.example.arcade.tankwars;

import android.graphics.Point;
import android.util.Log;
import sheep.math.Vector2;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
public class Controller {

    private static boolean firstPress = true;
    private static Calendar initialPressTime;
    private static Calendar releasePressTime;

    private static Tank activeTank = Tank.getTank2();
    private static String chosenProjectile = "Bullet";


    /**
     * Changes the active tank
     */
    public static void changeActiveTank() {
        if (activeTank == Tank.getTank1()) {
            activeTank = Tank.getTank2();
        } else activeTank = Tank.getTank1();
    }

    public static Tank getActiveTank() {
        return activeTank;
    }


    /**
     * Used to aim the barrel of a tank. Changes the barrelAngle variable for the tank and rotates the sprite.
     *
     * @param touchPos The point on the screen being touched
     */
    public static void aimBarrel(Point touchPos) {
        // Calculating angle
        Point tankPos = new Point((int) activeTank.getPosition().getX(), (int) activeTank.getPosition().getY());
        Vector2 aimVector = new Vector2(Math.abs(touchPos.x - tankPos.x), Math.abs(touchPos.y - tankPos.y));
        Vector2 normalizedAimVector = aimVector.getNormalized();
        double dotProduct = normalizedAimVector.getX();
        int angle = (int) Math.toDegrees(Math.acos(dotProduct));

        // Changing sign of angle if tank 2 is active
        if (activeTank == Tank.getTank2()) {
            angle = -angle;
        }
        // Changing angle of tank barrel
        activeTank.setBarrelAngle(angle);
    }


    /**
     * Used to calculate the power the bullet should be fired with
     * given the time the user held down the shoot ability
     */
    public static void recordPower() {

        Log.d("Controller", "Starting to record fire");
        initialPressTime = Calendar.getInstance();

    }


    public static void calculatePower() {
        //Released
        if (firstPress == true) {
            firstPress = false;
            return;
        }

        releasePressTime = Calendar.getInstance();

        //Dette er tidsforskjellen.
        long timeHeld = releasePressTime.getTimeInMillis() - initialPressTime.getTimeInMillis();
        Log.d("Controller", "Released the hold on fire, held for: " + timeHeld);

        activeTank.setTankPower(timeHeld);
    }

    public static Projectile getProjectile() {
        if (chosenProjectile.equals("Bullet")) {
            return new Bullet();
        } else return new Bullet();
    }

}
