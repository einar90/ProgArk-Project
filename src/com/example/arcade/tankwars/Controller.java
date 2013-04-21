package com.example.arcade.tankwars;

import android.graphics.Point;
import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.tankwars.projectiles.*;
import sheep.math.Vector2;

import java.util.Calendar;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 15:21
 */
public class Controller {

    private static boolean firstPress = true;
    private static Calendar initialPressTime;

    private static Tank activeTank = Tank.getTank1();
    private static String chosenProjectile = "Bullet";

    /**
     * Changes the active tank
     */
    public static void changeActiveTank() {
        if (activeTank == Tank.getTank1()) {
            activeTank = Tank.getTank2();
        } else activeTank = Tank.getTank1();
    }

    /**
     * @return Returns the tank firing a projectile.
     */
    public static Tank getActiveTank() {
        return activeTank;
    }

    public static Tank getInactiveTank() {
        if (activeTank == Tank.getTank1()) {
            return Tank.getTank2();
        } else return Tank.getTank1();
    }

    public static void setChosenProjectile(String projectile) {

        if (activeTank.checkAmmo(projectile)) {
            chosenProjectile = projectile;
        }

    }

    /**
     * Sets the Tank's initial positions on the map.
     */
    public static void setInitialTankPositions() {
        Point size = GraphicsHelper.getDisplaySize();
        Tank.getTank1().setPosition(size.x / 10, size.y / 3);
        Tank.getTank2().setPosition(size.x - size.x / 10, size.y / 3);
        setInitalBarrelPositions();
    }

    /**
     * Sets the TankBarrel's initial positions on the map,
     * and tries to place them on top of the tanks.
     */
    public static void setInitalBarrelPositions() {
        Point size = GraphicsHelper.getDisplaySize();
        Tank.getTankBarrel1().setPosition(size.x / 10 + Tank.getTankBarrelImageDimensions().x / 2, size.y / 3 - Tank.getTankImageDimensions().y / 2);
        Tank.getTankBarrel2().setPosition(size.x - size.x / 10 + Tank.getTankBarrelImageDimensions().x / 2, size.y / 3 - Tank.getTankImageDimensions().y / 2 + (size.y / 40));
        Tank.getTankBarrel2().rotate(180);

    }

    /**
     * Sets the initial fallspeed of the tanks and barrels
     * in the Y direction towards the ground.
     */
    public static void setStartSpeed() {
        Tank.getTank1().setYSpeed(100);
        Tank.getTank2().setYSpeed(100);
        Tank.getTankBarrel1().setYSpeed(200);
        Tank.getTankBarrel2().setYSpeed(200);
    }

    /**
     * Stops the movement of the tanks and barrels
     * in the Y direction.
     */
    public static void stopStartSpeed() {
        Tank.getTank1().setYSpeed(0);
        Tank.getTank2().setYSpeed(0);
        Tank.getTankBarrel1().setYSpeed(0);
        Tank.getTankBarrel2().setYSpeed(0);

        //Avoid future collisions
        Tank.getTank1().setPosition(Tank.getTank1().getPosition().getX(), Tank.getTank1().getPosition().getY() - 1);
        Tank.getTank2().setPosition(Tank.getTank2().getPosition().getX(), Tank.getTank2().getPosition().getY() - 1);
    }

    /**
     * Takes in the health of the two tanks when one is dead, and
     * pushes the endgamescreen displaying some scores etc.
     *
     * @param hp1 First tanks hp
     * @param hp2 Second tanks hp
     */
    public static void setEndGameGUI(int hp1, int hp2) {
        Game.getInstance().pushState(new TankWarsEndGameGUI(hp1, hp2));
    }

    /**
     * Method to calculate the distance between two points/vectors.
     *
     * @param one First point
     * @param two Second point
     * @return Distance between the two points.
     */
    public static double calculateDistance(Vector2 one, Vector2 two) {
        return Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2));

    }


    /**
     * Used to aim the barrel of a tank. Changes the barrelAngle variable for the tank and rotates the sprite.
     *
     * @param touchPos The point on the screen being touched
     */
    public static void aimBarrel(Point touchPos) {
        Point tankPos = new Point((int) activeTank.getPosition().getX(), (int) activeTank.getPosition().getY());
        Vector2 aimVector = new Vector2(Math.abs(touchPos.x - tankPos.x), Math.abs(touchPos.y - tankPos.y));
        Vector2 normalizedAimVector = aimVector.getNormalized();
        double dotProduct = normalizedAimVector.getX();
        int angle = (int) Math.toDegrees(Math.acos(dotProduct));

        if (activeTank == Tank.getTank2()) {
            angle = -angle;
        }
        activeTank.setBarrelAngle(angle);
    }


    /**
     * Used to calculate the power the bullet should be fired with
     * given the time the user held down the shoot ability
     */
    public static void recordPower() {
        initialPressTime = Calendar.getInstance();
    }

    /**
     * Calculates the power the projectile is going to be fired with
     * given the time the user held down the shoot ability. (Milliseconds)
     */
    public static void calculatePower() {
        if (firstPress) {
            firstPress = false;
            return;
        }

        Calendar releasePressTime = Calendar.getInstance();
        long timeHeld = releasePressTime.getTimeInMillis() - initialPressTime.getTimeInMillis();
        activeTank.setTankPower(timeHeld);
    }

    /**
     * @return Projectile that is selected
     *         If its not instantiated, it instantiates it.
     */
    public static Projectile getProjectile() {
        if (firstPress) {
            firstPress = false;
            return null;
        }
        if (chosenProjectile.equals("Bullet")) {
            return new Bullet();
        } else if (chosenProjectile.equals("TankShell")) {
            return new TankShell();
        } else if (chosenProjectile.equals("ThermiteShells")) {
            return new ThermiteShell();
        } else if (chosenProjectile.equals("Nukes")) {
            return new Nuke();
        } else return new Bullet(); // Returning bullet if everything else fails.
    }

    public static String getChosenProjectile() {
        return chosenProjectile;
    }

}
