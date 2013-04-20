package com.example.arcade.tankwars.projectiles;

import com.example.arcade.tankwars.Controller;
import com.example.arcade.tankwars.TankWarsUserInterface;
import com.example.arcade.tankwars.explosions.Explosion;
import com.example.arcade.tankwars.explosions.ThermiteExplosion;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:26
 */
public class ThermiteShell extends Projectile {

    public ThermiteShell() {
        super();
        damage = 35;
        explosionRadius = 10;
    }

    public void explode() {
        Explosion DummyExplosion = new ThermiteExplosion();
        DummyExplosion.setPosition(this.getPosition());
        TankWarsUserInterface.createExplosion(DummyExplosion);
        if (explosionRadius > Controller.calculateDistance(this.getPosition(), Controller.getInactiveTank().getPosition())) {
            //
            Controller.getInactiveTank().reduceHp(damage);
        }
        return;
    }
}
