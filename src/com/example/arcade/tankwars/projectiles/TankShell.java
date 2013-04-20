package com.example.arcade.tankwars.projectiles;

import android.util.Log;
import com.example.arcade.GraphicsHelper;
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
        explosionRadius = 100;
    }

    public void explode() {
        if (explosionRadius*GraphicsHelper.getScalingFactor()[0] > Controller.calculateDistance(this.getPosition(), Controller.getInactiveTank().getPosition())) {
            //
            Log.d("Explode", "It hit with Tankshell");
            Controller.getInactiveTank().reduceHp(damage);
        }
        return;
    }
}
