package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import com.example.arcade.Game;
import com.example.arcade.R;
import com.example.arcade.Scaling;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class Tank extends Sprite {

    private static final Resources resources = Game.getInstance().getResources();

    //Tank
    private static final Image tankImage1 = Scaling.getScaledImage(resources, R.drawable.tankbody1);
    private static final Image tankImage2 = Scaling.getScaledImage(resources, R.drawable.tankbody2);
    private static Tank tank1 = new Tank(tankImage1);
    private static Tank tank2 = new Tank(tankImage2);

    //TankBarrel
    private static final Image tankBarrelImage = Scaling.getScaledImage(resources, R.drawable.tankbarrel);
    private static Sprite tankBarrel1 = new Sprite(tankBarrelImage);
    private static Sprite tankBarrel2 = new Sprite(tankBarrelImage);

    //Extra stuff for Tank to hold
    private int barrelAngle;
    private int power;
    private int hp;
    private Sprite tankBarrel;
    Projectile projectilePicked;
    // private Dictionary projectileAmmo;    //Vet ikke om dictionary fungerer så bra

    protected Tank(Image tankimage) {
        super(tankimage);
        //this.hp = 100;
        //this.power = 0;
        //this.barrelAngle = 0;
        //this.projectileAmmo = setStartingAmmo(15, 1, 3);

    }


    public static Tank getTank1() {
        Log.d("Tank", "Returning tank1");
        if (tank1 == null) {
            tank1 = new Tank(tankImage1);
        }
        return tank1;
    }

    public static Tank getTank2() {
        Log.d("Tank", "Returning tank2");
        if (tank2 == null) {
            tank2 = new Tank(tankImage2);
        }
        return tank2;
    }

    public static Sprite getTankBarrel1() {
        Log.d("Tank", "Returning tankbarrel1");
        if (tankBarrel1 == null) {
            tankBarrel1 = new Sprite(tankBarrelImage);
            tank1.tankBarrel = tankBarrel1;
        } else {
            tank1.tankBarrel = tankBarrel1;
        }
        return tank1.tankBarrel;
    }

    public static Sprite getTankBarrel2() {
        Log.d("Tank", "Returning tankbarrel1");
        if (tankBarrel2 == null) {
            tankBarrel2 = new Sprite(tankBarrelImage);
            tank2.tankBarrel = tankBarrel2;
        } else {
            tank2.tankBarrel = tankBarrel2;
        }
        return tank2.tankBarrel;
    }

    public static void setInitialTankPositions(Point size) {
        Log.d("Tank", "Setting positions for both tanks");
        tank1.setPosition(size.x / 10, size.y / 3);
        tank2.setPosition(size.x - size.x / 10, size.y / 3);
        //Setter de litt i løse lufta foreløpig
        setInitalBarrelPositions(size);
    }

    public static void setInitalBarrelPositions(Point size) {
        Log.d("Tank", "Setting positions for both barrels");
        tankBarrel1.setPosition(size.x / 10, size.y / 3);
        tankBarrel2.setPosition(size.x - size.x / 10, size.y / 3);
        //sett posisjonen til barrels riktig i forhold til tanksene.
    }

    public void reduceHp(int dmg) {
        Log.d("Tank", "Reducing hp");
        this.hp = this.hp - dmg;
    }

    public boolean isTankDead() {
        Log.d("Tank", "Checking if tank is dead");
        if (this.hp < 0) return true;
        return false;
    }

    public static void setStartSpeed() {
        tank1.setSpeed(0, 50);
        tank2.setSpeed(0, 50);
    }
     /*
    public void reduceAmmo(String ammoName) {
        Log.d("Tank", "Reducing ammo of: " + ammoName);
        this.projectileAmmo.put(ammoName, Integer.parseInt(this.projectileAmmo.get(ammoName).toString()) - 1);
    }

    public boolean checkAmmo(String ammoName) {
        Log.d("Tank", "Checking ammo of: " + ammoName);
        if (Integer.parseInt(this.projectileAmmo.get(ammoName).toString()) > 0) return true;
        return false;
    }

    private static Dictionary setStartingAmmo(final int tankshells, final int nukes, final int thermites) {
        Log.d("Tank", "Initializing ammo");
        Map<String, Integer> map = new HashMap<String, Integer>() {{
            put("Bullet", 999);
            put("TankShell", tankshells);
            put("Nukes", nukes);
            put("ThermiteShells", thermites);
        }};
        return (Dictionary) map;
    }
      */
}
