package com.example.arcade.tankwars.projectiles;

import android.content.res.Resources;
import android.util.Log;
import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;
import com.example.arcade.tankwars.Controller;
import com.example.arcade.tankwars.Map;
import com.example.arcade.tankwars.Tank;
import com.example.arcade.tankwars.TankWarsUserInterface;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:01
 */
public abstract class Projectile extends Sprite {

    private static final Resources resources = Game.getInstance().getResources();
    private static final Image spriteImage = GraphicsHelper.getScaledImage(resources, R.drawable.projectile);

    public int damage;
    public int explosionRadius;


    /**
     * Contstructor for the Abstract class Projectile. All ammo types must call this!
     * Sets WindAffection, speed and direction, and the acceleration(gravity)
     * of the projectile. It also makes a call to Sprite() with the predefined
     * spriteImage we are using.
     */
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
        this.setAcceleration(windAffectionFactor, 200);

        Log.d("Projectile", "Position: " + this.getPosition().toString());
        Log.d("Projectile", "Speed: " + this.getSpeed().toString());
        Log.d("Projectile", "Acceleration: " + this.getAcceleration().toString());
    }

    /**
     * Checks if the Projectile had a collision with another sprite,
     * or map boundaries.
     *
     * @return True if Collision, else False
     */
    public boolean collision() {
        //To-do implementere collision detection mot objekter
        return false;
    }


    /**
     * Abstract methods
     * Going to be used in subclasses to draw
     * the Explosions on the device screen.
     */
    public abstract void explode();


    @Override
    public void update(float dt) {
        super.update(dt);


        // Deleting projectile if it's outside the screen
        Vector2 position = this.getPosition();
        if (position.getX() < 0 || position.getX() > GraphicsHelper.getDisplaySize().x
                || position.getY() < 0 || position.getY() > GraphicsHelper.getDisplaySize().y) {
            TankWarsUserInterface.removeCurrentProjectile();
            Controller.changeActiveTank();
            this.die();
        }

    }
}

