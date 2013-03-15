package com.example.arcade.tankwars;

import android.graphics.Point;
import com.example.arcade.Scaling;
import sheep.game.Sprite;
import sheep.graphics.Image;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class Tank extends Sprite {


    private static final Image tankImage1 = new Scaling().getScaledImage("tankbody1");
    private static final Image tankImage2 = new Scaling().getScaledImage("tankbody2");
    private static final Tank tank1 = new Tank(tankImage1);
    private static final Tank tank2 = new Tank(tankImage2);

    private static final Image tankBarrelImage = new Scaling().getScaledImage("tankbarrel");
    private static Sprite tankBarrel1 = new Sprite(tankBarrelImage);
    private static Sprite tankBarrel2 = new Sprite(tankBarrelImage);

    private int barrelAngle;
    private int power;
    private int hp;
    private Sprite tankBarrel;
    Projectile projectilePicked;
    private Dictionary projectileAmmo;    //Vet ikke om dictionary fungerer så bra

    public Tank(Image tankImage) {
        super(tankImage);
        this.hp = 100;
        this.power = 0;
        this.barrelAngle = 0;
        this.projectileAmmo = setStartingAmmo(15, 1, 3);

    }

    public static Tank getTank1() {
        return tank1;
    }

    public static Tank getTank2() {
        return tank2;
    }

    public static Sprite getTankBarrel1() {
        tank1.tankBarrel = tankBarrel1;
        return tank1.tankBarrel;
    }

    public static Sprite getTankBarrel2() {
        tank2.tankBarrel = tankBarrel2;
        return tank2.tankBarrel;
    }

    public static void setInitialTankPositions(Point size) {
        tank1.setPosition(size.x / 10, size.y / 3);
        tank2.setPosition(size.x - size.x / 10, size.y / 3);
        //Setter de litt i løse lufta foreløpig
        setInitalBarrelPositions(size);
    }

    public static void setInitalBarrelPositions(Point size) {
        //sett posisjonen til barrels riktig i forhold til tanksene.
    }

    public void reduceHp(int dmg) {
        this.hp = this.hp - dmg;
    }

    public void reduceAmmo(String ammoName) {
        this.projectileAmmo.put(ammoName, Integer.parseInt(this.projectileAmmo.get(ammoName).toString()) - 1);
    }

    public boolean checkAmmo(String ammoName) {
        if (Integer.parseInt(this.projectileAmmo.get(ammoName).toString()) > 0) return true;
        return false;
    }

    private static Dictionary setStartingAmmo(final int tankshells, final int nukes, final int thermites) {
        Map<String, Integer> map = new HashMap<String, Integer>() {{
            put("Bullet", 999);
            put("TankShell", tankshells);
            put("Nukes", nukes);
            put("ThermiteShells", thermites);
        }};
        return (Dictionary) map;
    }

}
