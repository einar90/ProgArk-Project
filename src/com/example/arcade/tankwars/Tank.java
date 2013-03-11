package com.example.arcade.tankwars;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.math.Vector2;

import java.util.Dictionary;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class Tank extends Sprite {

    private int barrelAngle;
    private int power;
    private int hp;
    private Vector2 position;
    Projectile projectilePicked;
    private Dictionary projectileAmmo;

    public Tank(Vector2 position) {
        this.position = position;
        this.hp = 100;
        this.power = 0;
        this.barrelAngle = 0;

    }

    public void draw(Canvas canvas) {


    }

    public void update(float dt) {

    }
}
