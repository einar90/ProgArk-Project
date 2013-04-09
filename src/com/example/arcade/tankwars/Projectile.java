package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class Projectile extends Sprite {

    private static final Resources resources = Game.getInstance().getResources();
    static final Image spriteImage = GraphicsHelper.getScaledImage(resources, R.drawable.projectile);

    private Vector2 velocityVector;
    private Vector2 position;
    private Projectile projectileType;  //Kan kanskje bruke string istedenfor her
    private int damage;
    private int windAffectionFactor;    /*Antar wind er en Vector2, og denne
                                         *brukes bare til å multipliseres med*/

    public Projectile() {
        super(spriteImage);
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
