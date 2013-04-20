package com.example.arcade.tankwars.projectiles;

import com.example.arcade.tankwars.Controller;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:27
 */
public class TankShell extends Projectile {

    public TankShell() {
        super();
        damage = 20;
        explosionRadius = 10;
    }

    public void explode() {
        if (explosionRadius > Controller.calculateDistance(this.getPosition(), Controller.getInactiveTank().getPosition())) {
            //
            Controller.getInactiveTank().reduceHp(damage);
        }
        return;
    }
}
