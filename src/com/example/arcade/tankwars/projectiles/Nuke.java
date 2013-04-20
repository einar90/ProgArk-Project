package com.example.arcade.tankwars.projectiles;

import android.util.Log;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.tankwars.Controller;
import com.example.arcade.tankwars.TankWarsUserInterface;
import com.example.arcade.tankwars.explosions.Explosion;
import com.example.arcade.tankwars.explosions.NukeExplosion;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:25
 */
public class Nuke extends Projectile {
    Explosion lol;

    public Nuke() {
        super();
        damage = 50;
        explosionRadius = 100;
    }

    public void explode() {
        Explosion DummyExplosion = new NukeExplosion();
        DummyExplosion.setPosition(this.getPosition());
        TankWarsUserInterface.createExplosion(DummyExplosion);
        if (explosionRadius* GraphicsHelper.getScalingFactor()[0] > Controller.calculateDistance(this.getPosition(), Controller.getInactiveTank().getPosition())) {
            //
            Log.d("Explode", "It hit with Nuke");
            Controller.getInactiveTank().reduceHp(damage);
        }
        return;
    }
}
