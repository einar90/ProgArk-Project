package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.util.Log;
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

    protected int damage;

    public Projectile() {
        super(spriteImage);
        int windAffectionFactor = -Map.getWindVector();

        // Setting initial position for projectile
        this.setPosition(Tank.getBarrelPosition().x, Tank.getBarrelPosition().y);

        // Setting initial speed vector for projectile as calculated from the barrel angle and power of the active tank
        Vector2 initialSpeed = Tank.getBarrelVector();
        initialSpeed.setX(initialSpeed.getX() * Tank.getTankPower());
        initialSpeed.setY(initialSpeed.getY() * Tank.getTankPower());
        this.setSpeed(initialSpeed);

        // Setting acceleration vector for projectile, taking into account gravity and wind
        this.setAcceleration(windAffectionFactor * 5, 100);

        Log.d("Projectile", "Position: " + this.getPosition().toString());
        Log.d("Projectile", "Speed: " + this.getSpeed().toString());
        Log.d("Projectile", "Acceleration: " + this.getAcceleration().toString());
    }


    public boolean collision() {
        //To-do implementere collision detection mot objekter
        return false;
    }


    /**
     * Abstract methods
     */
    abstract void explode();


}

