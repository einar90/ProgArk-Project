package com.example.arcade.tankwars;

import android.graphics.Canvas;
import com.example.arcade.Scaling;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

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

    private final Image tankImage1 = new Scaling().getScaledImage("tankbody1");
    private final Image tankImage2 = new Scaling().getScaledImage("tankbody2");
    private int barrelAngle;
    private int power;
    private int hp;
    Projectile projectilePicked;
    private Dictionary projectileAmmo;    //Vet ikke om dictionary fungerer så bra

    public Tank(Vector2 position, int playerNo) {

        if (playerNo == 1) {
            //Velg første bilde
        } else {
            //Velg andre bilde
        }

        this.setPosition(position);
        this.hp = 100;
        this.power = 0;
        this.barrelAngle = 0;
        this.projectileAmmo = setStartingAmmo(15, 1, 3);

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

    public void draw(Canvas canvas) {


    }

    public void update(float dt) {

    }
}
