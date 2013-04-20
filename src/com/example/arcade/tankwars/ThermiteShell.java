package com.example.arcade.tankwars;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:26
 */
class ThermiteShell extends Projectile {

    public ThermiteShell() {
        super();
        damage = 35;
        explosionRadius = 10;
    }

    public void explode() {
        if(explosionRadius > Controller.calculateDistance(this.getPosition(), Controller.getActiveTank().getPosition())){
            // TODO: Do some badass explosion stuff!
        }
        return;
    }
}
