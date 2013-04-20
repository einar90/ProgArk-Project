package com.example.arcade.tankwars;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:27
 */
class TankShell extends Projectile {

    public TankShell() {
        super();
        damage = 20;
        explosionRadius = 10;
    }

    public void explode() {
        if(explosionRadius > Controller.calculateDistance(this.getPosition(), Controller.getActiveTank().getPosition())){
            // TODO: Do some badass explosion stuff!
        }
        return;
    }
}
