package com.example.arcade.tankwars.projectiles;

import com.example.arcade.tankwars.Controller;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:25
 */
public class Nuke extends Projectile {

    public Nuke() {
        super();
        damage = 50;
        explosionRadius = 50;
    }

    public void explode() {
        if (explosionRadius > Controller.calculateDistance(this.getPosition(), Controller.getActiveTank().getPosition())) {
            // TODO: Do some badass explosion stuff!
        }
        return;
    }
}
