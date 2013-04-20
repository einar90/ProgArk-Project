package com.example.arcade.tankwars.projectiles;

import com.example.arcade.tankwars.Controller;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:24
 */
public class Bullet extends Projectile {


    public Bullet() {
        super();
        damage = 10;
        explosionRadius = 1;
    }

    public void explode() {
        if (explosionRadius > Controller.calculateDistance(this.getPosition(), Controller.getInactiveTank().getPosition())) {
            //
            Controller.getInactiveTank().reduceHp(damage);
        }
        return;
    }


}
