package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:50
 */
public class Tank extends Sprite {

    private static final Resources resources = Game.getInstance().getResources();

    //Tank
    private static final Image tankImage1 = GraphicsHelper.getScaledImage(resources, R.drawable.tankbody1);
    private static final Image tankImage2 = GraphicsHelper.getFlippedScaledImage(resources, R.drawable.tankbody2);
    private static Tank tank1 = new Tank(tankImage1);
    private static Tank tank2 = new Tank(tankImage2);

    //TankBarrel
    private static final Image tankBarrelImage = GraphicsHelper.getScaledImage(resources, R.drawable.tankbarrel);
    private static Sprite tankBarrel1 = new Sprite(tankBarrelImage);
    private static Sprite tankBarrel2 = new Sprite(tankBarrelImage);

    //Extra stuff for Tank to hold
    private int barrelAngle;
    private int power;
    private int hp;
    private Sprite tankBarrel;
    private Dictionary projectileAmmo;    //Vet ikke om dictionary fungerer så bra


    /**
     * Constructor for tank, creates the tank object,
     * and sets initial values like hp, barrelangle etc.
     *
     * @param tankimage The Sheep.Graphics.Image the tank will be using
     */
    Tank(Image tankimage) {
        super(tankimage);
        this.hp = 100;
        this.power = 0;
        this.barrelAngle = 0;
        this.projectileAmmo = setStartingAmmo(15, 1, 3);
        Log.d("Tank", "Tank initialized and ammo is: \n" + this.projectileAmmo.toString());

    }

    /**
     * Static method to retrieve Player One tank
     *
     * @return Player One Tank
     */
    public static Tank getTank1() {
        Log.d("Tank", "Returning tank1");
        if (tank1 == null) {
            tank1 = new Tank(tankImage1);
        }
        return tank1;
    }

    /**
     * Static method to retrieve Player Two tank
     *
     * @return Player Two Tank
     */
    public static Tank getTank2() {
        Log.d("Tank", "Returning tank2");
        if (tank2 == null) {
            tank2 = new Tank(tankImage2);
        }
        return tank2;
    }

    /**
     * Static method to retrieve Player 1's TankBarrel
     *
     * @return Player 1's TankBarrel
     */
    public static Sprite getTankBarrel1() {
        //Log.d("Tank", "Returning tankbarrel1");
        if (tankBarrel1 == null) {
            tankBarrel1 = new Sprite(tankBarrelImage);
            tank1.tankBarrel = tankBarrel1;
        } else {
            tank1.tankBarrel = tankBarrel1;
        }
        return tank1.tankBarrel;
    }

    /**
     * Static method to retrieve Player 2's TankBarrel
     *
     * @return Player 2's TankBarrel
     */
    public static Sprite getTankBarrel2() {
        //Log.d("Tank", "Returning tankbarrel1");
        if (tankBarrel2 == null) {
            tankBarrel2 = new Sprite(tankBarrelImage);
            tank2.tankBarrel = tankBarrel2;
        } else {
            tank2.tankBarrel = tankBarrel2;
        }
        return tank2.tankBarrel;
    }

    /**
     * Sets the Tank's initial positions on the map.
     *
     * @param size DisplaySize of the Device
     */
    public static void setInitialTankPositions(Point size) {
        Log.d("Tank", "Setting positions for both tanks");
        tank1.setPosition(size.x / 10, size.y / 3);
        tank2.setPosition(size.x - size.x / 10, size.y / 3);
        setInitalBarrelPositions(size);
    }

    /**
     * Sets the TankBarrel's initial positions on the map,
     * and tries to place them on top of the tanks.
     *
     * @param size DisplaySize of the Device
     */
    private static void setInitalBarrelPositions(Point size) {
        Log.d("Tank", "Setting positions for both barrels");
        tankBarrel1.setPosition(size.x / 10 + tankBarrelImage.getWidth() / 2, size.y / 3 - tankImage1.getHeight() / 2);
        tankBarrel2.setPosition(size.x - size.x / 10 + tankBarrelImage.getWidth() / 2, size.y / 3 - tankImage1.getHeight() / 2 + (size.y / 40));
        tankBarrel2.rotate(180);

        //sett posisjonen til barrels riktig i forhold til tanksene.
    }

    /**
     * Sets the barrel angle of the tank to a specific angle,
     * and rotates it to the correct position.
     *
     * @param angle The angle the barrel should be set to
     */
    public void setBarrelAngle(int angle) {
        int barrelDiff = this.barrelAngle - angle;
        this.barrelAngle = angle;
        tankBarrel.rotate(barrelDiff);
        Log.d("Value", "new barrelAngle: " + this.barrelAngle);
    }

    /**
     * Reduces a tanks healthpoints.
     *
     * @param dmg The amount healthpoints should be reduced with
     */
    public void reduceHp(int dmg) {
        Log.d("Tank", "Reducing hp");
        this.hp = this.hp - dmg;
    }

    /**
     * Method to check if the Tank lost too much hp,
     * and is now dead.
     *
     * @return True if dead, False if not
     */
    public boolean isTankDead() {
        Log.d("Tank", "Checking if tank is dead");
        return this.hp < 0;
    }

    /**
     * Sets the firing power of the tank, and
     * checks it hasn't been set over the max of 2000
     *
     * @param timeHeld Total time user held down on the screen, Milliseconds
     */
    public void setTankPower(long timeHeld) {
        if (timeHeld > 2000) {
            this.power = 2000;
        } else this.power = (int) timeHeld;
    }

    /**
     * Get the specified power the tank had for that shot
     *
     * @return Returns the Tanks firepower for that shot.
     */
    public static int getTankPower() {
        return Controller.getActiveTank().power;
    }

    /**
     * Sets the initial fallspeed of the tanks and barrels
     * in the Y direction towards the ground.
     */
    public static void setStartSpeed() {
        tank1.setYSpeed(100);
        tank2.setYSpeed(100);
        tankBarrel1.setYSpeed(200);
        tankBarrel2.setYSpeed(200);
    }

    /**
     * Stops the movement of the tanks and barrels
     * in the Y direction.
     */
    public static void stopStartSpeed() {
        Log.d("Speed", "Tank1 speed is: " + tank1.getSpeed().toString());
        Log.d("Speed", "Tank2 speed is: " + tank2.getSpeed().toString());
        Log.d("Speed", "Tankbarrel1 speed is: " + tankBarrel1.getSpeed().toString());
        tank1.setYSpeed(0);
        tank2.setYSpeed(0);
        getTankBarrel1().setYSpeed(0);
        getTankBarrel2().setYSpeed(0);

        //Avoid future collisions
        tank1.setPosition(tank1.getPosition().getX(), tank1.getPosition().getY() - 1);
        tank2.setPosition(tank2.getPosition().getX(), tank2.getPosition().getY() - 1);
    }

    /**
     * Method to reduce ammo, after said ammo has been fired.
     */
    public void reduceAmmo() {
        String ammoName = Controller.getChosenProjectile();
        Log.d("Tank", "Reducing ammo of: " + ammoName);
        this.projectileAmmo.put(ammoName, (Integer) this.projectileAmmo.get(ammoName) - 1);
    }

    /**
     * Method to check if there is more ammo of that kind left.
     *
     * @param ammoName The name of the Ammo to be checked
     * @return True if ammo > 0, false if ammo =< 0.
     */
    public boolean checkAmmo(String ammoName) {
        Log.d("Tank", "Checking ammo of: " + ammoName);
        return (Integer) this.projectileAmmo.get(ammoName) > 0;
    }

    /**
     * Method to create the dictionary which keeps control of
     * the ammo within the Tank object.
     *
     * @param tankshells Number of tankshells the tank will have.
     * @param nukes      Number of nukes the tank will have.
     * @param thermites  Number of thermites the tank will have.
     * @return A dictionary with the struct<String, Integer> where,
     *         String is Ammoname and Integer is number of rounds left
     */
    private static Dictionary setStartingAmmo(final int tankshells, final int nukes, final int thermites) {
        Log.d("Tank", "Initializing ammo");
        return new Hashtable<String, Integer>() {{
            put("Bullet", 999);
            put("TankShell", tankshells);
            put("Nukes", nukes);
            put("ThermiteShells", thermites);
        }};
    }

    /**
     * @return Returns the barrel angle of the currently active tank
     */
    private static int getBarrelAngle() {
        return Controller.getActiveTank().barrelAngle;
    }

    /**
     * Method to calculate the Vector2 the Projectile should follow as a path.
     *
     * @return The Vector2 which should dictate the direction of the projectile
     */
    public static Vector2 getBarrelVector() {
        if (Controller.getActiveTank() == tank1) {
            return new Vector2(Math.abs(1 / (float) Math.cos(getBarrelAngle())),
                    -Math.abs(1 / (float) Math.sin(getBarrelAngle()))).getNormalized();
        } else return new Vector2(-Math.abs(1 / (float) Math.cos(getBarrelAngle())),
                -Math.abs(1 / (float) Math.sin(getBarrelAngle()))).getNormalized();
    }


    /**
     * @return Returns the position of the barrel of the active tank
     */
    public static Point getBarrelPosition() {
        Sprite activeBarrel = Controller.getActiveTank().tankBarrel;
        return new Point((int) activeBarrel.getX(), (int) activeBarrel.getY());
    }

    public Dictionary<String, Integer> getProjectileAmmo() {
        return projectileAmmo;
    }


}
