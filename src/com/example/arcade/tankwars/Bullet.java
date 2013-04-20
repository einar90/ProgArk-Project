package com.example.arcade.tankwars;

import sheep.math.Vector2;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:24
 */
class Bullet extends Projectile {


    public Bullet() {
        super();
        damage = 10;
        explosionRadius = 1;
    }

    public void explode() {
        if(explosionRadius > Controller.calculateDistance(this.getPosition(), Controller.getActiveTank().getPosition())){
            // TODO: Do some badass explosion stuff!
        }
        return;
    }


}
