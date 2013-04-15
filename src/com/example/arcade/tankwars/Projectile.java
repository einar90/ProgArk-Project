package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
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
        Tank activeTank = Controller.getActiveTank();
        int windAffectionFactor = Map.getWindVector();
        Point displaySize = GraphicsHelper.getDisplaySize();

        // Setting initial position for projectile
        if (activeTank == Tank.getTank1()) {
            this.setPosition(displaySize.x / 10, displaySize.y / 3);
        } else this.setPosition(displaySize.x - displaySize.x / 10, displaySize.y / 3);

        // Setting initial speed vector for projectile as calculated from the barrel angle and power of the active tank
        Vector2 initialSpeed = new Vector2(1 / (float) Math.cos(Tank.getBarrelAngle()), 1 / (float) Math.sin(Tank.getBarrelAngle()));
        initialSpeed.normalize();
        initialSpeed.multiply(activeTank.getTankPower());
        this.setSpeed(initialSpeed);

        // Setting acceleration vector for projectile, taking into account gravity and wind
        this.setAcceleration(windAffectionFactor, -10);
    }


    public boolean collision() {
        //To-do implementere collision detection mot objekter
        return false;
    }


    public void draw(Canvas canvas) {
        this.draw(canvas);
    }

    public void update(float dt) {
        this.update(dt);
    }

    /**
     * Abstract methods
     */
    abstract void explode();


}

