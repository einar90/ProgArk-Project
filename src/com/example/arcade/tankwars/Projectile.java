package com.example.arcade.tankwars;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class Projectile extends Sprite {

    private Vector2 velocityVector;
    private int position;

    private int damage;
    private int windAffectionFactor;    /*Antar wind er en Vector2, og denne
                                         *brukes bare til å multipliseres med*/

    public Projectile() {
        //To-Do fullføre konstruktfør med spriteimage og startposisjon++

    }

    public Vector2 getInitialVector() {
        return null;
    }

    public boolean collision() {
        //To-do implementere collision detection mot objekter
        return false;
    }

    public void changeVector() {
        //To-do forandre retning på prosjektilet
    }

    public void changePosition() {
        //To-Do forandre posisjonen til prosjektilet
    }


    public void draw(Canvas canvas) {

    }

    public void update(float dt) {

    }
}
