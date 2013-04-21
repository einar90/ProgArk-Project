package com.example.arcade.tankwars.projectiles;

import android.util.Log;
import com.example.arcade.GraphicsHelper;
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
        explosionRadius = 100;
    }

    public void explode() {
        Explosion DummyExplosion = new ThermiteExplosion();
        DummyExplosion.setPosition(this.getPosition());
        DummyExplosion.setScale(1.5f, 1.5f);
        TankWarsUserInterface.createExplosion(DummyExplosion);
        if (explosionRadius * GraphicsHelper.getScalingFactor()[0] > Controller.calculateDistance(this.getPosition(), Controller.getInactiveTank().getPosition())) {
            //
            Controller.getInactiveTank().reduceHp(damage);
        }
        return;
    }
}
