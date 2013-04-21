package com.example.arcade.tankwars.explosions;

import com.example.arcade.tankwars.TankWarsUserInterface;
import sheep.game.Sprite;
import sheep.graphics.Image;

import java.util.Calendar;

/**
 * User: Dzenan
 * Date: 20.04.13
 * Time: 20:31
 */
public abstract class Explosion extends Sprite {
    private Calendar creationTime;
    private int duration;

    /**
     * Empty constructor, else there will be whine from the sublings..
     */
    Explosion() {
    }

    /**
     * Constructor for all the explosions that keeps track of create/kill
     * time.
     * @param duration      How long the explosion should last
     * @param spriteImage   What image to use on the explosion
     */
    Explosion(int duration, Image spriteImage) {
        super(spriteImage);
        creationTime = Calendar.getInstance();
        this.duration = duration;

    }



    @Override
    public void update(float dt) {
        super.update(dt);
        if(checkIfExplosionTimeDone()) TankWarsUserInterface.removeExplosion();
    }

    private boolean checkIfExplosionTimeDone(){
        return ((Calendar.getInstance().getTimeInMillis() - creationTime.getTimeInMillis()) / 1000 > duration);
    }


}
